package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFansEgeLevel;
import com.intelligent.chart.repository.PersonFansEgeLevelRepository;
import com.intelligent.chart.service.PersonFansEgeLevelService;

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
 * Test class for the PersonFansEgeLevelResource REST controller.
 *
 * @see PersonFansEgeLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFansEgeLevelResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonFansEgeLevelRepository personFansEgeLevelRepository;

    @Inject
    private PersonFansEgeLevelService personFansEgeLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFansEgeLevelMockMvc;

    private PersonFansEgeLevel personFansEgeLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFansEgeLevelResource personFansEgeLevelResource = new PersonFansEgeLevelResource();
        ReflectionTestUtils.setField(personFansEgeLevelResource, "personFansEgeLevelService", personFansEgeLevelService);
        this.restPersonFansEgeLevelMockMvc = MockMvcBuilders.standaloneSetup(personFansEgeLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFansEgeLevel createEntity(EntityManager em) {
        PersonFansEgeLevel personFansEgeLevel = new PersonFansEgeLevel()
                .count(DEFAULT_COUNT);
        return personFansEgeLevel;
    }

    @Before
    public void initTest() {
        personFansEgeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFansEgeLevel() throws Exception {
        int databaseSizeBeforeCreate = personFansEgeLevelRepository.findAll().size();

        // Create the PersonFansEgeLevel

        restPersonFansEgeLevelMockMvc.perform(post("/api/person-fans-ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansEgeLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonFansEgeLevel in the database
        List<PersonFansEgeLevel> personFansEgeLevelList = personFansEgeLevelRepository.findAll();
        assertThat(personFansEgeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFansEgeLevel testPersonFansEgeLevel = personFansEgeLevelList.get(personFansEgeLevelList.size() - 1);
        assertThat(testPersonFansEgeLevel.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFansEgeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFansEgeLevelRepository.findAll().size();

        // Create the PersonFansEgeLevel with an existing ID
        PersonFansEgeLevel existingPersonFansEgeLevel = new PersonFansEgeLevel();
        existingPersonFansEgeLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFansEgeLevelMockMvc.perform(post("/api/person-fans-ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFansEgeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFansEgeLevel> personFansEgeLevelList = personFansEgeLevelRepository.findAll();
        assertThat(personFansEgeLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFansEgeLevels() throws Exception {
        // Initialize the database
        personFansEgeLevelRepository.saveAndFlush(personFansEgeLevel);

        // Get all the personFansEgeLevelList
        restPersonFansEgeLevelMockMvc.perform(get("/api/person-fans-ege-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFansEgeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonFansEgeLevel() throws Exception {
        // Initialize the database
        personFansEgeLevelRepository.saveAndFlush(personFansEgeLevel);

        // Get the personFansEgeLevel
        restPersonFansEgeLevelMockMvc.perform(get("/api/person-fans-ege-levels/{id}", personFansEgeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFansEgeLevel.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFansEgeLevel() throws Exception {
        // Get the personFansEgeLevel
        restPersonFansEgeLevelMockMvc.perform(get("/api/person-fans-ege-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFansEgeLevel() throws Exception {
        // Initialize the database
        personFansEgeLevelService.save(personFansEgeLevel);

        int databaseSizeBeforeUpdate = personFansEgeLevelRepository.findAll().size();

        // Update the personFansEgeLevel
        PersonFansEgeLevel updatedPersonFansEgeLevel = personFansEgeLevelRepository.findOne(personFansEgeLevel.getId());
        updatedPersonFansEgeLevel
                .count(UPDATED_COUNT);

        restPersonFansEgeLevelMockMvc.perform(put("/api/person-fans-ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFansEgeLevel)))
            .andExpect(status().isOk());

        // Validate the PersonFansEgeLevel in the database
        List<PersonFansEgeLevel> personFansEgeLevelList = personFansEgeLevelRepository.findAll();
        assertThat(personFansEgeLevelList).hasSize(databaseSizeBeforeUpdate);
        PersonFansEgeLevel testPersonFansEgeLevel = personFansEgeLevelList.get(personFansEgeLevelList.size() - 1);
        assertThat(testPersonFansEgeLevel.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFansEgeLevel() throws Exception {
        int databaseSizeBeforeUpdate = personFansEgeLevelRepository.findAll().size();

        // Create the PersonFansEgeLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFansEgeLevelMockMvc.perform(put("/api/person-fans-ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansEgeLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonFansEgeLevel in the database
        List<PersonFansEgeLevel> personFansEgeLevelList = personFansEgeLevelRepository.findAll();
        assertThat(personFansEgeLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFansEgeLevel() throws Exception {
        // Initialize the database
        personFansEgeLevelService.save(personFansEgeLevel);

        int databaseSizeBeforeDelete = personFansEgeLevelRepository.findAll().size();

        // Get the personFansEgeLevel
        restPersonFansEgeLevelMockMvc.perform(delete("/api/person-fans-ege-levels/{id}", personFansEgeLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFansEgeLevel> personFansEgeLevelList = personFansEgeLevelRepository.findAll();
        assertThat(personFansEgeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
