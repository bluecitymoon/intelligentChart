package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonPaidNetworkDebit;
import com.intelligent.chart.repository.PersonPaidNetworkDebitRepository;
import com.intelligent.chart.service.PersonPaidNetworkDebitService;

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
 * Test class for the PersonPaidNetworkDebitResource REST controller.
 *
 * @see PersonPaidNetworkDebitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonPaidNetworkDebitResourceIntTest {

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    @Inject
    private PersonPaidNetworkDebitRepository personPaidNetworkDebitRepository;

    @Inject
    private PersonPaidNetworkDebitService personPaidNetworkDebitService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonPaidNetworkDebitMockMvc;

    private PersonPaidNetworkDebit personPaidNetworkDebit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonPaidNetworkDebitResource personPaidNetworkDebitResource = new PersonPaidNetworkDebitResource();
        ReflectionTestUtils.setField(personPaidNetworkDebitResource, "personPaidNetworkDebitService", personPaidNetworkDebitService);
        this.restPersonPaidNetworkDebitMockMvc = MockMvcBuilders.standaloneSetup(personPaidNetworkDebitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPaidNetworkDebit createEntity(EntityManager em) {
        PersonPaidNetworkDebit personPaidNetworkDebit = new PersonPaidNetworkDebit()
                .createDate(DEFAULT_CREATE_DATE)
                .amount(DEFAULT_AMOUNT);
        return personPaidNetworkDebit;
    }

    @Before
    public void initTest() {
        personPaidNetworkDebit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPaidNetworkDebit() throws Exception {
        int databaseSizeBeforeCreate = personPaidNetworkDebitRepository.findAll().size();

        // Create the PersonPaidNetworkDebit

        restPersonPaidNetworkDebitMockMvc.perform(post("/api/person-paid-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPaidNetworkDebit)))
            .andExpect(status().isCreated());

        // Validate the PersonPaidNetworkDebit in the database
        List<PersonPaidNetworkDebit> personPaidNetworkDebitList = personPaidNetworkDebitRepository.findAll();
        assertThat(personPaidNetworkDebitList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPaidNetworkDebit testPersonPaidNetworkDebit = personPaidNetworkDebitList.get(personPaidNetworkDebitList.size() - 1);
        assertThat(testPersonPaidNetworkDebit.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonPaidNetworkDebit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPersonPaidNetworkDebitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPaidNetworkDebitRepository.findAll().size();

        // Create the PersonPaidNetworkDebit with an existing ID
        PersonPaidNetworkDebit existingPersonPaidNetworkDebit = new PersonPaidNetworkDebit();
        existingPersonPaidNetworkDebit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPaidNetworkDebitMockMvc.perform(post("/api/person-paid-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonPaidNetworkDebit)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonPaidNetworkDebit> personPaidNetworkDebitList = personPaidNetworkDebitRepository.findAll();
        assertThat(personPaidNetworkDebitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonPaidNetworkDebits() throws Exception {
        // Initialize the database
        personPaidNetworkDebitRepository.saveAndFlush(personPaidNetworkDebit);

        // Get all the personPaidNetworkDebitList
        restPersonPaidNetworkDebitMockMvc.perform(get("/api/person-paid-network-debits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPaidNetworkDebit.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonPaidNetworkDebit() throws Exception {
        // Initialize the database
        personPaidNetworkDebitRepository.saveAndFlush(personPaidNetworkDebit);

        // Get the personPaidNetworkDebit
        restPersonPaidNetworkDebitMockMvc.perform(get("/api/person-paid-network-debits/{id}", personPaidNetworkDebit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPaidNetworkDebit.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPaidNetworkDebit() throws Exception {
        // Get the personPaidNetworkDebit
        restPersonPaidNetworkDebitMockMvc.perform(get("/api/person-paid-network-debits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPaidNetworkDebit() throws Exception {
        // Initialize the database
        personPaidNetworkDebitService.save(personPaidNetworkDebit);

        int databaseSizeBeforeUpdate = personPaidNetworkDebitRepository.findAll().size();

        // Update the personPaidNetworkDebit
        PersonPaidNetworkDebit updatedPersonPaidNetworkDebit = personPaidNetworkDebitRepository.findOne(personPaidNetworkDebit.getId());
        updatedPersonPaidNetworkDebit
                .createDate(UPDATED_CREATE_DATE)
                .amount(UPDATED_AMOUNT);

        restPersonPaidNetworkDebitMockMvc.perform(put("/api/person-paid-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonPaidNetworkDebit)))
            .andExpect(status().isOk());

        // Validate the PersonPaidNetworkDebit in the database
        List<PersonPaidNetworkDebit> personPaidNetworkDebitList = personPaidNetworkDebitRepository.findAll();
        assertThat(personPaidNetworkDebitList).hasSize(databaseSizeBeforeUpdate);
        PersonPaidNetworkDebit testPersonPaidNetworkDebit = personPaidNetworkDebitList.get(personPaidNetworkDebitList.size() - 1);
        assertThat(testPersonPaidNetworkDebit.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonPaidNetworkDebit.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPaidNetworkDebit() throws Exception {
        int databaseSizeBeforeUpdate = personPaidNetworkDebitRepository.findAll().size();

        // Create the PersonPaidNetworkDebit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonPaidNetworkDebitMockMvc.perform(put("/api/person-paid-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPaidNetworkDebit)))
            .andExpect(status().isCreated());

        // Validate the PersonPaidNetworkDebit in the database
        List<PersonPaidNetworkDebit> personPaidNetworkDebitList = personPaidNetworkDebitRepository.findAll();
        assertThat(personPaidNetworkDebitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonPaidNetworkDebit() throws Exception {
        // Initialize the database
        personPaidNetworkDebitService.save(personPaidNetworkDebit);

        int databaseSizeBeforeDelete = personPaidNetworkDebitRepository.findAll().size();

        // Get the personPaidNetworkDebit
        restPersonPaidNetworkDebitMockMvc.perform(delete("/api/person-paid-network-debits/{id}", personPaidNetworkDebit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPaidNetworkDebit> personPaidNetworkDebitList = personPaidNetworkDebitRepository.findAll();
        assertThat(personPaidNetworkDebitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
