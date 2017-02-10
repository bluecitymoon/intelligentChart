package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonAreaPercentage;
import com.intelligent.chart.repository.PersonAreaPercentageRepository;
import com.intelligent.chart.service.PersonAreaPercentageService;

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
 * Test class for the PersonAreaPercentageResource REST controller.
 *
 * @see PersonAreaPercentageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonAreaPercentageResourceIntTest {

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Inject
    private PersonAreaPercentageRepository personAreaPercentageRepository;

    @Inject
    private PersonAreaPercentageService personAreaPercentageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonAreaPercentageMockMvc;

    private PersonAreaPercentage personAreaPercentage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonAreaPercentageResource personAreaPercentageResource = new PersonAreaPercentageResource();
        ReflectionTestUtils.setField(personAreaPercentageResource, "personAreaPercentageService", personAreaPercentageService);
        this.restPersonAreaPercentageMockMvc = MockMvcBuilders.standaloneSetup(personAreaPercentageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonAreaPercentage createEntity(EntityManager em) {
        PersonAreaPercentage personAreaPercentage = new PersonAreaPercentage()
                .percentage(DEFAULT_PERCENTAGE);
        return personAreaPercentage;
    }

    @Before
    public void initTest() {
        personAreaPercentage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonAreaPercentage() throws Exception {
        int databaseSizeBeforeCreate = personAreaPercentageRepository.findAll().size();

        // Create the PersonAreaPercentage

        restPersonAreaPercentageMockMvc.perform(post("/api/person-area-percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAreaPercentage)))
            .andExpect(status().isCreated());

        // Validate the PersonAreaPercentage in the database
        List<PersonAreaPercentage> personAreaPercentageList = personAreaPercentageRepository.findAll();
        assertThat(personAreaPercentageList).hasSize(databaseSizeBeforeCreate + 1);
        PersonAreaPercentage testPersonAreaPercentage = personAreaPercentageList.get(personAreaPercentageList.size() - 1);
        assertThat(testPersonAreaPercentage.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createPersonAreaPercentageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personAreaPercentageRepository.findAll().size();

        // Create the PersonAreaPercentage with an existing ID
        PersonAreaPercentage existingPersonAreaPercentage = new PersonAreaPercentage();
        existingPersonAreaPercentage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonAreaPercentageMockMvc.perform(post("/api/person-area-percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonAreaPercentage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonAreaPercentage> personAreaPercentageList = personAreaPercentageRepository.findAll();
        assertThat(personAreaPercentageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonAreaPercentages() throws Exception {
        // Initialize the database
        personAreaPercentageRepository.saveAndFlush(personAreaPercentage);

        // Get all the personAreaPercentageList
        restPersonAreaPercentageMockMvc.perform(get("/api/person-area-percentages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personAreaPercentage.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonAreaPercentage() throws Exception {
        // Initialize the database
        personAreaPercentageRepository.saveAndFlush(personAreaPercentage);

        // Get the personAreaPercentage
        restPersonAreaPercentageMockMvc.perform(get("/api/person-area-percentages/{id}", personAreaPercentage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personAreaPercentage.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonAreaPercentage() throws Exception {
        // Get the personAreaPercentage
        restPersonAreaPercentageMockMvc.perform(get("/api/person-area-percentages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonAreaPercentage() throws Exception {
        // Initialize the database
        personAreaPercentageService.save(personAreaPercentage);

        int databaseSizeBeforeUpdate = personAreaPercentageRepository.findAll().size();

        // Update the personAreaPercentage
        PersonAreaPercentage updatedPersonAreaPercentage = personAreaPercentageRepository.findOne(personAreaPercentage.getId());
        updatedPersonAreaPercentage
                .percentage(UPDATED_PERCENTAGE);

        restPersonAreaPercentageMockMvc.perform(put("/api/person-area-percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonAreaPercentage)))
            .andExpect(status().isOk());

        // Validate the PersonAreaPercentage in the database
        List<PersonAreaPercentage> personAreaPercentageList = personAreaPercentageRepository.findAll();
        assertThat(personAreaPercentageList).hasSize(databaseSizeBeforeUpdate);
        PersonAreaPercentage testPersonAreaPercentage = personAreaPercentageList.get(personAreaPercentageList.size() - 1);
        assertThat(testPersonAreaPercentage.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonAreaPercentage() throws Exception {
        int databaseSizeBeforeUpdate = personAreaPercentageRepository.findAll().size();

        // Create the PersonAreaPercentage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonAreaPercentageMockMvc.perform(put("/api/person-area-percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personAreaPercentage)))
            .andExpect(status().isCreated());

        // Validate the PersonAreaPercentage in the database
        List<PersonAreaPercentage> personAreaPercentageList = personAreaPercentageRepository.findAll();
        assertThat(personAreaPercentageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonAreaPercentage() throws Exception {
        // Initialize the database
        personAreaPercentageService.save(personAreaPercentage);

        int databaseSizeBeforeDelete = personAreaPercentageRepository.findAll().size();

        // Get the personAreaPercentage
        restPersonAreaPercentageMockMvc.perform(delete("/api/person-area-percentages/{id}", personAreaPercentage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonAreaPercentage> personAreaPercentageList = personAreaPercentageRepository.findAll();
        assertThat(personAreaPercentageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
