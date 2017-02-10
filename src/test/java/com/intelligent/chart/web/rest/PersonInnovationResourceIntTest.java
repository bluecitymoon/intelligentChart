package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonInnovation;
import com.intelligent.chart.repository.PersonInnovationRepository;
import com.intelligent.chart.service.PersonInnovationService;

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
 * Test class for the PersonInnovationResource REST controller.
 *
 * @see PersonInnovationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonInnovationResourceIntTest {

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Inject
    private PersonInnovationRepository personInnovationRepository;

    @Inject
    private PersonInnovationService personInnovationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonInnovationMockMvc;

    private PersonInnovation personInnovation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonInnovationResource personInnovationResource = new PersonInnovationResource();
        ReflectionTestUtils.setField(personInnovationResource, "personInnovationService", personInnovationService);
        this.restPersonInnovationMockMvc = MockMvcBuilders.standaloneSetup(personInnovationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInnovation createEntity(EntityManager em) {
        PersonInnovation personInnovation = new PersonInnovation()
                .percentage(DEFAULT_PERCENTAGE);
        return personInnovation;
    }

    @Before
    public void initTest() {
        personInnovation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonInnovation() throws Exception {
        int databaseSizeBeforeCreate = personInnovationRepository.findAll().size();

        // Create the PersonInnovation

        restPersonInnovationMockMvc.perform(post("/api/person-innovations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInnovation)))
            .andExpect(status().isCreated());

        // Validate the PersonInnovation in the database
        List<PersonInnovation> personInnovationList = personInnovationRepository.findAll();
        assertThat(personInnovationList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInnovation testPersonInnovation = personInnovationList.get(personInnovationList.size() - 1);
        assertThat(testPersonInnovation.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createPersonInnovationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personInnovationRepository.findAll().size();

        // Create the PersonInnovation with an existing ID
        PersonInnovation existingPersonInnovation = new PersonInnovation();
        existingPersonInnovation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInnovationMockMvc.perform(post("/api/person-innovations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonInnovation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonInnovation> personInnovationList = personInnovationRepository.findAll();
        assertThat(personInnovationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonInnovations() throws Exception {
        // Initialize the database
        personInnovationRepository.saveAndFlush(personInnovation);

        // Get all the personInnovationList
        restPersonInnovationMockMvc.perform(get("/api/person-innovations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInnovation.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonInnovation() throws Exception {
        // Initialize the database
        personInnovationRepository.saveAndFlush(personInnovation);

        // Get the personInnovation
        restPersonInnovationMockMvc.perform(get("/api/person-innovations/{id}", personInnovation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personInnovation.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonInnovation() throws Exception {
        // Get the personInnovation
        restPersonInnovationMockMvc.perform(get("/api/person-innovations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonInnovation() throws Exception {
        // Initialize the database
        personInnovationService.save(personInnovation);

        int databaseSizeBeforeUpdate = personInnovationRepository.findAll().size();

        // Update the personInnovation
        PersonInnovation updatedPersonInnovation = personInnovationRepository.findOne(personInnovation.getId());
        updatedPersonInnovation
                .percentage(UPDATED_PERCENTAGE);

        restPersonInnovationMockMvc.perform(put("/api/person-innovations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonInnovation)))
            .andExpect(status().isOk());

        // Validate the PersonInnovation in the database
        List<PersonInnovation> personInnovationList = personInnovationRepository.findAll();
        assertThat(personInnovationList).hasSize(databaseSizeBeforeUpdate);
        PersonInnovation testPersonInnovation = personInnovationList.get(personInnovationList.size() - 1);
        assertThat(testPersonInnovation.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonInnovation() throws Exception {
        int databaseSizeBeforeUpdate = personInnovationRepository.findAll().size();

        // Create the PersonInnovation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonInnovationMockMvc.perform(put("/api/person-innovations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInnovation)))
            .andExpect(status().isCreated());

        // Validate the PersonInnovation in the database
        List<PersonInnovation> personInnovationList = personInnovationRepository.findAll();
        assertThat(personInnovationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonInnovation() throws Exception {
        // Initialize the database
        personInnovationService.save(personInnovation);

        int databaseSizeBeforeDelete = personInnovationRepository.findAll().size();

        // Get the personInnovation
        restPersonInnovationMockMvc.perform(delete("/api/person-innovations/{id}", personInnovation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonInnovation> personInnovationList = personInnovationRepository.findAll();
        assertThat(personInnovationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
