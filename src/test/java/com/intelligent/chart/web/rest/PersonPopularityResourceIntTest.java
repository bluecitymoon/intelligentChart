package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonPopularity;
import com.intelligent.chart.repository.PersonPopularityRepository;
import com.intelligent.chart.service.PersonPopularityService;

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
 * Test class for the PersonPopularityResource REST controller.
 *
 * @see PersonPopularityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonPopularityResourceIntTest {

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Inject
    private PersonPopularityRepository personPopularityRepository;

    @Inject
    private PersonPopularityService personPopularityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonPopularityMockMvc;

    private PersonPopularity personPopularity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonPopularityResource personPopularityResource = new PersonPopularityResource();
        ReflectionTestUtils.setField(personPopularityResource, "personPopularityService", personPopularityService);
        this.restPersonPopularityMockMvc = MockMvcBuilders.standaloneSetup(personPopularityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPopularity createEntity(EntityManager em) {
        PersonPopularity personPopularity = new PersonPopularity()
                .percentage(DEFAULT_PERCENTAGE);
        return personPopularity;
    }

    @Before
    public void initTest() {
        personPopularity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPopularity() throws Exception {
        int databaseSizeBeforeCreate = personPopularityRepository.findAll().size();

        // Create the PersonPopularity

        restPersonPopularityMockMvc.perform(post("/api/person-popularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPopularity)))
            .andExpect(status().isCreated());

        // Validate the PersonPopularity in the database
        List<PersonPopularity> personPopularityList = personPopularityRepository.findAll();
        assertThat(personPopularityList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPopularity testPersonPopularity = personPopularityList.get(personPopularityList.size() - 1);
        assertThat(testPersonPopularity.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createPersonPopularityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPopularityRepository.findAll().size();

        // Create the PersonPopularity with an existing ID
        PersonPopularity existingPersonPopularity = new PersonPopularity();
        existingPersonPopularity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPopularityMockMvc.perform(post("/api/person-popularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonPopularity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonPopularity> personPopularityList = personPopularityRepository.findAll();
        assertThat(personPopularityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonPopularities() throws Exception {
        // Initialize the database
        personPopularityRepository.saveAndFlush(personPopularity);

        // Get all the personPopularityList
        restPersonPopularityMockMvc.perform(get("/api/person-popularities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPopularity.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonPopularity() throws Exception {
        // Initialize the database
        personPopularityRepository.saveAndFlush(personPopularity);

        // Get the personPopularity
        restPersonPopularityMockMvc.perform(get("/api/person-popularities/{id}", personPopularity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPopularity.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPopularity() throws Exception {
        // Get the personPopularity
        restPersonPopularityMockMvc.perform(get("/api/person-popularities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPopularity() throws Exception {
        // Initialize the database
        personPopularityService.save(personPopularity);

        int databaseSizeBeforeUpdate = personPopularityRepository.findAll().size();

        // Update the personPopularity
        PersonPopularity updatedPersonPopularity = personPopularityRepository.findOne(personPopularity.getId());
        updatedPersonPopularity
                .percentage(UPDATED_PERCENTAGE);

        restPersonPopularityMockMvc.perform(put("/api/person-popularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonPopularity)))
            .andExpect(status().isOk());

        // Validate the PersonPopularity in the database
        List<PersonPopularity> personPopularityList = personPopularityRepository.findAll();
        assertThat(personPopularityList).hasSize(databaseSizeBeforeUpdate);
        PersonPopularity testPersonPopularity = personPopularityList.get(personPopularityList.size() - 1);
        assertThat(testPersonPopularity.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPopularity() throws Exception {
        int databaseSizeBeforeUpdate = personPopularityRepository.findAll().size();

        // Create the PersonPopularity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonPopularityMockMvc.perform(put("/api/person-popularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPopularity)))
            .andExpect(status().isCreated());

        // Validate the PersonPopularity in the database
        List<PersonPopularity> personPopularityList = personPopularityRepository.findAll();
        assertThat(personPopularityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonPopularity() throws Exception {
        // Initialize the database
        personPopularityService.save(personPopularity);

        int databaseSizeBeforeDelete = personPopularityRepository.findAll().size();

        // Get the personPopularity
        restPersonPopularityMockMvc.perform(delete("/api/person-popularities/{id}", personPopularity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPopularity> personPopularityList = personPopularityRepository.findAll();
        assertThat(personPopularityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
