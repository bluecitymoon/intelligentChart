package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.EgeLevel;
import com.intelligent.chart.repository.EgeLevelRepository;
import com.intelligent.chart.service.EgeLevelService;

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
 * Test class for the EgeLevelResource REST controller.
 *
 * @see EgeLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class EgeLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private EgeLevelRepository egeLevelRepository;

    @Inject
    private EgeLevelService egeLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEgeLevelMockMvc;

    private EgeLevel egeLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EgeLevelResource egeLevelResource = new EgeLevelResource();
        ReflectionTestUtils.setField(egeLevelResource, "egeLevelService", egeLevelService);
        this.restEgeLevelMockMvc = MockMvcBuilders.standaloneSetup(egeLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EgeLevel createEntity(EntityManager em) {
        EgeLevel egeLevel = new EgeLevel()
                .name(DEFAULT_NAME);
        return egeLevel;
    }

    @Before
    public void initTest() {
        egeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createEgeLevel() throws Exception {
        int databaseSizeBeforeCreate = egeLevelRepository.findAll().size();

        // Create the EgeLevel

        restEgeLevelMockMvc.perform(post("/api/ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(egeLevel)))
            .andExpect(status().isCreated());

        // Validate the EgeLevel in the database
        List<EgeLevel> egeLevelList = egeLevelRepository.findAll();
        assertThat(egeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        EgeLevel testEgeLevel = egeLevelList.get(egeLevelList.size() - 1);
        assertThat(testEgeLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEgeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = egeLevelRepository.findAll().size();

        // Create the EgeLevel with an existing ID
        EgeLevel existingEgeLevel = new EgeLevel();
        existingEgeLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEgeLevelMockMvc.perform(post("/api/ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEgeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EgeLevel> egeLevelList = egeLevelRepository.findAll();
        assertThat(egeLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEgeLevels() throws Exception {
        // Initialize the database
        egeLevelRepository.saveAndFlush(egeLevel);

        // Get all the egeLevelList
        restEgeLevelMockMvc.perform(get("/api/ege-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(egeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEgeLevel() throws Exception {
        // Initialize the database
        egeLevelRepository.saveAndFlush(egeLevel);

        // Get the egeLevel
        restEgeLevelMockMvc.perform(get("/api/ege-levels/{id}", egeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(egeLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEgeLevel() throws Exception {
        // Get the egeLevel
        restEgeLevelMockMvc.perform(get("/api/ege-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEgeLevel() throws Exception {
        // Initialize the database
        egeLevelService.save(egeLevel);

        int databaseSizeBeforeUpdate = egeLevelRepository.findAll().size();

        // Update the egeLevel
        EgeLevel updatedEgeLevel = egeLevelRepository.findOne(egeLevel.getId());
        updatedEgeLevel
                .name(UPDATED_NAME);

        restEgeLevelMockMvc.perform(put("/api/ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEgeLevel)))
            .andExpect(status().isOk());

        // Validate the EgeLevel in the database
        List<EgeLevel> egeLevelList = egeLevelRepository.findAll();
        assertThat(egeLevelList).hasSize(databaseSizeBeforeUpdate);
        EgeLevel testEgeLevel = egeLevelList.get(egeLevelList.size() - 1);
        assertThat(testEgeLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEgeLevel() throws Exception {
        int databaseSizeBeforeUpdate = egeLevelRepository.findAll().size();

        // Create the EgeLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEgeLevelMockMvc.perform(put("/api/ege-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(egeLevel)))
            .andExpect(status().isCreated());

        // Validate the EgeLevel in the database
        List<EgeLevel> egeLevelList = egeLevelRepository.findAll();
        assertThat(egeLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEgeLevel() throws Exception {
        // Initialize the database
        egeLevelService.save(egeLevel);

        int databaseSizeBeforeDelete = egeLevelRepository.findAll().size();

        // Get the egeLevel
        restEgeLevelMockMvc.perform(delete("/api/ege-levels/{id}", egeLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EgeLevel> egeLevelList = egeLevelRepository.findAll();
        assertThat(egeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
