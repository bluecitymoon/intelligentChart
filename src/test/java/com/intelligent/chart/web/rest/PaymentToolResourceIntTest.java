package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PaymentTool;
import com.intelligent.chart.repository.PaymentToolRepository;
import com.intelligent.chart.service.PaymentToolService;

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
 * Test class for the PaymentToolResource REST controller.
 *
 * @see PaymentToolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PaymentToolResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PaymentToolRepository paymentToolRepository;

    @Inject
    private PaymentToolService paymentToolService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPaymentToolMockMvc;

    private PaymentTool paymentTool;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentToolResource paymentToolResource = new PaymentToolResource();
        ReflectionTestUtils.setField(paymentToolResource, "paymentToolService", paymentToolService);
        this.restPaymentToolMockMvc = MockMvcBuilders.standaloneSetup(paymentToolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTool createEntity(EntityManager em) {
        PaymentTool paymentTool = new PaymentTool()
                .name(DEFAULT_NAME);
        return paymentTool;
    }

    @Before
    public void initTest() {
        paymentTool = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTool() throws Exception {
        int databaseSizeBeforeCreate = paymentToolRepository.findAll().size();

        // Create the PaymentTool

        restPaymentToolMockMvc.perform(post("/api/payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTool)))
            .andExpect(status().isCreated());

        // Validate the PaymentTool in the database
        List<PaymentTool> paymentToolList = paymentToolRepository.findAll();
        assertThat(paymentToolList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTool testPaymentTool = paymentToolList.get(paymentToolList.size() - 1);
        assertThat(testPaymentTool.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPaymentToolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentToolRepository.findAll().size();

        // Create the PaymentTool with an existing ID
        PaymentTool existingPaymentTool = new PaymentTool();
        existingPaymentTool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentToolMockMvc.perform(post("/api/payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPaymentTool)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaymentTool> paymentToolList = paymentToolRepository.findAll();
        assertThat(paymentToolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentTools() throws Exception {
        // Initialize the database
        paymentToolRepository.saveAndFlush(paymentTool);

        // Get all the paymentToolList
        restPaymentToolMockMvc.perform(get("/api/payment-tools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTool.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPaymentTool() throws Exception {
        // Initialize the database
        paymentToolRepository.saveAndFlush(paymentTool);

        // Get the paymentTool
        restPaymentToolMockMvc.perform(get("/api/payment-tools/{id}", paymentTool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTool.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentTool() throws Exception {
        // Get the paymentTool
        restPaymentToolMockMvc.perform(get("/api/payment-tools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTool() throws Exception {
        // Initialize the database
        paymentToolService.save(paymentTool);

        int databaseSizeBeforeUpdate = paymentToolRepository.findAll().size();

        // Update the paymentTool
        PaymentTool updatedPaymentTool = paymentToolRepository.findOne(paymentTool.getId());
        updatedPaymentTool
                .name(UPDATED_NAME);

        restPaymentToolMockMvc.perform(put("/api/payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentTool)))
            .andExpect(status().isOk());

        // Validate the PaymentTool in the database
        List<PaymentTool> paymentToolList = paymentToolRepository.findAll();
        assertThat(paymentToolList).hasSize(databaseSizeBeforeUpdate);
        PaymentTool testPaymentTool = paymentToolList.get(paymentToolList.size() - 1);
        assertThat(testPaymentTool.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTool() throws Exception {
        int databaseSizeBeforeUpdate = paymentToolRepository.findAll().size();

        // Create the PaymentTool

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentToolMockMvc.perform(put("/api/payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTool)))
            .andExpect(status().isCreated());

        // Validate the PaymentTool in the database
        List<PaymentTool> paymentToolList = paymentToolRepository.findAll();
        assertThat(paymentToolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentTool() throws Exception {
        // Initialize the database
        paymentToolService.save(paymentTool);

        int databaseSizeBeforeDelete = paymentToolRepository.findAll().size();

        // Get the paymentTool
        restPaymentToolMockMvc.perform(delete("/api/payment-tools/{id}", paymentTool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentTool> paymentToolList = paymentToolRepository.findAll();
        assertThat(paymentToolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
