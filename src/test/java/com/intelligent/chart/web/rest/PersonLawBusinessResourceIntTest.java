package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonLawBusiness;
import com.intelligent.chart.repository.PersonLawBusinessRepository;
import com.intelligent.chart.service.PersonLawBusinessService;

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
 * Test class for the PersonLawBusinessResource REST controller.
 *
 * @see PersonLawBusinessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonLawBusinessResourceIntTest {

    private static final String DEFAULT_DESCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBBBBBBB";

    @Inject
    private PersonLawBusinessRepository personLawBusinessRepository;

    @Inject
    private PersonLawBusinessService personLawBusinessService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonLawBusinessMockMvc;

    private PersonLawBusiness personLawBusiness;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonLawBusinessResource personLawBusinessResource = new PersonLawBusinessResource();
        ReflectionTestUtils.setField(personLawBusinessResource, "personLawBusinessService", personLawBusinessService);
        this.restPersonLawBusinessMockMvc = MockMvcBuilders.standaloneSetup(personLawBusinessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonLawBusiness createEntity(EntityManager em) {
        PersonLawBusiness personLawBusiness = new PersonLawBusiness()
                .descriptions(DEFAULT_DESCRIPTIONS);
        return personLawBusiness;
    }

    @Before
    public void initTest() {
        personLawBusiness = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonLawBusiness() throws Exception {
        int databaseSizeBeforeCreate = personLawBusinessRepository.findAll().size();

        // Create the PersonLawBusiness

        restPersonLawBusinessMockMvc.perform(post("/api/person-law-businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLawBusiness)))
            .andExpect(status().isCreated());

        // Validate the PersonLawBusiness in the database
        List<PersonLawBusiness> personLawBusinessList = personLawBusinessRepository.findAll();
        assertThat(personLawBusinessList).hasSize(databaseSizeBeforeCreate + 1);
        PersonLawBusiness testPersonLawBusiness = personLawBusinessList.get(personLawBusinessList.size() - 1);
        assertThat(testPersonLawBusiness.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void createPersonLawBusinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personLawBusinessRepository.findAll().size();

        // Create the PersonLawBusiness with an existing ID
        PersonLawBusiness existingPersonLawBusiness = new PersonLawBusiness();
        existingPersonLawBusiness.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonLawBusinessMockMvc.perform(post("/api/person-law-businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonLawBusiness)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonLawBusiness> personLawBusinessList = personLawBusinessRepository.findAll();
        assertThat(personLawBusinessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonLawBusinesses() throws Exception {
        // Initialize the database
        personLawBusinessRepository.saveAndFlush(personLawBusiness);

        // Get all the personLawBusinessList
        restPersonLawBusinessMockMvc.perform(get("/api/person-law-businesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personLawBusiness.getId().intValue())))
            .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())));
    }

    @Test
    @Transactional
    public void getPersonLawBusiness() throws Exception {
        // Initialize the database
        personLawBusinessRepository.saveAndFlush(personLawBusiness);

        // Get the personLawBusiness
        restPersonLawBusinessMockMvc.perform(get("/api/person-law-businesses/{id}", personLawBusiness.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personLawBusiness.getId().intValue()))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonLawBusiness() throws Exception {
        // Get the personLawBusiness
        restPersonLawBusinessMockMvc.perform(get("/api/person-law-businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonLawBusiness() throws Exception {
        // Initialize the database
        personLawBusinessService.save(personLawBusiness);

        int databaseSizeBeforeUpdate = personLawBusinessRepository.findAll().size();

        // Update the personLawBusiness
        PersonLawBusiness updatedPersonLawBusiness = personLawBusinessRepository.findOne(personLawBusiness.getId());
        updatedPersonLawBusiness
                .descriptions(UPDATED_DESCRIPTIONS);

        restPersonLawBusinessMockMvc.perform(put("/api/person-law-businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonLawBusiness)))
            .andExpect(status().isOk());

        // Validate the PersonLawBusiness in the database
        List<PersonLawBusiness> personLawBusinessList = personLawBusinessRepository.findAll();
        assertThat(personLawBusinessList).hasSize(databaseSizeBeforeUpdate);
        PersonLawBusiness testPersonLawBusiness = personLawBusinessList.get(personLawBusinessList.size() - 1);
        assertThat(testPersonLawBusiness.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonLawBusiness() throws Exception {
        int databaseSizeBeforeUpdate = personLawBusinessRepository.findAll().size();

        // Create the PersonLawBusiness

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonLawBusinessMockMvc.perform(put("/api/person-law-businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personLawBusiness)))
            .andExpect(status().isCreated());

        // Validate the PersonLawBusiness in the database
        List<PersonLawBusiness> personLawBusinessList = personLawBusinessRepository.findAll();
        assertThat(personLawBusinessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonLawBusiness() throws Exception {
        // Initialize the database
        personLawBusinessService.save(personLawBusiness);

        int databaseSizeBeforeDelete = personLawBusinessRepository.findAll().size();

        // Get the personLawBusiness
        restPersonLawBusinessMockMvc.perform(delete("/api/person-law-businesses/{id}", personLawBusiness.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonLawBusiness> personLawBusinessList = personLawBusinessRepository.findAll();
        assertThat(personLawBusinessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
