package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonNetworkDebit;
import com.intelligent.chart.repository.PersonNetworkDebitRepository;
import com.intelligent.chart.service.PersonNetworkDebitService;

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
 * Test class for the PersonNetworkDebitResource REST controller.
 *
 * @see PersonNetworkDebitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonNetworkDebitResourceIntTest {

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    @Inject
    private PersonNetworkDebitRepository personNetworkDebitRepository;

    @Inject
    private PersonNetworkDebitService personNetworkDebitService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonNetworkDebitMockMvc;

    private PersonNetworkDebit personNetworkDebit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonNetworkDebitResource personNetworkDebitResource = new PersonNetworkDebitResource();
        ReflectionTestUtils.setField(personNetworkDebitResource, "personNetworkDebitService", personNetworkDebitService);
        this.restPersonNetworkDebitMockMvc = MockMvcBuilders.standaloneSetup(personNetworkDebitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonNetworkDebit createEntity(EntityManager em) {
        PersonNetworkDebit personNetworkDebit = new PersonNetworkDebit()
                .createDate(DEFAULT_CREATE_DATE)
                .amount(DEFAULT_AMOUNT);
        return personNetworkDebit;
    }

    @Before
    public void initTest() {
        personNetworkDebit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonNetworkDebit() throws Exception {
        int databaseSizeBeforeCreate = personNetworkDebitRepository.findAll().size();

        // Create the PersonNetworkDebit

        restPersonNetworkDebitMockMvc.perform(post("/api/person-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkDebit)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkDebit in the database
        List<PersonNetworkDebit> personNetworkDebitList = personNetworkDebitRepository.findAll();
        assertThat(personNetworkDebitList).hasSize(databaseSizeBeforeCreate + 1);
        PersonNetworkDebit testPersonNetworkDebit = personNetworkDebitList.get(personNetworkDebitList.size() - 1);
        assertThat(testPersonNetworkDebit.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonNetworkDebit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPersonNetworkDebitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personNetworkDebitRepository.findAll().size();

        // Create the PersonNetworkDebit with an existing ID
        PersonNetworkDebit existingPersonNetworkDebit = new PersonNetworkDebit();
        existingPersonNetworkDebit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonNetworkDebitMockMvc.perform(post("/api/person-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonNetworkDebit)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonNetworkDebit> personNetworkDebitList = personNetworkDebitRepository.findAll();
        assertThat(personNetworkDebitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonNetworkDebits() throws Exception {
        // Initialize the database
        personNetworkDebitRepository.saveAndFlush(personNetworkDebit);

        // Get all the personNetworkDebitList
        restPersonNetworkDebitMockMvc.perform(get("/api/person-network-debits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personNetworkDebit.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonNetworkDebit() throws Exception {
        // Initialize the database
        personNetworkDebitRepository.saveAndFlush(personNetworkDebit);

        // Get the personNetworkDebit
        restPersonNetworkDebitMockMvc.perform(get("/api/person-network-debits/{id}", personNetworkDebit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personNetworkDebit.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonNetworkDebit() throws Exception {
        // Get the personNetworkDebit
        restPersonNetworkDebitMockMvc.perform(get("/api/person-network-debits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonNetworkDebit() throws Exception {
        // Initialize the database
        personNetworkDebitService.save(personNetworkDebit);

        int databaseSizeBeforeUpdate = personNetworkDebitRepository.findAll().size();

        // Update the personNetworkDebit
        PersonNetworkDebit updatedPersonNetworkDebit = personNetworkDebitRepository.findOne(personNetworkDebit.getId());
        updatedPersonNetworkDebit
                .createDate(UPDATED_CREATE_DATE)
                .amount(UPDATED_AMOUNT);

        restPersonNetworkDebitMockMvc.perform(put("/api/person-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonNetworkDebit)))
            .andExpect(status().isOk());

        // Validate the PersonNetworkDebit in the database
        List<PersonNetworkDebit> personNetworkDebitList = personNetworkDebitRepository.findAll();
        assertThat(personNetworkDebitList).hasSize(databaseSizeBeforeUpdate);
        PersonNetworkDebit testPersonNetworkDebit = personNetworkDebitList.get(personNetworkDebitList.size() - 1);
        assertThat(testPersonNetworkDebit.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonNetworkDebit.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonNetworkDebit() throws Exception {
        int databaseSizeBeforeUpdate = personNetworkDebitRepository.findAll().size();

        // Create the PersonNetworkDebit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonNetworkDebitMockMvc.perform(put("/api/person-network-debits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkDebit)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkDebit in the database
        List<PersonNetworkDebit> personNetworkDebitList = personNetworkDebitRepository.findAll();
        assertThat(personNetworkDebitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonNetworkDebit() throws Exception {
        // Initialize the database
        personNetworkDebitService.save(personNetworkDebit);

        int databaseSizeBeforeDelete = personNetworkDebitRepository.findAll().size();

        // Get the personNetworkDebit
        restPersonNetworkDebitMockMvc.perform(delete("/api/person-network-debits/{id}", personNetworkDebit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonNetworkDebit> personNetworkDebitList = personNetworkDebitRepository.findAll();
        assertThat(personNetworkDebitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
