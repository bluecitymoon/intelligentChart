package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonEndorsement;
import com.intelligent.chart.repository.PersonEndorsementRepository;
import com.intelligent.chart.service.PersonEndorsementService;

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
 * Test class for the PersonEndorsementResource REST controller.
 *
 * @see PersonEndorsementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonEndorsementResourceIntTest {

    private static final Integer DEFAULT_SEQ_NUMBER = 1;
    private static final Integer UPDATED_SEQ_NUMBER = 2;

    private static final Float DEFAULT_PAID_AMOUNT = 1F;
    private static final Float UPDATED_PAID_AMOUNT = 2F;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BEHAVIOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BEHAVIOR_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    @Inject
    private PersonEndorsementRepository personEndorsementRepository;

    @Inject
    private PersonEndorsementService personEndorsementService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonEndorsementMockMvc;

    private PersonEndorsement personEndorsement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonEndorsementResource personEndorsementResource = new PersonEndorsementResource();
        ReflectionTestUtils.setField(personEndorsementResource, "personEndorsementService", personEndorsementService);
        this.restPersonEndorsementMockMvc = MockMvcBuilders.standaloneSetup(personEndorsementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEndorsement createEntity(EntityManager em) {
        PersonEndorsement personEndorsement = new PersonEndorsement()
                .seqNumber(DEFAULT_SEQ_NUMBER)
                .paidAmount(DEFAULT_PAID_AMOUNT)
                .createDate(DEFAULT_CREATE_DATE)
                .address(DEFAULT_ADDRESS)
                .behaviorDescription(DEFAULT_BEHAVIOR_DESCRIPTION)
                .createBy(DEFAULT_CREATE_BY);
        return personEndorsement;
    }

    @Before
    public void initTest() {
        personEndorsement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEndorsement() throws Exception {
        int databaseSizeBeforeCreate = personEndorsementRepository.findAll().size();

        // Create the PersonEndorsement

        restPersonEndorsementMockMvc.perform(post("/api/person-endorsements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEndorsement)))
            .andExpect(status().isCreated());

        // Validate the PersonEndorsement in the database
        List<PersonEndorsement> personEndorsementList = personEndorsementRepository.findAll();
        assertThat(personEndorsementList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEndorsement testPersonEndorsement = personEndorsementList.get(personEndorsementList.size() - 1);
        assertThat(testPersonEndorsement.getSeqNumber()).isEqualTo(DEFAULT_SEQ_NUMBER);
        assertThat(testPersonEndorsement.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testPersonEndorsement.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonEndorsement.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPersonEndorsement.getBehaviorDescription()).isEqualTo(DEFAULT_BEHAVIOR_DESCRIPTION);
        assertThat(testPersonEndorsement.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
    }

    @Test
    @Transactional
    public void createPersonEndorsementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEndorsementRepository.findAll().size();

        // Create the PersonEndorsement with an existing ID
        PersonEndorsement existingPersonEndorsement = new PersonEndorsement();
        existingPersonEndorsement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEndorsementMockMvc.perform(post("/api/person-endorsements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonEndorsement)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonEndorsement> personEndorsementList = personEndorsementRepository.findAll();
        assertThat(personEndorsementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonEndorsements() throws Exception {
        // Initialize the database
        personEndorsementRepository.saveAndFlush(personEndorsement);

        // Get all the personEndorsementList
        restPersonEndorsementMockMvc.perform(get("/api/person-endorsements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEndorsement.getId().intValue())))
            .andExpect(jsonPath("$.[*].seqNumber").value(hasItem(DEFAULT_SEQ_NUMBER)))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].behaviorDescription").value(hasItem(DEFAULT_BEHAVIOR_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getPersonEndorsement() throws Exception {
        // Initialize the database
        personEndorsementRepository.saveAndFlush(personEndorsement);

        // Get the personEndorsement
        restPersonEndorsementMockMvc.perform(get("/api/person-endorsements/{id}", personEndorsement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personEndorsement.getId().intValue()))
            .andExpect(jsonPath("$.seqNumber").value(DEFAULT_SEQ_NUMBER))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.behaviorDescription").value(DEFAULT_BEHAVIOR_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonEndorsement() throws Exception {
        // Get the personEndorsement
        restPersonEndorsementMockMvc.perform(get("/api/person-endorsements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEndorsement() throws Exception {
        // Initialize the database
        personEndorsementService.save(personEndorsement);

        int databaseSizeBeforeUpdate = personEndorsementRepository.findAll().size();

        // Update the personEndorsement
        PersonEndorsement updatedPersonEndorsement = personEndorsementRepository.findOne(personEndorsement.getId());
        updatedPersonEndorsement
                .seqNumber(UPDATED_SEQ_NUMBER)
                .paidAmount(UPDATED_PAID_AMOUNT)
                .createDate(UPDATED_CREATE_DATE)
                .address(UPDATED_ADDRESS)
                .behaviorDescription(UPDATED_BEHAVIOR_DESCRIPTION)
                .createBy(UPDATED_CREATE_BY);

        restPersonEndorsementMockMvc.perform(put("/api/person-endorsements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonEndorsement)))
            .andExpect(status().isOk());

        // Validate the PersonEndorsement in the database
        List<PersonEndorsement> personEndorsementList = personEndorsementRepository.findAll();
        assertThat(personEndorsementList).hasSize(databaseSizeBeforeUpdate);
        PersonEndorsement testPersonEndorsement = personEndorsementList.get(personEndorsementList.size() - 1);
        assertThat(testPersonEndorsement.getSeqNumber()).isEqualTo(UPDATED_SEQ_NUMBER);
        assertThat(testPersonEndorsement.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPersonEndorsement.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonEndorsement.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonEndorsement.getBehaviorDescription()).isEqualTo(UPDATED_BEHAVIOR_DESCRIPTION);
        assertThat(testPersonEndorsement.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEndorsement() throws Exception {
        int databaseSizeBeforeUpdate = personEndorsementRepository.findAll().size();

        // Create the PersonEndorsement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonEndorsementMockMvc.perform(put("/api/person-endorsements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEndorsement)))
            .andExpect(status().isCreated());

        // Validate the PersonEndorsement in the database
        List<PersonEndorsement> personEndorsementList = personEndorsementRepository.findAll();
        assertThat(personEndorsementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonEndorsement() throws Exception {
        // Initialize the database
        personEndorsementService.save(personEndorsement);

        int databaseSizeBeforeDelete = personEndorsementRepository.findAll().size();

        // Get the personEndorsement
        restPersonEndorsementMockMvc.perform(delete("/api/person-endorsements/{id}", personEndorsement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonEndorsement> personEndorsementList = personEndorsementRepository.findAll();
        assertThat(personEndorsementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
