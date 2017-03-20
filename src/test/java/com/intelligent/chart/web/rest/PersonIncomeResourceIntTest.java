package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonIncome;
import com.intelligent.chart.repository.PersonIncomeRepository;
import com.intelligent.chart.service.PersonIncomeService;

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
 * Test class for the PersonIncomeResource REST controller.
 *
 * @see PersonIncomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonIncomeResourceIntTest {

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Float DEFAULT_IN_COUNTRY_SALARY_TOTAL = 1F;
    private static final Float UPDATED_IN_COUNTRY_SALARY_TOTAL = 2F;

    private static final Float DEFAULT_IN_COUNTRY_PLUS_BOX_TOTAL = 1F;
    private static final Float UPDATED_IN_COUNTRY_PLUS_BOX_TOTAL = 2F;

    private static final Float DEFAULT_OUT_COUNTRY_SALARY_TOTAL = 1F;
    private static final Float UPDATED_OUT_COUNTRY_SALARY_TOTAL = 2F;

    private static final Float DEFAULT_OUT_COUNTRY_PLUS_BOX_TOTAL = 1F;
    private static final Float UPDATED_OUT_COUNTRY_PLUS_BOX_TOTAL = 2F;

    @Inject
    private PersonIncomeRepository personIncomeRepository;

    @Inject
    private PersonIncomeService personIncomeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonIncomeMockMvc;

    private PersonIncome personIncome;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonIncomeResource personIncomeResource = new PersonIncomeResource();
        ReflectionTestUtils.setField(personIncomeResource, "personIncomeService", personIncomeService);
        this.restPersonIncomeMockMvc = MockMvcBuilders.standaloneSetup(personIncomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonIncome createEntity(EntityManager em) {
        PersonIncome personIncome = new PersonIncome()
                .year(DEFAULT_YEAR)
                .inCountrySalaryTotal(DEFAULT_IN_COUNTRY_SALARY_TOTAL)
                .inCountryPlusBoxTotal(DEFAULT_IN_COUNTRY_PLUS_BOX_TOTAL)
                .outCountrySalaryTotal(DEFAULT_OUT_COUNTRY_SALARY_TOTAL)
                .outCountryPlusBoxTotal(DEFAULT_OUT_COUNTRY_PLUS_BOX_TOTAL);
        return personIncome;
    }

    @Before
    public void initTest() {
        personIncome = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonIncome() throws Exception {
        int databaseSizeBeforeCreate = personIncomeRepository.findAll().size();

        // Create the PersonIncome

        restPersonIncomeMockMvc.perform(post("/api/person-incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personIncome)))
            .andExpect(status().isCreated());

        // Validate the PersonIncome in the database
        List<PersonIncome> personIncomeList = personIncomeRepository.findAll();
        assertThat(personIncomeList).hasSize(databaseSizeBeforeCreate + 1);
        PersonIncome testPersonIncome = personIncomeList.get(personIncomeList.size() - 1);
        assertThat(testPersonIncome.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPersonIncome.getInCountrySalaryTotal()).isEqualTo(DEFAULT_IN_COUNTRY_SALARY_TOTAL);
        assertThat(testPersonIncome.getInCountryPlusBoxTotal()).isEqualTo(DEFAULT_IN_COUNTRY_PLUS_BOX_TOTAL);
        assertThat(testPersonIncome.getOutCountrySalaryTotal()).isEqualTo(DEFAULT_OUT_COUNTRY_SALARY_TOTAL);
        assertThat(testPersonIncome.getOutCountryPlusBoxTotal()).isEqualTo(DEFAULT_OUT_COUNTRY_PLUS_BOX_TOTAL);
    }

    @Test
    @Transactional
    public void createPersonIncomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personIncomeRepository.findAll().size();

        // Create the PersonIncome with an existing ID
        PersonIncome existingPersonIncome = new PersonIncome();
        existingPersonIncome.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonIncomeMockMvc.perform(post("/api/person-incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonIncome)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonIncome> personIncomeList = personIncomeRepository.findAll();
        assertThat(personIncomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonIncomes() throws Exception {
        // Initialize the database
        personIncomeRepository.saveAndFlush(personIncome);

        // Get all the personIncomeList
        restPersonIncomeMockMvc.perform(get("/api/person-incomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personIncome.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].inCountrySalaryTotal").value(hasItem(DEFAULT_IN_COUNTRY_SALARY_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].inCountryPlusBoxTotal").value(hasItem(DEFAULT_IN_COUNTRY_PLUS_BOX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].outCountrySalaryTotal").value(hasItem(DEFAULT_OUT_COUNTRY_SALARY_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].outCountryPlusBoxTotal").value(hasItem(DEFAULT_OUT_COUNTRY_PLUS_BOX_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getPersonIncome() throws Exception {
        // Initialize the database
        personIncomeRepository.saveAndFlush(personIncome);

        // Get the personIncome
        restPersonIncomeMockMvc.perform(get("/api/person-incomes/{id}", personIncome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personIncome.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.inCountrySalaryTotal").value(DEFAULT_IN_COUNTRY_SALARY_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.inCountryPlusBoxTotal").value(DEFAULT_IN_COUNTRY_PLUS_BOX_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.outCountrySalaryTotal").value(DEFAULT_OUT_COUNTRY_SALARY_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.outCountryPlusBoxTotal").value(DEFAULT_OUT_COUNTRY_PLUS_BOX_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonIncome() throws Exception {
        // Get the personIncome
        restPersonIncomeMockMvc.perform(get("/api/person-incomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonIncome() throws Exception {
        // Initialize the database
        personIncomeService.save(personIncome);

        int databaseSizeBeforeUpdate = personIncomeRepository.findAll().size();

        // Update the personIncome
        PersonIncome updatedPersonIncome = personIncomeRepository.findOne(personIncome.getId());
        updatedPersonIncome
                .year(UPDATED_YEAR)
                .inCountrySalaryTotal(UPDATED_IN_COUNTRY_SALARY_TOTAL)
                .inCountryPlusBoxTotal(UPDATED_IN_COUNTRY_PLUS_BOX_TOTAL)
                .outCountrySalaryTotal(UPDATED_OUT_COUNTRY_SALARY_TOTAL)
                .outCountryPlusBoxTotal(UPDATED_OUT_COUNTRY_PLUS_BOX_TOTAL);

        restPersonIncomeMockMvc.perform(put("/api/person-incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonIncome)))
            .andExpect(status().isOk());

        // Validate the PersonIncome in the database
        List<PersonIncome> personIncomeList = personIncomeRepository.findAll();
        assertThat(personIncomeList).hasSize(databaseSizeBeforeUpdate);
        PersonIncome testPersonIncome = personIncomeList.get(personIncomeList.size() - 1);
        assertThat(testPersonIncome.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPersonIncome.getInCountrySalaryTotal()).isEqualTo(UPDATED_IN_COUNTRY_SALARY_TOTAL);
        assertThat(testPersonIncome.getInCountryPlusBoxTotal()).isEqualTo(UPDATED_IN_COUNTRY_PLUS_BOX_TOTAL);
        assertThat(testPersonIncome.getOutCountrySalaryTotal()).isEqualTo(UPDATED_OUT_COUNTRY_SALARY_TOTAL);
        assertThat(testPersonIncome.getOutCountryPlusBoxTotal()).isEqualTo(UPDATED_OUT_COUNTRY_PLUS_BOX_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonIncome() throws Exception {
        int databaseSizeBeforeUpdate = personIncomeRepository.findAll().size();

        // Create the PersonIncome

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonIncomeMockMvc.perform(put("/api/person-incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personIncome)))
            .andExpect(status().isCreated());

        // Validate the PersonIncome in the database
        List<PersonIncome> personIncomeList = personIncomeRepository.findAll();
        assertThat(personIncomeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonIncome() throws Exception {
        // Initialize the database
        personIncomeService.save(personIncome);

        int databaseSizeBeforeDelete = personIncomeRepository.findAll().size();

        // Get the personIncome
        restPersonIncomeMockMvc.perform(delete("/api/person-incomes/{id}", personIncome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonIncome> personIncomeList = personIncomeRepository.findAll();
        assertThat(personIncomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
