package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonEducationBackground;
import com.intelligent.chart.repository.PersonEducationBackgroundRepository;
import com.intelligent.chart.service.PersonEducationBackgroundService;

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
 * Test class for the PersonEducationBackgroundResource REST controller.
 *
 * @see PersonEducationBackgroundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonEducationBackgroundResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private PersonEducationBackgroundRepository personEducationBackgroundRepository;

    @Inject
    private PersonEducationBackgroundService personEducationBackgroundService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonEducationBackgroundMockMvc;

    private PersonEducationBackground personEducationBackground;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonEducationBackgroundResource personEducationBackgroundResource = new PersonEducationBackgroundResource();
        ReflectionTestUtils.setField(personEducationBackgroundResource, "personEducationBackgroundService", personEducationBackgroundService);
        this.restPersonEducationBackgroundMockMvc = MockMvcBuilders.standaloneSetup(personEducationBackgroundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEducationBackground createEntity(EntityManager em) {
        PersonEducationBackground personEducationBackground = new PersonEducationBackground()
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .description(DEFAULT_DESCRIPTION);
        return personEducationBackground;
    }

    @Before
    public void initTest() {
        personEducationBackground = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEducationBackground() throws Exception {
        int databaseSizeBeforeCreate = personEducationBackgroundRepository.findAll().size();

        // Create the PersonEducationBackground

        restPersonEducationBackgroundMockMvc.perform(post("/api/person-education-backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEducationBackground)))
            .andExpect(status().isCreated());

        // Validate the PersonEducationBackground in the database
        List<PersonEducationBackground> personEducationBackgroundList = personEducationBackgroundRepository.findAll();
        assertThat(personEducationBackgroundList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEducationBackground testPersonEducationBackground = personEducationBackgroundList.get(personEducationBackgroundList.size() - 1);
        assertThat(testPersonEducationBackground.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPersonEducationBackground.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPersonEducationBackground.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPersonEducationBackgroundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEducationBackgroundRepository.findAll().size();

        // Create the PersonEducationBackground with an existing ID
        PersonEducationBackground existingPersonEducationBackground = new PersonEducationBackground();
        existingPersonEducationBackground.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEducationBackgroundMockMvc.perform(post("/api/person-education-backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonEducationBackground)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonEducationBackground> personEducationBackgroundList = personEducationBackgroundRepository.findAll();
        assertThat(personEducationBackgroundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonEducationBackgrounds() throws Exception {
        // Initialize the database
        personEducationBackgroundRepository.saveAndFlush(personEducationBackground);

        // Get all the personEducationBackgroundList
        restPersonEducationBackgroundMockMvc.perform(get("/api/person-education-backgrounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEducationBackground.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonEducationBackground() throws Exception {
        // Initialize the database
        personEducationBackgroundRepository.saveAndFlush(personEducationBackground);

        // Get the personEducationBackground
        restPersonEducationBackgroundMockMvc.perform(get("/api/person-education-backgrounds/{id}", personEducationBackground.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personEducationBackground.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonEducationBackground() throws Exception {
        // Get the personEducationBackground
        restPersonEducationBackgroundMockMvc.perform(get("/api/person-education-backgrounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEducationBackground() throws Exception {
        // Initialize the database
        personEducationBackgroundService.save(personEducationBackground);

        int databaseSizeBeforeUpdate = personEducationBackgroundRepository.findAll().size();

        // Update the personEducationBackground
        PersonEducationBackground updatedPersonEducationBackground = personEducationBackgroundRepository.findOne(personEducationBackground.getId());
        updatedPersonEducationBackground
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .description(UPDATED_DESCRIPTION);

        restPersonEducationBackgroundMockMvc.perform(put("/api/person-education-backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonEducationBackground)))
            .andExpect(status().isOk());

        // Validate the PersonEducationBackground in the database
        List<PersonEducationBackground> personEducationBackgroundList = personEducationBackgroundRepository.findAll();
        assertThat(personEducationBackgroundList).hasSize(databaseSizeBeforeUpdate);
        PersonEducationBackground testPersonEducationBackground = personEducationBackgroundList.get(personEducationBackgroundList.size() - 1);
        assertThat(testPersonEducationBackground.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPersonEducationBackground.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPersonEducationBackground.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEducationBackground() throws Exception {
        int databaseSizeBeforeUpdate = personEducationBackgroundRepository.findAll().size();

        // Create the PersonEducationBackground

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonEducationBackgroundMockMvc.perform(put("/api/person-education-backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEducationBackground)))
            .andExpect(status().isCreated());

        // Validate the PersonEducationBackground in the database
        List<PersonEducationBackground> personEducationBackgroundList = personEducationBackgroundRepository.findAll();
        assertThat(personEducationBackgroundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonEducationBackground() throws Exception {
        // Initialize the database
        personEducationBackgroundService.save(personEducationBackground);

        int databaseSizeBeforeDelete = personEducationBackgroundRepository.findAll().size();

        // Get the personEducationBackground
        restPersonEducationBackgroundMockMvc.perform(delete("/api/person-education-backgrounds/{id}", personEducationBackground.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonEducationBackground> personEducationBackgroundList = personEducationBackgroundRepository.findAll();
        assertThat(personEducationBackgroundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
