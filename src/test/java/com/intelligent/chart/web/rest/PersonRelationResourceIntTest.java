package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonRelation;
import com.intelligent.chart.repository.PersonRelationRepository;
import com.intelligent.chart.service.PersonRelationService;

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
 * Test class for the PersonRelationResource REST controller.
 *
 * @see PersonRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonRelationResourceIntTest {

    @Inject
    private PersonRelationRepository personRelationRepository;

    @Inject
    private PersonRelationService personRelationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonRelationMockMvc;

    private PersonRelation personRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonRelationResource personRelationResource = new PersonRelationResource();
        ReflectionTestUtils.setField(personRelationResource, "personRelationService", personRelationService);
        this.restPersonRelationMockMvc = MockMvcBuilders.standaloneSetup(personRelationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonRelation createEntity(EntityManager em) {
        PersonRelation personRelation = new PersonRelation();
        return personRelation;
    }

    @Before
    public void initTest() {
        personRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonRelation() throws Exception {
        int databaseSizeBeforeCreate = personRelationRepository.findAll().size();

        // Create the PersonRelation

        restPersonRelationMockMvc.perform(post("/api/person-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRelation)))
            .andExpect(status().isCreated());

        // Validate the PersonRelation in the database
        List<PersonRelation> personRelationList = personRelationRepository.findAll();
        assertThat(personRelationList).hasSize(databaseSizeBeforeCreate + 1);
        PersonRelation testPersonRelation = personRelationList.get(personRelationList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRelationRepository.findAll().size();

        // Create the PersonRelation with an existing ID
        PersonRelation existingPersonRelation = new PersonRelation();
        existingPersonRelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonRelationMockMvc.perform(post("/api/person-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonRelation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonRelation> personRelationList = personRelationRepository.findAll();
        assertThat(personRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonRelations() throws Exception {
        // Initialize the database
        personRelationRepository.saveAndFlush(personRelation);

        // Get all the personRelationList
        restPersonRelationMockMvc.perform(get("/api/person-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personRelation.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonRelation() throws Exception {
        // Initialize the database
        personRelationRepository.saveAndFlush(personRelation);

        // Get the personRelation
        restPersonRelationMockMvc.perform(get("/api/person-relations/{id}", personRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personRelation.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonRelation() throws Exception {
        // Get the personRelation
        restPersonRelationMockMvc.perform(get("/api/person-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonRelation() throws Exception {
        // Initialize the database
        personRelationService.save(personRelation);

        int databaseSizeBeforeUpdate = personRelationRepository.findAll().size();

        // Update the personRelation
        PersonRelation updatedPersonRelation = personRelationRepository.findOne(personRelation.getId());

        restPersonRelationMockMvc.perform(put("/api/person-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonRelation)))
            .andExpect(status().isOk());

        // Validate the PersonRelation in the database
        List<PersonRelation> personRelationList = personRelationRepository.findAll();
        assertThat(personRelationList).hasSize(databaseSizeBeforeUpdate);
        PersonRelation testPersonRelation = personRelationList.get(personRelationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonRelation() throws Exception {
        int databaseSizeBeforeUpdate = personRelationRepository.findAll().size();

        // Create the PersonRelation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonRelationMockMvc.perform(put("/api/person-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRelation)))
            .andExpect(status().isCreated());

        // Validate the PersonRelation in the database
        List<PersonRelation> personRelationList = personRelationRepository.findAll();
        assertThat(personRelationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonRelation() throws Exception {
        // Initialize the database
        personRelationService.save(personRelation);

        int databaseSizeBeforeDelete = personRelationRepository.findAll().size();

        // Get the personRelation
        restPersonRelationMockMvc.perform(delete("/api/person-relations/{id}", personRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonRelation> personRelationList = personRelationRepository.findAll();
        assertThat(personRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
