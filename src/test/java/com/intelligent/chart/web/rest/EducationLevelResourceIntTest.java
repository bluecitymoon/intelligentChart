package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.EducationLevel;
import com.intelligent.chart.repository.EducationLevelRepository;
import com.intelligent.chart.service.EducationLevelService;

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
 * Test class for the EducationLevelResource REST controller.
 *
 * @see EducationLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class EducationLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private EducationLevelRepository educationLevelRepository;

    @Inject
    private EducationLevelService educationLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEducationLevelMockMvc;

    private EducationLevel educationLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationLevelResource educationLevelResource = new EducationLevelResource();
        ReflectionTestUtils.setField(educationLevelResource, "educationLevelService", educationLevelService);
        this.restEducationLevelMockMvc = MockMvcBuilders.standaloneSetup(educationLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationLevel createEntity(EntityManager em) {
        EducationLevel educationLevel = new EducationLevel()
                .name(DEFAULT_NAME);
        return educationLevel;
    }

    @Before
    public void initTest() {
        educationLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationLevel() throws Exception {
        int databaseSizeBeforeCreate = educationLevelRepository.findAll().size();

        // Create the EducationLevel

        restEducationLevelMockMvc.perform(post("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevel)))
            .andExpect(status().isCreated());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeCreate + 1);
        EducationLevel testEducationLevel = educationLevelList.get(educationLevelList.size() - 1);
        assertThat(testEducationLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEducationLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationLevelRepository.findAll().size();

        // Create the EducationLevel with an existing ID
        EducationLevel existingEducationLevel = new EducationLevel();
        existingEducationLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationLevelMockMvc.perform(post("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEducationLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEducationLevels() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList
        restEducationLevelMockMvc.perform(get("/api/education-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEducationLevel() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get the educationLevel
        restEducationLevelMockMvc.perform(get("/api/education-levels/{id}", educationLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducationLevel() throws Exception {
        // Get the educationLevel
        restEducationLevelMockMvc.perform(get("/api/education-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationLevel() throws Exception {
        // Initialize the database
        educationLevelService.save(educationLevel);

        int databaseSizeBeforeUpdate = educationLevelRepository.findAll().size();

        // Update the educationLevel
        EducationLevel updatedEducationLevel = educationLevelRepository.findOne(educationLevel.getId());
        updatedEducationLevel
                .name(UPDATED_NAME);

        restEducationLevelMockMvc.perform(put("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationLevel)))
            .andExpect(status().isOk());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeUpdate);
        EducationLevel testEducationLevel = educationLevelList.get(educationLevelList.size() - 1);
        assertThat(testEducationLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationLevel() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelRepository.findAll().size();

        // Create the EducationLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationLevelMockMvc.perform(put("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevel)))
            .andExpect(status().isCreated());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationLevel() throws Exception {
        // Initialize the database
        educationLevelService.save(educationLevel);

        int databaseSizeBeforeDelete = educationLevelRepository.findAll().size();

        // Get the educationLevel
        restEducationLevelMockMvc.perform(delete("/api/education-levels/{id}", educationLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
