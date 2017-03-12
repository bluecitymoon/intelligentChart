package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFanSex;
import com.intelligent.chart.repository.PersonFanSexRepository;
import com.intelligent.chart.service.PersonFanSexService;

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
 * Test class for the PersonFanSexResource REST controller.
 *
 * @see PersonFanSexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFanSexResourceIntTest {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Inject
    private PersonFanSexRepository personFanSexRepository;

    @Inject
    private PersonFanSexService personFanSexService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFanSexMockMvc;

    private PersonFanSex personFanSex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFanSexResource personFanSexResource = new PersonFanSexResource();
        ReflectionTestUtils.setField(personFanSexResource, "personFanSexService", personFanSexService);
        this.restPersonFanSexMockMvc = MockMvcBuilders.standaloneSetup(personFanSexResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFanSex createEntity(EntityManager em) {
        PersonFanSex personFanSex = new PersonFanSex()
                .count(DEFAULT_COUNT);
        return personFanSex;
    }

    @Before
    public void initTest() {
        personFanSex = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFanSex() throws Exception {
        int databaseSizeBeforeCreate = personFanSexRepository.findAll().size();

        // Create the PersonFanSex

        restPersonFanSexMockMvc.perform(post("/api/person-fan-sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanSex)))
            .andExpect(status().isCreated());

        // Validate the PersonFanSex in the database
        List<PersonFanSex> personFanSexList = personFanSexRepository.findAll();
        assertThat(personFanSexList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFanSex testPersonFanSex = personFanSexList.get(personFanSexList.size() - 1);
        assertThat(testPersonFanSex.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFanSexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFanSexRepository.findAll().size();

        // Create the PersonFanSex with an existing ID
        PersonFanSex existingPersonFanSex = new PersonFanSex();
        existingPersonFanSex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFanSexMockMvc.perform(post("/api/person-fan-sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFanSex)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFanSex> personFanSexList = personFanSexRepository.findAll();
        assertThat(personFanSexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFanSexes() throws Exception {
        // Initialize the database
        personFanSexRepository.saveAndFlush(personFanSex);

        // Get all the personFanSexList
        restPersonFanSexMockMvc.perform(get("/api/person-fan-sexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFanSex.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFanSex() throws Exception {
        // Initialize the database
        personFanSexRepository.saveAndFlush(personFanSex);

        // Get the personFanSex
        restPersonFanSexMockMvc.perform(get("/api/person-fan-sexes/{id}", personFanSex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFanSex.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFanSex() throws Exception {
        // Get the personFanSex
        restPersonFanSexMockMvc.perform(get("/api/person-fan-sexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFanSex() throws Exception {
        // Initialize the database
        personFanSexService.save(personFanSex);

        int databaseSizeBeforeUpdate = personFanSexRepository.findAll().size();

        // Update the personFanSex
        PersonFanSex updatedPersonFanSex = personFanSexRepository.findOne(personFanSex.getId());
        updatedPersonFanSex
                .count(UPDATED_COUNT);

        restPersonFanSexMockMvc.perform(put("/api/person-fan-sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFanSex)))
            .andExpect(status().isOk());

        // Validate the PersonFanSex in the database
        List<PersonFanSex> personFanSexList = personFanSexRepository.findAll();
        assertThat(personFanSexList).hasSize(databaseSizeBeforeUpdate);
        PersonFanSex testPersonFanSex = personFanSexList.get(personFanSexList.size() - 1);
        assertThat(testPersonFanSex.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFanSex() throws Exception {
        int databaseSizeBeforeUpdate = personFanSexRepository.findAll().size();

        // Create the PersonFanSex

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFanSexMockMvc.perform(put("/api/person-fan-sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanSex)))
            .andExpect(status().isCreated());

        // Validate the PersonFanSex in the database
        List<PersonFanSex> personFanSexList = personFanSexRepository.findAll();
        assertThat(personFanSexList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFanSex() throws Exception {
        // Initialize the database
        personFanSexService.save(personFanSex);

        int databaseSizeBeforeDelete = personFanSexRepository.findAll().size();

        // Get the personFanSex
        restPersonFanSexMockMvc.perform(delete("/api/person-fan-sexes/{id}", personFanSex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFanSex> personFanSexList = personFanSexRepository.findAll();
        assertThat(personFanSexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
