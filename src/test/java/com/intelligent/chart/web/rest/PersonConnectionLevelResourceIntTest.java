package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonConnectionLevel;
import com.intelligent.chart.repository.PersonConnectionLevelRepository;
import com.intelligent.chart.service.PersonConnectionLevelService;

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
 * Test class for the PersonConnectionLevelResource REST controller.
 *
 * @see PersonConnectionLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonConnectionLevelResourceIntTest {

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Inject
    private PersonConnectionLevelRepository personConnectionLevelRepository;

    @Inject
    private PersonConnectionLevelService personConnectionLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonConnectionLevelMockMvc;

    private PersonConnectionLevel personConnectionLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonConnectionLevelResource personConnectionLevelResource = new PersonConnectionLevelResource();
        ReflectionTestUtils.setField(personConnectionLevelResource, "personConnectionLevelService", personConnectionLevelService);
        this.restPersonConnectionLevelMockMvc = MockMvcBuilders.standaloneSetup(personConnectionLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonConnectionLevel createEntity(EntityManager em) {
        PersonConnectionLevel personConnectionLevel = new PersonConnectionLevel()
                .percentage(DEFAULT_PERCENTAGE);
        return personConnectionLevel;
    }

    @Before
    public void initTest() {
        personConnectionLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonConnectionLevel() throws Exception {
        int databaseSizeBeforeCreate = personConnectionLevelRepository.findAll().size();

        // Create the PersonConnectionLevel

        restPersonConnectionLevelMockMvc.perform(post("/api/person-connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personConnectionLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonConnectionLevel in the database
        List<PersonConnectionLevel> personConnectionLevelList = personConnectionLevelRepository.findAll();
        assertThat(personConnectionLevelList).hasSize(databaseSizeBeforeCreate + 1);
        PersonConnectionLevel testPersonConnectionLevel = personConnectionLevelList.get(personConnectionLevelList.size() - 1);
        assertThat(testPersonConnectionLevel.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createPersonConnectionLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personConnectionLevelRepository.findAll().size();

        // Create the PersonConnectionLevel with an existing ID
        PersonConnectionLevel existingPersonConnectionLevel = new PersonConnectionLevel();
        existingPersonConnectionLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonConnectionLevelMockMvc.perform(post("/api/person-connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonConnectionLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonConnectionLevel> personConnectionLevelList = personConnectionLevelRepository.findAll();
        assertThat(personConnectionLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonConnectionLevels() throws Exception {
        // Initialize the database
        personConnectionLevelRepository.saveAndFlush(personConnectionLevel);

        // Get all the personConnectionLevelList
        restPersonConnectionLevelMockMvc.perform(get("/api/person-connection-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personConnectionLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonConnectionLevel() throws Exception {
        // Initialize the database
        personConnectionLevelRepository.saveAndFlush(personConnectionLevel);

        // Get the personConnectionLevel
        restPersonConnectionLevelMockMvc.perform(get("/api/person-connection-levels/{id}", personConnectionLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personConnectionLevel.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonConnectionLevel() throws Exception {
        // Get the personConnectionLevel
        restPersonConnectionLevelMockMvc.perform(get("/api/person-connection-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonConnectionLevel() throws Exception {
        // Initialize the database
        personConnectionLevelService.save(personConnectionLevel);

        int databaseSizeBeforeUpdate = personConnectionLevelRepository.findAll().size();

        // Update the personConnectionLevel
        PersonConnectionLevel updatedPersonConnectionLevel = personConnectionLevelRepository.findOne(personConnectionLevel.getId());
        updatedPersonConnectionLevel
                .percentage(UPDATED_PERCENTAGE);

        restPersonConnectionLevelMockMvc.perform(put("/api/person-connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonConnectionLevel)))
            .andExpect(status().isOk());

        // Validate the PersonConnectionLevel in the database
        List<PersonConnectionLevel> personConnectionLevelList = personConnectionLevelRepository.findAll();
        assertThat(personConnectionLevelList).hasSize(databaseSizeBeforeUpdate);
        PersonConnectionLevel testPersonConnectionLevel = personConnectionLevelList.get(personConnectionLevelList.size() - 1);
        assertThat(testPersonConnectionLevel.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonConnectionLevel() throws Exception {
        int databaseSizeBeforeUpdate = personConnectionLevelRepository.findAll().size();

        // Create the PersonConnectionLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonConnectionLevelMockMvc.perform(put("/api/person-connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personConnectionLevel)))
            .andExpect(status().isCreated());

        // Validate the PersonConnectionLevel in the database
        List<PersonConnectionLevel> personConnectionLevelList = personConnectionLevelRepository.findAll();
        assertThat(personConnectionLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonConnectionLevel() throws Exception {
        // Initialize the database
        personConnectionLevelService.save(personConnectionLevel);

        int databaseSizeBeforeDelete = personConnectionLevelRepository.findAll().size();

        // Get the personConnectionLevel
        restPersonConnectionLevelMockMvc.perform(delete("/api/person-connection-levels/{id}", personConnectionLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonConnectionLevel> personConnectionLevelList = personConnectionLevelRepository.findAll();
        assertThat(personConnectionLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
