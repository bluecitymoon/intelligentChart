package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonWordCloud;
import com.intelligent.chart.repository.PersonWordCloudRepository;
import com.intelligent.chart.service.PersonWordCloudService;

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
 * Test class for the PersonWordCloudResource REST controller.
 *
 * @see PersonWordCloudResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonWordCloudResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonWordCloudRepository personWordCloudRepository;

    @Inject
    private PersonWordCloudService personWordCloudService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonWordCloudMockMvc;

    private PersonWordCloud personWordCloud;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonWordCloudResource personWordCloudResource = new PersonWordCloudResource();
        ReflectionTestUtils.setField(personWordCloudResource, "personWordCloudService", personWordCloudService);
        this.restPersonWordCloudMockMvc = MockMvcBuilders.standaloneSetup(personWordCloudResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonWordCloud createEntity(EntityManager em) {
        PersonWordCloud personWordCloud = new PersonWordCloud()
                .count(DEFAULT_COUNT);
        return personWordCloud;
    }

    @Before
    public void initTest() {
        personWordCloud = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonWordCloud() throws Exception {
        int databaseSizeBeforeCreate = personWordCloudRepository.findAll().size();

        // Create the PersonWordCloud

        restPersonWordCloudMockMvc.perform(post("/api/person-word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personWordCloud)))
            .andExpect(status().isCreated());

        // Validate the PersonWordCloud in the database
        List<PersonWordCloud> personWordCloudList = personWordCloudRepository.findAll();
        assertThat(personWordCloudList).hasSize(databaseSizeBeforeCreate + 1);
        PersonWordCloud testPersonWordCloud = personWordCloudList.get(personWordCloudList.size() - 1);
        assertThat(testPersonWordCloud.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonWordCloudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personWordCloudRepository.findAll().size();

        // Create the PersonWordCloud with an existing ID
        PersonWordCloud existingPersonWordCloud = new PersonWordCloud();
        existingPersonWordCloud.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonWordCloudMockMvc.perform(post("/api/person-word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonWordCloud)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonWordCloud> personWordCloudList = personWordCloudRepository.findAll();
        assertThat(personWordCloudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonWordClouds() throws Exception {
        // Initialize the database
        personWordCloudRepository.saveAndFlush(personWordCloud);

        // Get all the personWordCloudList
        restPersonWordCloudMockMvc.perform(get("/api/person-word-clouds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personWordCloud.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonWordCloud() throws Exception {
        // Initialize the database
        personWordCloudRepository.saveAndFlush(personWordCloud);

        // Get the personWordCloud
        restPersonWordCloudMockMvc.perform(get("/api/person-word-clouds/{id}", personWordCloud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personWordCloud.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonWordCloud() throws Exception {
        // Get the personWordCloud
        restPersonWordCloudMockMvc.perform(get("/api/person-word-clouds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonWordCloud() throws Exception {
        // Initialize the database
        personWordCloudService.save(personWordCloud);

        int databaseSizeBeforeUpdate = personWordCloudRepository.findAll().size();

        // Update the personWordCloud
        PersonWordCloud updatedPersonWordCloud = personWordCloudRepository.findOne(personWordCloud.getId());
        updatedPersonWordCloud
                .count(UPDATED_COUNT);

        restPersonWordCloudMockMvc.perform(put("/api/person-word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonWordCloud)))
            .andExpect(status().isOk());

        // Validate the PersonWordCloud in the database
        List<PersonWordCloud> personWordCloudList = personWordCloudRepository.findAll();
        assertThat(personWordCloudList).hasSize(databaseSizeBeforeUpdate);
        PersonWordCloud testPersonWordCloud = personWordCloudList.get(personWordCloudList.size() - 1);
        assertThat(testPersonWordCloud.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonWordCloud() throws Exception {
        int databaseSizeBeforeUpdate = personWordCloudRepository.findAll().size();

        // Create the PersonWordCloud

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonWordCloudMockMvc.perform(put("/api/person-word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personWordCloud)))
            .andExpect(status().isCreated());

        // Validate the PersonWordCloud in the database
        List<PersonWordCloud> personWordCloudList = personWordCloudRepository.findAll();
        assertThat(personWordCloudList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonWordCloud() throws Exception {
        // Initialize the database
        personWordCloudService.save(personWordCloud);

        int databaseSizeBeforeDelete = personWordCloudRepository.findAll().size();

        // Get the personWordCloud
        restPersonWordCloudMockMvc.perform(delete("/api/person-word-clouds/{id}", personWordCloud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonWordCloud> personWordCloudList = personWordCloudRepository.findAll();
        assertThat(personWordCloudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
