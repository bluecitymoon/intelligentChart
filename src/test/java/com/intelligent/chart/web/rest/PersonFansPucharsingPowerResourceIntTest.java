package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFansPucharsingPower;
import com.intelligent.chart.repository.PersonFansPucharsingPowerRepository;
import com.intelligent.chart.service.PersonFansPucharsingPowerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonFansPucharsingPowerResource REST controller.
 *
 * @see PersonFansPucharsingPowerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFansPucharsingPowerResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonFansPucharsingPowerRepository personFansPucharsingPowerRepository;

    @Inject
    private PersonFansPucharsingPowerService personFansPucharsingPowerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFansPucharsingPowerMockMvc;

    private PersonFansPucharsingPower personFansPucharsingPower;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFansPucharsingPowerResource personFansPucharsingPowerResource = new PersonFansPucharsingPowerResource();
        ReflectionTestUtils.setField(personFansPucharsingPowerResource, "personFansPucharsingPowerService", personFansPucharsingPowerService);
        this.restPersonFansPucharsingPowerMockMvc = MockMvcBuilders.standaloneSetup(personFansPucharsingPowerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFansPucharsingPower createEntity(EntityManager em) {
        PersonFansPucharsingPower personFansPucharsingPower = new PersonFansPucharsingPower()
                .count(DEFAULT_COUNT);
        return personFansPucharsingPower;
    }

    @Before
    public void initTest() {
        personFansPucharsingPower = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFansPucharsingPower() throws Exception {
        int databaseSizeBeforeCreate = personFansPucharsingPowerRepository.findAll().size();

        // Create the PersonFansPucharsingPower

        restPersonFansPucharsingPowerMockMvc.perform(post("/api/person-fans-pucharsing-powers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansPucharsingPower)))
            .andExpect(status().isCreated());

        // Validate the PersonFansPucharsingPower in the database
        List<PersonFansPucharsingPower> personFansPucharsingPowerList = personFansPucharsingPowerRepository.findAll();
        assertThat(personFansPucharsingPowerList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFansPucharsingPower testPersonFansPucharsingPower = personFansPucharsingPowerList.get(personFansPucharsingPowerList.size() - 1);
        assertThat(testPersonFansPucharsingPower.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFansPucharsingPowerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFansPucharsingPowerRepository.findAll().size();

        // Create the PersonFansPucharsingPower with an existing ID
        PersonFansPucharsingPower existingPersonFansPucharsingPower = new PersonFansPucharsingPower();
        existingPersonFansPucharsingPower.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFansPucharsingPowerMockMvc.perform(post("/api/person-fans-pucharsing-powers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFansPucharsingPower)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFansPucharsingPower> personFansPucharsingPowerList = personFansPucharsingPowerRepository.findAll();
        assertThat(personFansPucharsingPowerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFansPucharsingPowers() throws Exception {
        // Initialize the database
        personFansPucharsingPowerRepository.saveAndFlush(personFansPucharsingPower);

        // Get all the personFansPucharsingPowerList
        restPersonFansPucharsingPowerMockMvc.perform(get("/api/person-fans-pucharsing-powers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFansPucharsingPower.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonFansPucharsingPower() throws Exception {
        // Initialize the database
        personFansPucharsingPowerRepository.saveAndFlush(personFansPucharsingPower);

        // Get the personFansPucharsingPower
        restPersonFansPucharsingPowerMockMvc.perform(get("/api/person-fans-pucharsing-powers/{id}", personFansPucharsingPower.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFansPucharsingPower.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFansPucharsingPower() throws Exception {
        // Get the personFansPucharsingPower
        restPersonFansPucharsingPowerMockMvc.perform(get("/api/person-fans-pucharsing-powers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFansPucharsingPower() throws Exception {
        // Initialize the database
        personFansPucharsingPowerService.save(personFansPucharsingPower);

        int databaseSizeBeforeUpdate = personFansPucharsingPowerRepository.findAll().size();

        // Update the personFansPucharsingPower
        PersonFansPucharsingPower updatedPersonFansPucharsingPower = personFansPucharsingPowerRepository.findOne(personFansPucharsingPower.getId());
        updatedPersonFansPucharsingPower
                .count(UPDATED_COUNT);

        restPersonFansPucharsingPowerMockMvc.perform(put("/api/person-fans-pucharsing-powers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFansPucharsingPower)))
            .andExpect(status().isOk());

        // Validate the PersonFansPucharsingPower in the database
        List<PersonFansPucharsingPower> personFansPucharsingPowerList = personFansPucharsingPowerRepository.findAll();
        assertThat(personFansPucharsingPowerList).hasSize(databaseSizeBeforeUpdate);
        PersonFansPucharsingPower testPersonFansPucharsingPower = personFansPucharsingPowerList.get(personFansPucharsingPowerList.size() - 1);
        assertThat(testPersonFansPucharsingPower.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFansPucharsingPower() throws Exception {
        int databaseSizeBeforeUpdate = personFansPucharsingPowerRepository.findAll().size();

        // Create the PersonFansPucharsingPower

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFansPucharsingPowerMockMvc.perform(put("/api/person-fans-pucharsing-powers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansPucharsingPower)))
            .andExpect(status().isCreated());

        // Validate the PersonFansPucharsingPower in the database
        List<PersonFansPucharsingPower> personFansPucharsingPowerList = personFansPucharsingPowerRepository.findAll();
        assertThat(personFansPucharsingPowerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFansPucharsingPower() throws Exception {
        // Initialize the database
        personFansPucharsingPowerService.save(personFansPucharsingPower);

        int databaseSizeBeforeDelete = personFansPucharsingPowerRepository.findAll().size();

        // Get the personFansPucharsingPower
        restPersonFansPucharsingPowerMockMvc.perform(delete("/api/person-fans-pucharsing-powers/{id}", personFansPucharsingPower.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFansPucharsingPower> personFansPucharsingPowerList = personFansPucharsingPowerRepository.findAll();
        assertThat(personFansPucharsingPowerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
