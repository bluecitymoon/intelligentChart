package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonTieBa;
import com.intelligent.chart.repository.PersonTieBaRepository;
import com.intelligent.chart.service.PersonTieBaService;

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
 * Test class for the PersonTieBaResource REST controller.
 *
 * @see PersonTieBaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonTieBaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_COUNT = 1;
    private static final Integer UPDATED_USER_COUNT = 2;

    private static final Integer DEFAULT_POST_COUNT = 1;
    private static final Integer UPDATED_POST_COUNT = 2;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RANK_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RANK_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    @Inject
    private PersonTieBaRepository personTieBaRepository;

    @Inject
    private PersonTieBaService personTieBaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonTieBaMockMvc;

    private PersonTieBa personTieBa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonTieBaResource personTieBaResource = new PersonTieBaResource();
        ReflectionTestUtils.setField(personTieBaResource, "personTieBaService", personTieBaService);
        this.restPersonTieBaMockMvc = MockMvcBuilders.standaloneSetup(personTieBaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonTieBa createEntity(EntityManager em) {
        PersonTieBa personTieBa = new PersonTieBa()
                .name(DEFAULT_NAME)
                .userCount(DEFAULT_USER_COUNT)
                .postCount(DEFAULT_POST_COUNT)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .rankDescription(DEFAULT_RANK_DESCRIPTION)
                .rank(DEFAULT_RANK);
        return personTieBa;
    }

    @Before
    public void initTest() {
        personTieBa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonTieBa() throws Exception {
        int databaseSizeBeforeCreate = personTieBaRepository.findAll().size();

        // Create the PersonTieBa

        restPersonTieBaMockMvc.perform(post("/api/person-tie-bas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTieBa)))
            .andExpect(status().isCreated());

        // Validate the PersonTieBa in the database
        List<PersonTieBa> personTieBaList = personTieBaRepository.findAll();
        assertThat(personTieBaList).hasSize(databaseSizeBeforeCreate + 1);
        PersonTieBa testPersonTieBa = personTieBaList.get(personTieBaList.size() - 1);
        assertThat(testPersonTieBa.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonTieBa.getUserCount()).isEqualTo(DEFAULT_USER_COUNT);
        assertThat(testPersonTieBa.getPostCount()).isEqualTo(DEFAULT_POST_COUNT);
        assertThat(testPersonTieBa.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testPersonTieBa.getRankDescription()).isEqualTo(DEFAULT_RANK_DESCRIPTION);
        assertThat(testPersonTieBa.getRank()).isEqualTo(DEFAULT_RANK);
    }

    @Test
    @Transactional
    public void createPersonTieBaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personTieBaRepository.findAll().size();

        // Create the PersonTieBa with an existing ID
        PersonTieBa existingPersonTieBa = new PersonTieBa();
        existingPersonTieBa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonTieBaMockMvc.perform(post("/api/person-tie-bas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonTieBa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonTieBa> personTieBaList = personTieBaRepository.findAll();
        assertThat(personTieBaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonTieBas() throws Exception {
        // Initialize the database
        personTieBaRepository.saveAndFlush(personTieBa);

        // Get all the personTieBaList
        restPersonTieBaMockMvc.perform(get("/api/person-tie-bas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personTieBa.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userCount").value(hasItem(DEFAULT_USER_COUNT)))
            .andExpect(jsonPath("$.[*].postCount").value(hasItem(DEFAULT_POST_COUNT)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].rankDescription").value(hasItem(DEFAULT_RANK_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)));
    }

    @Test
    @Transactional
    public void getPersonTieBa() throws Exception {
        // Initialize the database
        personTieBaRepository.saveAndFlush(personTieBa);

        // Get the personTieBa
        restPersonTieBaMockMvc.perform(get("/api/person-tie-bas/{id}", personTieBa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personTieBa.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userCount").value(DEFAULT_USER_COUNT))
            .andExpect(jsonPath("$.postCount").value(DEFAULT_POST_COUNT))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rankDescription").value(DEFAULT_RANK_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK));
    }

    @Test
    @Transactional
    public void getNonExistingPersonTieBa() throws Exception {
        // Get the personTieBa
        restPersonTieBaMockMvc.perform(get("/api/person-tie-bas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonTieBa() throws Exception {
        // Initialize the database
        personTieBaService.save(personTieBa);

        int databaseSizeBeforeUpdate = personTieBaRepository.findAll().size();

        // Update the personTieBa
        PersonTieBa updatedPersonTieBa = personTieBaRepository.findOne(personTieBa.getId());
        updatedPersonTieBa
                .name(UPDATED_NAME)
                .userCount(UPDATED_USER_COUNT)
                .postCount(UPDATED_POST_COUNT)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .rankDescription(UPDATED_RANK_DESCRIPTION)
                .rank(UPDATED_RANK);

        restPersonTieBaMockMvc.perform(put("/api/person-tie-bas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonTieBa)))
            .andExpect(status().isOk());

        // Validate the PersonTieBa in the database
        List<PersonTieBa> personTieBaList = personTieBaRepository.findAll();
        assertThat(personTieBaList).hasSize(databaseSizeBeforeUpdate);
        PersonTieBa testPersonTieBa = personTieBaList.get(personTieBaList.size() - 1);
        assertThat(testPersonTieBa.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonTieBa.getUserCount()).isEqualTo(UPDATED_USER_COUNT);
        assertThat(testPersonTieBa.getPostCount()).isEqualTo(UPDATED_POST_COUNT);
        assertThat(testPersonTieBa.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testPersonTieBa.getRankDescription()).isEqualTo(UPDATED_RANK_DESCRIPTION);
        assertThat(testPersonTieBa.getRank()).isEqualTo(UPDATED_RANK);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonTieBa() throws Exception {
        int databaseSizeBeforeUpdate = personTieBaRepository.findAll().size();

        // Create the PersonTieBa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonTieBaMockMvc.perform(put("/api/person-tie-bas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTieBa)))
            .andExpect(status().isCreated());

        // Validate the PersonTieBa in the database
        List<PersonTieBa> personTieBaList = personTieBaRepository.findAll();
        assertThat(personTieBaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonTieBa() throws Exception {
        // Initialize the database
        personTieBaService.save(personTieBa);

        int databaseSizeBeforeDelete = personTieBaRepository.findAll().size();

        // Get the personTieBa
        restPersonTieBaMockMvc.perform(delete("/api/person-tie-bas/{id}", personTieBa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonTieBa> personTieBaList = personTieBaRepository.findAll();
        assertThat(personTieBaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
