package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonCreditCardActivity;
import com.intelligent.chart.repository.PersonCreditCardActivityRepository;
import com.intelligent.chart.service.PersonCreditCardActivityService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonCreditCardActivityResource REST controller.
 *
 * @see PersonCreditCardActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonCreditCardActivityResourceIntTest {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PersonCreditCardActivityRepository personCreditCardActivityRepository;

    @Inject
    private PersonCreditCardActivityService personCreditCardActivityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonCreditCardActivityMockMvc;

    private PersonCreditCardActivity personCreditCardActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonCreditCardActivityResource personCreditCardActivityResource = new PersonCreditCardActivityResource();
        ReflectionTestUtils.setField(personCreditCardActivityResource, "personCreditCardActivityService", personCreditCardActivityService);
        this.restPersonCreditCardActivityMockMvc = MockMvcBuilders.standaloneSetup(personCreditCardActivityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonCreditCardActivity createEntity(EntityManager em) {
        PersonCreditCardActivity personCreditCardActivity = new PersonCreditCardActivity()
                .amount(DEFAULT_AMOUNT)
                .createDate(DEFAULT_CREATE_DATE);
        return personCreditCardActivity;
    }

    @Before
    public void initTest() {
        personCreditCardActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonCreditCardActivity() throws Exception {
        int databaseSizeBeforeCreate = personCreditCardActivityRepository.findAll().size();

        // Create the PersonCreditCardActivity

        restPersonCreditCardActivityMockMvc.perform(post("/api/person-credit-card-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personCreditCardActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonCreditCardActivity in the database
        List<PersonCreditCardActivity> personCreditCardActivityList = personCreditCardActivityRepository.findAll();
        assertThat(personCreditCardActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PersonCreditCardActivity testPersonCreditCardActivity = personCreditCardActivityList.get(personCreditCardActivityList.size() - 1);
        assertThat(testPersonCreditCardActivity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPersonCreditCardActivity.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createPersonCreditCardActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personCreditCardActivityRepository.findAll().size();

        // Create the PersonCreditCardActivity with an existing ID
        PersonCreditCardActivity existingPersonCreditCardActivity = new PersonCreditCardActivity();
        existingPersonCreditCardActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonCreditCardActivityMockMvc.perform(post("/api/person-credit-card-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonCreditCardActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonCreditCardActivity> personCreditCardActivityList = personCreditCardActivityRepository.findAll();
        assertThat(personCreditCardActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonCreditCardActivities() throws Exception {
        // Initialize the database
        personCreditCardActivityRepository.saveAndFlush(personCreditCardActivity);

        // Get all the personCreditCardActivityList
        restPersonCreditCardActivityMockMvc.perform(get("/api/person-credit-card-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personCreditCardActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPersonCreditCardActivity() throws Exception {
        // Initialize the database
        personCreditCardActivityRepository.saveAndFlush(personCreditCardActivity);

        // Get the personCreditCardActivity
        restPersonCreditCardActivityMockMvc.perform(get("/api/person-credit-card-activities/{id}", personCreditCardActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personCreditCardActivity.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonCreditCardActivity() throws Exception {
        // Get the personCreditCardActivity
        restPersonCreditCardActivityMockMvc.perform(get("/api/person-credit-card-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonCreditCardActivity() throws Exception {
        // Initialize the database
        personCreditCardActivityService.save(personCreditCardActivity);

        int databaseSizeBeforeUpdate = personCreditCardActivityRepository.findAll().size();

        // Update the personCreditCardActivity
        PersonCreditCardActivity updatedPersonCreditCardActivity = personCreditCardActivityRepository.findOne(personCreditCardActivity.getId());
        updatedPersonCreditCardActivity
                .amount(UPDATED_AMOUNT)
                .createDate(UPDATED_CREATE_DATE);

        restPersonCreditCardActivityMockMvc.perform(put("/api/person-credit-card-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonCreditCardActivity)))
            .andExpect(status().isOk());

        // Validate the PersonCreditCardActivity in the database
        List<PersonCreditCardActivity> personCreditCardActivityList = personCreditCardActivityRepository.findAll();
        assertThat(personCreditCardActivityList).hasSize(databaseSizeBeforeUpdate);
        PersonCreditCardActivity testPersonCreditCardActivity = personCreditCardActivityList.get(personCreditCardActivityList.size() - 1);
        assertThat(testPersonCreditCardActivity.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPersonCreditCardActivity.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonCreditCardActivity() throws Exception {
        int databaseSizeBeforeUpdate = personCreditCardActivityRepository.findAll().size();

        // Create the PersonCreditCardActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonCreditCardActivityMockMvc.perform(put("/api/person-credit-card-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personCreditCardActivity)))
            .andExpect(status().isCreated());

        // Validate the PersonCreditCardActivity in the database
        List<PersonCreditCardActivity> personCreditCardActivityList = personCreditCardActivityRepository.findAll();
        assertThat(personCreditCardActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonCreditCardActivity() throws Exception {
        // Initialize the database
        personCreditCardActivityService.save(personCreditCardActivity);

        int databaseSizeBeforeDelete = personCreditCardActivityRepository.findAll().size();

        // Get the personCreditCardActivity
        restPersonCreditCardActivityMockMvc.perform(delete("/api/person-credit-card-activities/{id}", personCreditCardActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonCreditCardActivity> personCreditCardActivityList = personCreditCardActivityRepository.findAll();
        assertThat(personCreditCardActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
