package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonNetworkTexiActivity;
import com.intelligent.chart.repository.PersonNetworkTexiActivityRepository;
import com.intelligent.chart.service.PersonNetworkTexiActivityService;

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
 * Test class for the PersonNetworkTexiActivityResource REST controller.
 *
 * @see PersonNetworkTexiActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonNetworkTexiActivityResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonNetworkTexiActivityRepository personNetworkTexiActivityRepository;

    @Inject
    private PersonNetworkTexiActivityService personNetworkTexiActivityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonNetworkTexiActivityMockMvc;

    private PersonNetworkTexiActivity personNetworkTexiActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonNetworkTexiActivityResource personNetworkTexiActivityResource = new PersonNetworkTexiActivityResource();
        ReflectionTestUtils.setField(personNetworkTexiActivityResource, "personNetworkTexiActivityService", personNetworkTexiActivityService);
        this.restPersonNetworkTexiActivityMockMvc = MockMvcBuilders.standaloneSetup(personNetworkTexiActivityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonNetworkTexiActivity createEntity(EntityManager em) {
        PersonNetworkTexiActivity personNetworkTexiActivity = new PersonNetworkTexiActivity()
                .count(DEFAULT_COUNT);
        return personNetworkTexiActivity;
    }

    @Before
    public void initTest() {
        personNetworkTexiActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonNetworkTexiActivity() throws Exception {
        int databaseSizeBeforeCreate = personNetworkTexiActivityRepository.findAll().size();

        // Create the PersonNetworkTexiActivity

        restPersonNetworkTexiActivityMockMvc.perform(post("/api/person-network-texi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkTexiActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkTexiActivity in the database
        List<PersonNetworkTexiActivity> personNetworkTexiActivityList = personNetworkTexiActivityRepository.findAll();
        assertThat(personNetworkTexiActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PersonNetworkTexiActivity testPersonNetworkTexiActivity = personNetworkTexiActivityList.get(personNetworkTexiActivityList.size() - 1);
        assertThat(testPersonNetworkTexiActivity.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonNetworkTexiActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personNetworkTexiActivityRepository.findAll().size();

        // Create the PersonNetworkTexiActivity with an existing ID
        PersonNetworkTexiActivity existingPersonNetworkTexiActivity = new PersonNetworkTexiActivity();
        existingPersonNetworkTexiActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonNetworkTexiActivityMockMvc.perform(post("/api/person-network-texi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonNetworkTexiActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonNetworkTexiActivity> personNetworkTexiActivityList = personNetworkTexiActivityRepository.findAll();
        assertThat(personNetworkTexiActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonNetworkTexiActivities() throws Exception {
        // Initialize the database
        personNetworkTexiActivityRepository.saveAndFlush(personNetworkTexiActivity);

        // Get all the personNetworkTexiActivityList
        restPersonNetworkTexiActivityMockMvc.perform(get("/api/person-network-texi-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personNetworkTexiActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonNetworkTexiActivity() throws Exception {
        // Initialize the database
        personNetworkTexiActivityRepository.saveAndFlush(personNetworkTexiActivity);

        // Get the personNetworkTexiActivity
        restPersonNetworkTexiActivityMockMvc.perform(get("/api/person-network-texi-activities/{id}", personNetworkTexiActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personNetworkTexiActivity.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonNetworkTexiActivity() throws Exception {
        // Get the personNetworkTexiActivity
        restPersonNetworkTexiActivityMockMvc.perform(get("/api/person-network-texi-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonNetworkTexiActivity() throws Exception {
        // Initialize the database
        personNetworkTexiActivityService.save(personNetworkTexiActivity);

        int databaseSizeBeforeUpdate = personNetworkTexiActivityRepository.findAll().size();

        // Update the personNetworkTexiActivity
        PersonNetworkTexiActivity updatedPersonNetworkTexiActivity = personNetworkTexiActivityRepository.findOne(personNetworkTexiActivity.getId());
        updatedPersonNetworkTexiActivity
                .count(UPDATED_COUNT);

        restPersonNetworkTexiActivityMockMvc.perform(put("/api/person-network-texi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonNetworkTexiActivity)))
            .andExpect(status().isOk());

        // Validate the PersonNetworkTexiActivity in the database
        List<PersonNetworkTexiActivity> personNetworkTexiActivityList = personNetworkTexiActivityRepository.findAll();
        assertThat(personNetworkTexiActivityList).hasSize(databaseSizeBeforeUpdate);
        PersonNetworkTexiActivity testPersonNetworkTexiActivity = personNetworkTexiActivityList.get(personNetworkTexiActivityList.size() - 1);
        assertThat(testPersonNetworkTexiActivity.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonNetworkTexiActivity() throws Exception {
        int databaseSizeBeforeUpdate = personNetworkTexiActivityRepository.findAll().size();

        // Create the PersonNetworkTexiActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonNetworkTexiActivityMockMvc.perform(put("/api/person-network-texi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkTexiActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkTexiActivity in the database
        List<PersonNetworkTexiActivity> personNetworkTexiActivityList = personNetworkTexiActivityRepository.findAll();
        assertThat(personNetworkTexiActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonNetworkTexiActivity() throws Exception {
        // Initialize the database
        personNetworkTexiActivityService.save(personNetworkTexiActivity);

        int databaseSizeBeforeDelete = personNetworkTexiActivityRepository.findAll().size();

        // Get the personNetworkTexiActivity
        restPersonNetworkTexiActivityMockMvc.perform(delete("/api/person-network-texi-activities/{id}", personNetworkTexiActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonNetworkTexiActivity> personNetworkTexiActivityList = personNetworkTexiActivityRepository.findAll();
        assertThat(personNetworkTexiActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
