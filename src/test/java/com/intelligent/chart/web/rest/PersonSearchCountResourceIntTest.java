package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonSearchCount;
import com.intelligent.chart.repository.PersonSearchCountRepository;
import com.intelligent.chart.service.PersonSearchCountService;

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
 * Test class for the PersonSearchCountResource REST controller.
 *
 * @see PersonSearchCountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonSearchCountResourceIntTest {

    private static final LocalDate DEFAULT_SEARCH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SEARCH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonSearchCountRepository personSearchCountRepository;

    @Inject
    private PersonSearchCountService personSearchCountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonSearchCountMockMvc;

    private PersonSearchCount personSearchCount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonSearchCountResource personSearchCountResource = new PersonSearchCountResource();
        ReflectionTestUtils.setField(personSearchCountResource, "personSearchCountService", personSearchCountService);
        this.restPersonSearchCountMockMvc = MockMvcBuilders.standaloneSetup(personSearchCountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonSearchCount createEntity(EntityManager em) {
        PersonSearchCount personSearchCount = new PersonSearchCount()
                .searchDate(DEFAULT_SEARCH_DATE)
                .count(DEFAULT_COUNT);
        return personSearchCount;
    }

    @Before
    public void initTest() {
        personSearchCount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonSearchCount() throws Exception {
        int databaseSizeBeforeCreate = personSearchCountRepository.findAll().size();

        // Create the PersonSearchCount

        restPersonSearchCountMockMvc.perform(post("/api/person-search-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSearchCount)))
            .andExpect(status().isCreated());

        // Validate the PersonSearchCount in the database
        List<PersonSearchCount> personSearchCountList = personSearchCountRepository.findAll();
        assertThat(personSearchCountList).hasSize(databaseSizeBeforeCreate + 1);
        PersonSearchCount testPersonSearchCount = personSearchCountList.get(personSearchCountList.size() - 1);
        assertThat(testPersonSearchCount.getSearchDate()).isEqualTo(DEFAULT_SEARCH_DATE);
        assertThat(testPersonSearchCount.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonSearchCountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personSearchCountRepository.findAll().size();

        // Create the PersonSearchCount with an existing ID
        PersonSearchCount existingPersonSearchCount = new PersonSearchCount();
        existingPersonSearchCount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonSearchCountMockMvc.perform(post("/api/person-search-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonSearchCount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonSearchCount> personSearchCountList = personSearchCountRepository.findAll();
        assertThat(personSearchCountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonSearchCounts() throws Exception {
        // Initialize the database
        personSearchCountRepository.saveAndFlush(personSearchCount);

        // Get all the personSearchCountList
        restPersonSearchCountMockMvc.perform(get("/api/person-search-counts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personSearchCount.getId().intValue())))
            .andExpect(jsonPath("$.[*].searchDate").value(hasItem(DEFAULT_SEARCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonSearchCount() throws Exception {
        // Initialize the database
        personSearchCountRepository.saveAndFlush(personSearchCount);

        // Get the personSearchCount
        restPersonSearchCountMockMvc.perform(get("/api/person-search-counts/{id}", personSearchCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personSearchCount.getId().intValue()))
            .andExpect(jsonPath("$.searchDate").value(DEFAULT_SEARCH_DATE.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonSearchCount() throws Exception {
        // Get the personSearchCount
        restPersonSearchCountMockMvc.perform(get("/api/person-search-counts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonSearchCount() throws Exception {
        // Initialize the database
        personSearchCountService.save(personSearchCount);

        int databaseSizeBeforeUpdate = personSearchCountRepository.findAll().size();

        // Update the personSearchCount
        PersonSearchCount updatedPersonSearchCount = personSearchCountRepository.findOne(personSearchCount.getId());
        updatedPersonSearchCount
                .searchDate(UPDATED_SEARCH_DATE)
                .count(UPDATED_COUNT);

        restPersonSearchCountMockMvc.perform(put("/api/person-search-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonSearchCount)))
            .andExpect(status().isOk());

        // Validate the PersonSearchCount in the database
        List<PersonSearchCount> personSearchCountList = personSearchCountRepository.findAll();
        assertThat(personSearchCountList).hasSize(databaseSizeBeforeUpdate);
        PersonSearchCount testPersonSearchCount = personSearchCountList.get(personSearchCountList.size() - 1);
        assertThat(testPersonSearchCount.getSearchDate()).isEqualTo(UPDATED_SEARCH_DATE);
        assertThat(testPersonSearchCount.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonSearchCount() throws Exception {
        int databaseSizeBeforeUpdate = personSearchCountRepository.findAll().size();

        // Create the PersonSearchCount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonSearchCountMockMvc.perform(put("/api/person-search-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSearchCount)))
            .andExpect(status().isCreated());

        // Validate the PersonSearchCount in the database
        List<PersonSearchCount> personSearchCountList = personSearchCountRepository.findAll();
        assertThat(personSearchCountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonSearchCount() throws Exception {
        // Initialize the database
        personSearchCountService.save(personSearchCount);

        int databaseSizeBeforeDelete = personSearchCountRepository.findAll().size();

        // Get the personSearchCount
        restPersonSearchCountMockMvc.perform(delete("/api/person-search-counts/{id}", personSearchCount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonSearchCount> personSearchCountList = personSearchCountRepository.findAll();
        assertThat(personSearchCountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
