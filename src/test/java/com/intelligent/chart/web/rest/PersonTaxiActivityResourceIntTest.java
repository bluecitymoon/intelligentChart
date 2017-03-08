package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonTaxiActivity;
import com.intelligent.chart.repository.PersonTaxiActivityRepository;
import com.intelligent.chart.service.PersonTaxiActivityService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.intelligent.chart.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonTaxiActivityResource REST controller.
 *
 * @see PersonTaxiActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonTaxiActivityResourceIntTest {

    private static final String DEFAULT_START_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_START_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_PAID_AMOUNT = 1F;
    private static final Float UPDATED_PAID_AMOUNT = 2F;

    @Inject
    private PersonTaxiActivityRepository personTaxiActivityRepository;

    @Inject
    private PersonTaxiActivityService personTaxiActivityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonTaxiActivityMockMvc;

    private PersonTaxiActivity personTaxiActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonTaxiActivityResource personTaxiActivityResource = new PersonTaxiActivityResource();
        ReflectionTestUtils.setField(personTaxiActivityResource, "personTaxiActivityService", personTaxiActivityService);
        this.restPersonTaxiActivityMockMvc = MockMvcBuilders.standaloneSetup(personTaxiActivityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonTaxiActivity createEntity(EntityManager em) {
        PersonTaxiActivity personTaxiActivity = new PersonTaxiActivity()
                .startPlace(DEFAULT_START_PLACE)
                .destination(DEFAULT_DESTINATION)
                .createDate(DEFAULT_CREATE_DATE)
                .paidAmount(DEFAULT_PAID_AMOUNT);
        return personTaxiActivity;
    }

    @Before
    public void initTest() {
        personTaxiActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonTaxiActivity() throws Exception {
        int databaseSizeBeforeCreate = personTaxiActivityRepository.findAll().size();

        // Create the PersonTaxiActivity

        restPersonTaxiActivityMockMvc.perform(post("/api/person-taxi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTaxiActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonTaxiActivity in the database
        List<PersonTaxiActivity> personTaxiActivityList = personTaxiActivityRepository.findAll();
        assertThat(personTaxiActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PersonTaxiActivity testPersonTaxiActivity = personTaxiActivityList.get(personTaxiActivityList.size() - 1);
        assertThat(testPersonTaxiActivity.getStartPlace()).isEqualTo(DEFAULT_START_PLACE);
        assertThat(testPersonTaxiActivity.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testPersonTaxiActivity.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonTaxiActivity.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void createPersonTaxiActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personTaxiActivityRepository.findAll().size();

        // Create the PersonTaxiActivity with an existing ID
        PersonTaxiActivity existingPersonTaxiActivity = new PersonTaxiActivity();
        existingPersonTaxiActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonTaxiActivityMockMvc.perform(post("/api/person-taxi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonTaxiActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonTaxiActivity> personTaxiActivityList = personTaxiActivityRepository.findAll();
        assertThat(personTaxiActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonTaxiActivities() throws Exception {
        // Initialize the database
        personTaxiActivityRepository.saveAndFlush(personTaxiActivity);

        // Get all the personTaxiActivityList
        restPersonTaxiActivityMockMvc.perform(get("/api/person-taxi-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personTaxiActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].startPlace").value(hasItem(DEFAULT_START_PLACE.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonTaxiActivity() throws Exception {
        // Initialize the database
        personTaxiActivityRepository.saveAndFlush(personTaxiActivity);

        // Get the personTaxiActivity
        restPersonTaxiActivityMockMvc.perform(get("/api/person-taxi-activities/{id}", personTaxiActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personTaxiActivity.getId().intValue()))
            .andExpect(jsonPath("$.startPlace").value(DEFAULT_START_PLACE.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonTaxiActivity() throws Exception {
        // Get the personTaxiActivity
        restPersonTaxiActivityMockMvc.perform(get("/api/person-taxi-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonTaxiActivity() throws Exception {
        // Initialize the database
        personTaxiActivityService.save(personTaxiActivity);

        int databaseSizeBeforeUpdate = personTaxiActivityRepository.findAll().size();

        // Update the personTaxiActivity
        PersonTaxiActivity updatedPersonTaxiActivity = personTaxiActivityRepository.findOne(personTaxiActivity.getId());
        updatedPersonTaxiActivity
                .startPlace(UPDATED_START_PLACE)
                .destination(UPDATED_DESTINATION)
                .createDate(UPDATED_CREATE_DATE)
                .paidAmount(UPDATED_PAID_AMOUNT);

        restPersonTaxiActivityMockMvc.perform(put("/api/person-taxi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonTaxiActivity)))
            .andExpect(status().isOk());

        // Validate the PersonTaxiActivity in the database
        List<PersonTaxiActivity> personTaxiActivityList = personTaxiActivityRepository.findAll();
        assertThat(personTaxiActivityList).hasSize(databaseSizeBeforeUpdate);
        PersonTaxiActivity testPersonTaxiActivity = personTaxiActivityList.get(personTaxiActivityList.size() - 1);
        assertThat(testPersonTaxiActivity.getStartPlace()).isEqualTo(UPDATED_START_PLACE);
        assertThat(testPersonTaxiActivity.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testPersonTaxiActivity.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonTaxiActivity.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonTaxiActivity() throws Exception {
        int databaseSizeBeforeUpdate = personTaxiActivityRepository.findAll().size();

        // Create the PersonTaxiActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonTaxiActivityMockMvc.perform(put("/api/person-taxi-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTaxiActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonTaxiActivity in the database
        List<PersonTaxiActivity> personTaxiActivityList = personTaxiActivityRepository.findAll();
        assertThat(personTaxiActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonTaxiActivity() throws Exception {
        // Initialize the database
        personTaxiActivityService.save(personTaxiActivity);

        int databaseSizeBeforeDelete = personTaxiActivityRepository.findAll().size();

        // Get the personTaxiActivity
        restPersonTaxiActivityMockMvc.perform(delete("/api/person-taxi-activities/{id}", personTaxiActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonTaxiActivity> personTaxiActivityList = personTaxiActivityRepository.findAll();
        assertThat(personTaxiActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
