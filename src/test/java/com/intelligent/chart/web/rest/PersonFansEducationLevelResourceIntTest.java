package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFansEducationLevel;
import com.intelligent.chart.repository.PersonFansEducationLevelRepository;
import com.intelligent.chart.service.PersonFansEducationLevelService;

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
 * Test class for the PersonFansEducationLevelResource REST controller.
 *
 * @see PersonFansEducationLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFansEducationLevelResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonFansEducationLevelRepository personFansEducationLevelRepository;

    @Inject
    private PersonFansEducationLevelService personFansEducationLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFansEducationLevelMockMvc;

    private PersonFansEducationLevel personFansEducationLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFansEducationLevelResource personFansEducationLevelResource = new PersonFansEducationLevelResource();
        ReflectionTestUtils.setField(personFansEducationLevelResource, "personFansEducationLevelService", personFansEducationLevelService);
        this.restPersonFansEducationLevelMockMvc = MockMvcBuilders.standaloneSetup(personFansEducationLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFansEducationLevel createEntity(EntityManager em) {
        PersonFansEducationLevel personFansEducationLevel = new PersonFansEducationLevel()
                .count(DEFAULT_COUNT);
        return personFansEducationLevel;
    }

    @Before
    public void initTest() {
        personFansEducationLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFansEducationLevel() throws Exception {
        int databaseSizeBeforeCreate = personFansEducationLevelRepository.findAll().size();

        // Create the PersonFansEducationLevel

        restPersonFansEducationLevelMockMvc.perform(post("/api/person-fans-education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansEducationLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonFansEducationLevel in the database
        List<PersonFansEducationLevel> personFansEducationLevelList = personFansEducationLevelRepository.findAll();
        assertThat(personFansEducationLevelList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFansEducationLevel testPersonFansEducationLevel = personFansEducationLevelList.get(personFansEducationLevelList.size() - 1);
        assertThat(testPersonFansEducationLevel.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFansEducationLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFansEducationLevelRepository.findAll().size();

        // Create the PersonFansEducationLevel with an existing ID
        PersonFansEducationLevel existingPersonFansEducationLevel = new PersonFansEducationLevel();
        existingPersonFansEducationLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFansEducationLevelMockMvc.perform(post("/api/person-fans-education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFansEducationLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFansEducationLevel> personFansEducationLevelList = personFansEducationLevelRepository.findAll();
        assertThat(personFansEducationLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFansEducationLevels() throws Exception {
        // Initialize the database
        personFansEducationLevelRepository.saveAndFlush(personFansEducationLevel);

        // Get all the personFansEducationLevelList
        restPersonFansEducationLevelMockMvc.perform(get("/api/person-fans-education-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFansEducationLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonFansEducationLevel() throws Exception {
        // Initialize the database
        personFansEducationLevelRepository.saveAndFlush(personFansEducationLevel);

        // Get the personFansEducationLevel
        restPersonFansEducationLevelMockMvc.perform(get("/api/person-fans-education-levels/{id}", personFansEducationLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFansEducationLevel.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFansEducationLevel() throws Exception {
        // Get the personFansEducationLevel
        restPersonFansEducationLevelMockMvc.perform(get("/api/person-fans-education-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFansEducationLevel() throws Exception {
        // Initialize the database
        personFansEducationLevelService.save(personFansEducationLevel);

        int databaseSizeBeforeUpdate = personFansEducationLevelRepository.findAll().size();

        // Update the personFansEducationLevel
        PersonFansEducationLevel updatedPersonFansEducationLevel = personFansEducationLevelRepository.findOne(personFansEducationLevel.getId());
        updatedPersonFansEducationLevel
                .count(UPDATED_COUNT);

        restPersonFansEducationLevelMockMvc.perform(put("/api/person-fans-education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFansEducationLevel)))
            .andExpect(status().isOk());

        // Validate the PersonFansEducationLevel in the database
        List<PersonFansEducationLevel> personFansEducationLevelList = personFansEducationLevelRepository.findAll();
        assertThat(personFansEducationLevelList).hasSize(databaseSizeBeforeUpdate);
        PersonFansEducationLevel testPersonFansEducationLevel = personFansEducationLevelList.get(personFansEducationLevelList.size() - 1);
        assertThat(testPersonFansEducationLevel.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFansEducationLevel() throws Exception {
        int databaseSizeBeforeUpdate = personFansEducationLevelRepository.findAll().size();

        // Create the PersonFansEducationLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFansEducationLevelMockMvc.perform(put("/api/person-fans-education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansEducationLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonFansEducationLevel in the database
        List<PersonFansEducationLevel> personFansEducationLevelList = personFansEducationLevelRepository.findAll();
        assertThat(personFansEducationLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFansEducationLevel() throws Exception {
        // Initialize the database
        personFansEducationLevelService.save(personFansEducationLevel);

        int databaseSizeBeforeDelete = personFansEducationLevelRepository.findAll().size();

        // Get the personFansEducationLevel
        restPersonFansEducationLevelMockMvc.perform(delete("/api/person-fans-education-levels/{id}", personFansEducationLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFansEducationLevel> personFansEducationLevelList = personFansEducationLevelRepository.findAll();
        assertThat(personFansEducationLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
