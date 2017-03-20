package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonMediaShowUpCount;
import com.intelligent.chart.repository.PersonMediaShowUpCountRepository;
import com.intelligent.chart.service.PersonMediaShowUpCountService;

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
 * Test class for the PersonMediaShowUpCountResource REST controller.
 *
 * @see PersonMediaShowUpCountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonMediaShowUpCountResourceIntTest {

    private static final LocalDate DEFAULT_SHOW_UP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHOW_UP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonMediaShowUpCountRepository personMediaShowUpCountRepository;

    @Inject
    private PersonMediaShowUpCountService personMediaShowUpCountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonMediaShowUpCountMockMvc;

    private PersonMediaShowUpCount personMediaShowUpCount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonMediaShowUpCountResource personMediaShowUpCountResource = new PersonMediaShowUpCountResource();
        ReflectionTestUtils.setField(personMediaShowUpCountResource, "personMediaShowUpCountService", personMediaShowUpCountService);
        this.restPersonMediaShowUpCountMockMvc = MockMvcBuilders.standaloneSetup(personMediaShowUpCountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonMediaShowUpCount createEntity(EntityManager em) {
        PersonMediaShowUpCount personMediaShowUpCount = new PersonMediaShowUpCount()
                .showUpDate(DEFAULT_SHOW_UP_DATE)
                .count(DEFAULT_COUNT);
        return personMediaShowUpCount;
    }

    @Before
    public void initTest() {
        personMediaShowUpCount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonMediaShowUpCount() throws Exception {
        int databaseSizeBeforeCreate = personMediaShowUpCountRepository.findAll().size();

        // Create the PersonMediaShowUpCount

        restPersonMediaShowUpCountMockMvc.perform(post("/api/person-media-show-up-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personMediaShowUpCount)))
            .andExpect(status().isCreated());

        // Validate the PersonMediaShowUpCount in the database
        List<PersonMediaShowUpCount> personMediaShowUpCountList = personMediaShowUpCountRepository.findAll();
        assertThat(personMediaShowUpCountList).hasSize(databaseSizeBeforeCreate + 1);
        PersonMediaShowUpCount testPersonMediaShowUpCount = personMediaShowUpCountList.get(personMediaShowUpCountList.size() - 1);
        assertThat(testPersonMediaShowUpCount.getShowUpDate()).isEqualTo(DEFAULT_SHOW_UP_DATE);
        assertThat(testPersonMediaShowUpCount.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonMediaShowUpCountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personMediaShowUpCountRepository.findAll().size();

        // Create the PersonMediaShowUpCount with an existing ID
        PersonMediaShowUpCount existingPersonMediaShowUpCount = new PersonMediaShowUpCount();
        existingPersonMediaShowUpCount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMediaShowUpCountMockMvc.perform(post("/api/person-media-show-up-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonMediaShowUpCount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonMediaShowUpCount> personMediaShowUpCountList = personMediaShowUpCountRepository.findAll();
        assertThat(personMediaShowUpCountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonMediaShowUpCounts() throws Exception {
        // Initialize the database
        personMediaShowUpCountRepository.saveAndFlush(personMediaShowUpCount);

        // Get all the personMediaShowUpCountList
        restPersonMediaShowUpCountMockMvc.perform(get("/api/person-media-show-up-counts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personMediaShowUpCount.getId().intValue())))
            .andExpect(jsonPath("$.[*].showUpDate").value(hasItem(DEFAULT_SHOW_UP_DATE.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonMediaShowUpCount() throws Exception {
        // Initialize the database
        personMediaShowUpCountRepository.saveAndFlush(personMediaShowUpCount);

        // Get the personMediaShowUpCount
        restPersonMediaShowUpCountMockMvc.perform(get("/api/person-media-show-up-counts/{id}", personMediaShowUpCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personMediaShowUpCount.getId().intValue()))
            .andExpect(jsonPath("$.showUpDate").value(DEFAULT_SHOW_UP_DATE.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonMediaShowUpCount() throws Exception {
        // Get the personMediaShowUpCount
        restPersonMediaShowUpCountMockMvc.perform(get("/api/person-media-show-up-counts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonMediaShowUpCount() throws Exception {
        // Initialize the database
        personMediaShowUpCountService.save(personMediaShowUpCount);

        int databaseSizeBeforeUpdate = personMediaShowUpCountRepository.findAll().size();

        // Update the personMediaShowUpCount
        PersonMediaShowUpCount updatedPersonMediaShowUpCount = personMediaShowUpCountRepository.findOne(personMediaShowUpCount.getId());
        updatedPersonMediaShowUpCount
                .showUpDate(UPDATED_SHOW_UP_DATE)
                .count(UPDATED_COUNT);

        restPersonMediaShowUpCountMockMvc.perform(put("/api/person-media-show-up-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonMediaShowUpCount)))
            .andExpect(status().isOk());

        // Validate the PersonMediaShowUpCount in the database
        List<PersonMediaShowUpCount> personMediaShowUpCountList = personMediaShowUpCountRepository.findAll();
        assertThat(personMediaShowUpCountList).hasSize(databaseSizeBeforeUpdate);
        PersonMediaShowUpCount testPersonMediaShowUpCount = personMediaShowUpCountList.get(personMediaShowUpCountList.size() - 1);
        assertThat(testPersonMediaShowUpCount.getShowUpDate()).isEqualTo(UPDATED_SHOW_UP_DATE);
        assertThat(testPersonMediaShowUpCount.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonMediaShowUpCount() throws Exception {
        int databaseSizeBeforeUpdate = personMediaShowUpCountRepository.findAll().size();

        // Create the PersonMediaShowUpCount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMediaShowUpCountMockMvc.perform(put("/api/person-media-show-up-counts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personMediaShowUpCount)))
            .andExpect(status().isCreated());

        // Validate the PersonMediaShowUpCount in the database
        List<PersonMediaShowUpCount> personMediaShowUpCountList = personMediaShowUpCountRepository.findAll();
        assertThat(personMediaShowUpCountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonMediaShowUpCount() throws Exception {
        // Initialize the database
        personMediaShowUpCountService.save(personMediaShowUpCount);

        int databaseSizeBeforeDelete = personMediaShowUpCountRepository.findAll().size();

        // Get the personMediaShowUpCount
        restPersonMediaShowUpCountMockMvc.perform(delete("/api/person-media-show-up-counts/{id}", personMediaShowUpCount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonMediaShowUpCount> personMediaShowUpCountList = personMediaShowUpCountRepository.findAll();
        assertThat(personMediaShowUpCountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
