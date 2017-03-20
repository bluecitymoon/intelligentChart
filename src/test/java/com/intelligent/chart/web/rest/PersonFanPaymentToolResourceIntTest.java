package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFanPaymentTool;
import com.intelligent.chart.repository.PersonFanPaymentToolRepository;
import com.intelligent.chart.service.PersonFanPaymentToolService;

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
 * Test class for the PersonFanPaymentToolResource REST controller.
 *
 * @see PersonFanPaymentToolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFanPaymentToolResourceIntTest {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Inject
    private PersonFanPaymentToolRepository personFanPaymentToolRepository;

    @Inject
    private PersonFanPaymentToolService personFanPaymentToolService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFanPaymentToolMockMvc;

    private PersonFanPaymentTool personFanPaymentTool;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFanPaymentToolResource personFanPaymentToolResource = new PersonFanPaymentToolResource();
        ReflectionTestUtils.setField(personFanPaymentToolResource, "personFanPaymentToolService", personFanPaymentToolService);
        this.restPersonFanPaymentToolMockMvc = MockMvcBuilders.standaloneSetup(personFanPaymentToolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFanPaymentTool createEntity(EntityManager em) {
        PersonFanPaymentTool personFanPaymentTool = new PersonFanPaymentTool()
                .count(DEFAULT_COUNT);
        return personFanPaymentTool;
    }

    @Before
    public void initTest() {
        personFanPaymentTool = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFanPaymentTool() throws Exception {
        int databaseSizeBeforeCreate = personFanPaymentToolRepository.findAll().size();

        // Create the PersonFanPaymentTool

        restPersonFanPaymentToolMockMvc.perform(post("/api/person-fan-payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanPaymentTool)))
            .andExpect(status().isCreated());

        // Validate the PersonFanPaymentTool in the database
        List<PersonFanPaymentTool> personFanPaymentToolList = personFanPaymentToolRepository.findAll();
        assertThat(personFanPaymentToolList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFanPaymentTool testPersonFanPaymentTool = personFanPaymentToolList.get(personFanPaymentToolList.size() - 1);
        assertThat(testPersonFanPaymentTool.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFanPaymentToolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFanPaymentToolRepository.findAll().size();

        // Create the PersonFanPaymentTool with an existing ID
        PersonFanPaymentTool existingPersonFanPaymentTool = new PersonFanPaymentTool();
        existingPersonFanPaymentTool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFanPaymentToolMockMvc.perform(post("/api/person-fan-payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFanPaymentTool)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFanPaymentTool> personFanPaymentToolList = personFanPaymentToolRepository.findAll();
        assertThat(personFanPaymentToolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFanPaymentTools() throws Exception {
        // Initialize the database
        personFanPaymentToolRepository.saveAndFlush(personFanPaymentTool);

        // Get all the personFanPaymentToolList
        restPersonFanPaymentToolMockMvc.perform(get("/api/person-fan-payment-tools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFanPaymentTool.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFanPaymentTool() throws Exception {
        // Initialize the database
        personFanPaymentToolRepository.saveAndFlush(personFanPaymentTool);

        // Get the personFanPaymentTool
        restPersonFanPaymentToolMockMvc.perform(get("/api/person-fan-payment-tools/{id}", personFanPaymentTool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFanPaymentTool.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFanPaymentTool() throws Exception {
        // Get the personFanPaymentTool
        restPersonFanPaymentToolMockMvc.perform(get("/api/person-fan-payment-tools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFanPaymentTool() throws Exception {
        // Initialize the database
        personFanPaymentToolService.save(personFanPaymentTool);

        int databaseSizeBeforeUpdate = personFanPaymentToolRepository.findAll().size();

        // Update the personFanPaymentTool
        PersonFanPaymentTool updatedPersonFanPaymentTool = personFanPaymentToolRepository.findOne(personFanPaymentTool.getId());
        updatedPersonFanPaymentTool
                .count(UPDATED_COUNT);

        restPersonFanPaymentToolMockMvc.perform(put("/api/person-fan-payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFanPaymentTool)))
            .andExpect(status().isOk());

        // Validate the PersonFanPaymentTool in the database
        List<PersonFanPaymentTool> personFanPaymentToolList = personFanPaymentToolRepository.findAll();
        assertThat(personFanPaymentToolList).hasSize(databaseSizeBeforeUpdate);
        PersonFanPaymentTool testPersonFanPaymentTool = personFanPaymentToolList.get(personFanPaymentToolList.size() - 1);
        assertThat(testPersonFanPaymentTool.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFanPaymentTool() throws Exception {
        int databaseSizeBeforeUpdate = personFanPaymentToolRepository.findAll().size();

        // Create the PersonFanPaymentTool

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFanPaymentToolMockMvc.perform(put("/api/person-fan-payment-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanPaymentTool)))
            .andExpect(status().isCreated());

        // Validate the PersonFanPaymentTool in the database
        List<PersonFanPaymentTool> personFanPaymentToolList = personFanPaymentToolRepository.findAll();
        assertThat(personFanPaymentToolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFanPaymentTool() throws Exception {
        // Initialize the database
        personFanPaymentToolService.save(personFanPaymentTool);

        int databaseSizeBeforeDelete = personFanPaymentToolRepository.findAll().size();

        // Get the personFanPaymentTool
        restPersonFanPaymentToolMockMvc.perform(delete("/api/person-fan-payment-tools/{id}", personFanPaymentTool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFanPaymentTool> personFanPaymentToolList = personFanPaymentToolRepository.findAll();
        assertThat(personFanPaymentToolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
