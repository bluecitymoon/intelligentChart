package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.CreditCardActivityType;
import com.intelligent.chart.repository.CreditCardActivityTypeRepository;
import com.intelligent.chart.service.CreditCardActivityTypeService;

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
 * Test class for the CreditCardActivityTypeResource REST controller.
 *
 * @see CreditCardActivityTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class CreditCardActivityTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CreditCardActivityTypeRepository creditCardActivityTypeRepository;

    @Inject
    private CreditCardActivityTypeService creditCardActivityTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCreditCardActivityTypeMockMvc;

    private CreditCardActivityType creditCardActivityType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CreditCardActivityTypeResource creditCardActivityTypeResource = new CreditCardActivityTypeResource();
        ReflectionTestUtils.setField(creditCardActivityTypeResource, "creditCardActivityTypeService", creditCardActivityTypeService);
        this.restCreditCardActivityTypeMockMvc = MockMvcBuilders.standaloneSetup(creditCardActivityTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardActivityType createEntity(EntityManager em) {
        CreditCardActivityType creditCardActivityType = new CreditCardActivityType()
                .name(DEFAULT_NAME);
        return creditCardActivityType;
    }

    @Before
    public void initTest() {
        creditCardActivityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditCardActivityType() throws Exception {
        int databaseSizeBeforeCreate = creditCardActivityTypeRepository.findAll().size();

        // Create the CreditCardActivityType

        restCreditCardActivityTypeMockMvc.perform(post("/api/credit-card-activity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardActivityType)))
            .andExpect(status().isCreated());

        // Validate the CreditCardActivityType in the database
        List<CreditCardActivityType> creditCardActivityTypeList = creditCardActivityTypeRepository.findAll();
        assertThat(creditCardActivityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardActivityType testCreditCardActivityType = creditCardActivityTypeList.get(creditCardActivityTypeList.size() - 1);
        assertThat(testCreditCardActivityType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCreditCardActivityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditCardActivityTypeRepository.findAll().size();

        // Create the CreditCardActivityType with an existing ID
        CreditCardActivityType existingCreditCardActivityType = new CreditCardActivityType();
        existingCreditCardActivityType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardActivityTypeMockMvc.perform(post("/api/credit-card-activity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCreditCardActivityType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CreditCardActivityType> creditCardActivityTypeList = creditCardActivityTypeRepository.findAll();
        assertThat(creditCardActivityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCreditCardActivityTypes() throws Exception {
        // Initialize the database
        creditCardActivityTypeRepository.saveAndFlush(creditCardActivityType);

        // Get all the creditCardActivityTypeList
        restCreditCardActivityTypeMockMvc.perform(get("/api/credit-card-activity-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardActivityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCreditCardActivityType() throws Exception {
        // Initialize the database
        creditCardActivityTypeRepository.saveAndFlush(creditCardActivityType);

        // Get the creditCardActivityType
        restCreditCardActivityTypeMockMvc.perform(get("/api/credit-card-activity-types/{id}", creditCardActivityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardActivityType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditCardActivityType() throws Exception {
        // Get the creditCardActivityType
        restCreditCardActivityTypeMockMvc.perform(get("/api/credit-card-activity-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditCardActivityType() throws Exception {
        // Initialize the database
        creditCardActivityTypeService.save(creditCardActivityType);

        int databaseSizeBeforeUpdate = creditCardActivityTypeRepository.findAll().size();

        // Update the creditCardActivityType
        CreditCardActivityType updatedCreditCardActivityType = creditCardActivityTypeRepository.findOne(creditCardActivityType.getId());
        updatedCreditCardActivityType
                .name(UPDATED_NAME);

        restCreditCardActivityTypeMockMvc.perform(put("/api/credit-card-activity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCreditCardActivityType)))
            .andExpect(status().isOk());

        // Validate the CreditCardActivityType in the database
        List<CreditCardActivityType> creditCardActivityTypeList = creditCardActivityTypeRepository.findAll();
        assertThat(creditCardActivityTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditCardActivityType testCreditCardActivityType = creditCardActivityTypeList.get(creditCardActivityTypeList.size() - 1);
        assertThat(testCreditCardActivityType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditCardActivityType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardActivityTypeRepository.findAll().size();

        // Create the CreditCardActivityType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCreditCardActivityTypeMockMvc.perform(put("/api/credit-card-activity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardActivityType)))
            .andExpect(status().isCreated());

        // Validate the CreditCardActivityType in the database
        List<CreditCardActivityType> creditCardActivityTypeList = creditCardActivityTypeRepository.findAll();
        assertThat(creditCardActivityTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCreditCardActivityType() throws Exception {
        // Initialize the database
        creditCardActivityTypeService.save(creditCardActivityType);

        int databaseSizeBeforeDelete = creditCardActivityTypeRepository.findAll().size();

        // Get the creditCardActivityType
        restCreditCardActivityTypeMockMvc.perform(delete("/api/credit-card-activity-types/{id}", creditCardActivityType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditCardActivityType> creditCardActivityTypeList = creditCardActivityTypeRepository.findAll();
        assertThat(creditCardActivityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
