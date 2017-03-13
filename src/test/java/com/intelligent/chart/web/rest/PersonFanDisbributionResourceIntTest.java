package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFanDisbribution;
import com.intelligent.chart.repository.PersonFanDisbributionRepository;
import com.intelligent.chart.service.PersonFanDisbributionService;

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
 * Test class for the PersonFanDisbributionResource REST controller.
 *
 * @see PersonFanDisbributionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFanDisbributionResourceIntTest {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Inject
    private PersonFanDisbributionRepository personFanDisbributionRepository;

    @Inject
    private PersonFanDisbributionService personFanDisbributionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFanDisbributionMockMvc;

    private PersonFanDisbribution personFanDisbribution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFanDisbributionResource personFanDisbributionResource = new PersonFanDisbributionResource();
        ReflectionTestUtils.setField(personFanDisbributionResource, "personFanDisbributionService", personFanDisbributionService);
        this.restPersonFanDisbributionMockMvc = MockMvcBuilders.standaloneSetup(personFanDisbributionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFanDisbribution createEntity(EntityManager em) {
        PersonFanDisbribution personFanDisbribution = new PersonFanDisbribution()
                .count(DEFAULT_COUNT);
        return personFanDisbribution;
    }

    @Before
    public void initTest() {
        personFanDisbribution = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFanDisbribution() throws Exception {
        int databaseSizeBeforeCreate = personFanDisbributionRepository.findAll().size();

        // Create the PersonFanDisbribution

        restPersonFanDisbributionMockMvc.perform(post("/api/person-fan-disbributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanDisbribution)))
            .andExpect(status().isCreated());

        // Validate the PersonFanDisbribution in the database
        List<PersonFanDisbribution> personFanDisbributionList = personFanDisbributionRepository.findAll();
        assertThat(personFanDisbributionList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFanDisbribution testPersonFanDisbribution = personFanDisbributionList.get(personFanDisbributionList.size() - 1);
        assertThat(testPersonFanDisbribution.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFanDisbributionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFanDisbributionRepository.findAll().size();

        // Create the PersonFanDisbribution with an existing ID
        PersonFanDisbribution existingPersonFanDisbribution = new PersonFanDisbribution();
        existingPersonFanDisbribution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFanDisbributionMockMvc.perform(post("/api/person-fan-disbributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFanDisbribution)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFanDisbribution> personFanDisbributionList = personFanDisbributionRepository.findAll();
        assertThat(personFanDisbributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFanDisbributions() throws Exception {
        // Initialize the database
        personFanDisbributionRepository.saveAndFlush(personFanDisbribution);

        // Get all the personFanDisbributionList
        restPersonFanDisbributionMockMvc.perform(get("/api/person-fan-disbributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFanDisbribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFanDisbribution() throws Exception {
        // Initialize the database
        personFanDisbributionRepository.saveAndFlush(personFanDisbribution);

        // Get the personFanDisbribution
        restPersonFanDisbributionMockMvc.perform(get("/api/person-fan-disbributions/{id}", personFanDisbribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFanDisbribution.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFanDisbribution() throws Exception {
        // Get the personFanDisbribution
        restPersonFanDisbributionMockMvc.perform(get("/api/person-fan-disbributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFanDisbribution() throws Exception {
        // Initialize the database
        personFanDisbributionService.save(personFanDisbribution);

        int databaseSizeBeforeUpdate = personFanDisbributionRepository.findAll().size();

        // Update the personFanDisbribution
        PersonFanDisbribution updatedPersonFanDisbribution = personFanDisbributionRepository.findOne(personFanDisbribution.getId());
        updatedPersonFanDisbribution
                .count(UPDATED_COUNT);

        restPersonFanDisbributionMockMvc.perform(put("/api/person-fan-disbributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFanDisbribution)))
            .andExpect(status().isOk());

        // Validate the PersonFanDisbribution in the database
        List<PersonFanDisbribution> personFanDisbributionList = personFanDisbributionRepository.findAll();
        assertThat(personFanDisbributionList).hasSize(databaseSizeBeforeUpdate);
        PersonFanDisbribution testPersonFanDisbribution = personFanDisbributionList.get(personFanDisbributionList.size() - 1);
        assertThat(testPersonFanDisbribution.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFanDisbribution() throws Exception {
        int databaseSizeBeforeUpdate = personFanDisbributionRepository.findAll().size();

        // Create the PersonFanDisbribution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFanDisbributionMockMvc.perform(put("/api/person-fan-disbributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanDisbribution)))
            .andExpect(status().isCreated());

        // Validate the PersonFanDisbribution in the database
        List<PersonFanDisbribution> personFanDisbributionList = personFanDisbributionRepository.findAll();
        assertThat(personFanDisbributionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFanDisbribution() throws Exception {
        // Initialize the database
        personFanDisbributionService.save(personFanDisbribution);

        int databaseSizeBeforeDelete = personFanDisbributionRepository.findAll().size();

        // Get the personFanDisbribution
        restPersonFanDisbributionMockMvc.perform(delete("/api/person-fan-disbributions/{id}", personFanDisbribution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFanDisbribution> personFanDisbributionList = personFanDisbributionRepository.findAll();
        assertThat(personFanDisbributionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
