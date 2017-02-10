package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PrizeLevel;
import com.intelligent.chart.repository.PrizeLevelRepository;
import com.intelligent.chart.service.PrizeLevelService;

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
 * Test class for the PrizeLevelResource REST controller.
 *
 * @see PrizeLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PrizeLevelResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PrizeLevelRepository prizeLevelRepository;

    @Inject
    private PrizeLevelService prizeLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPrizeLevelMockMvc;

    private PrizeLevel prizeLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrizeLevelResource prizeLevelResource = new PrizeLevelResource();
        ReflectionTestUtils.setField(prizeLevelResource, "prizeLevelService", prizeLevelService);
        this.restPrizeLevelMockMvc = MockMvcBuilders.standaloneSetup(prizeLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizeLevel createEntity(EntityManager em) {
        PrizeLevel prizeLevel = new PrizeLevel()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return prizeLevel;
    }

    @Before
    public void initTest() {
        prizeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrizeLevel() throws Exception {
        int databaseSizeBeforeCreate = prizeLevelRepository.findAll().size();

        // Create the PrizeLevel

        restPrizeLevelMockMvc.perform(post("/api/prize-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeLevel)))
            .andExpect(status().isCreated());

        // Validate the PrizeLevel in the database
        List<PrizeLevel> prizeLevelList = prizeLevelRepository.findAll();
        assertThat(prizeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        PrizeLevel testPrizeLevel = prizeLevelList.get(prizeLevelList.size() - 1);
        assertThat(testPrizeLevel.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPrizeLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPrizeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prizeLevelRepository.findAll().size();

        // Create the PrizeLevel with an existing ID
        PrizeLevel existingPrizeLevel = new PrizeLevel();
        existingPrizeLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeLevelMockMvc.perform(post("/api/prize-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPrizeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PrizeLevel> prizeLevelList = prizeLevelRepository.findAll();
        assertThat(prizeLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrizeLevels() throws Exception {
        // Initialize the database
        prizeLevelRepository.saveAndFlush(prizeLevel);

        // Get all the prizeLevelList
        restPrizeLevelMockMvc.perform(get("/api/prize-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPrizeLevel() throws Exception {
        // Initialize the database
        prizeLevelRepository.saveAndFlush(prizeLevel);

        // Get the prizeLevel
        restPrizeLevelMockMvc.perform(get("/api/prize-levels/{id}", prizeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prizeLevel.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrizeLevel() throws Exception {
        // Get the prizeLevel
        restPrizeLevelMockMvc.perform(get("/api/prize-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrizeLevel() throws Exception {
        // Initialize the database
        prizeLevelService.save(prizeLevel);

        int databaseSizeBeforeUpdate = prizeLevelRepository.findAll().size();

        // Update the prizeLevel
        PrizeLevel updatedPrizeLevel = prizeLevelRepository.findOne(prizeLevel.getId());
        updatedPrizeLevel
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restPrizeLevelMockMvc.perform(put("/api/prize-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrizeLevel)))
            .andExpect(status().isOk());

        // Validate the PrizeLevel in the database
        List<PrizeLevel> prizeLevelList = prizeLevelRepository.findAll();
        assertThat(prizeLevelList).hasSize(databaseSizeBeforeUpdate);
        PrizeLevel testPrizeLevel = prizeLevelList.get(prizeLevelList.size() - 1);
        assertThat(testPrizeLevel.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPrizeLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrizeLevel() throws Exception {
        int databaseSizeBeforeUpdate = prizeLevelRepository.findAll().size();

        // Create the PrizeLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrizeLevelMockMvc.perform(put("/api/prize-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeLevel)))
            .andExpect(status().isCreated());

        // Validate the PrizeLevel in the database
        List<PrizeLevel> prizeLevelList = prizeLevelRepository.findAll();
        assertThat(prizeLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrizeLevel() throws Exception {
        // Initialize the database
        prizeLevelService.save(prizeLevel);

        int databaseSizeBeforeDelete = prizeLevelRepository.findAll().size();

        // Get the prizeLevel
        restPrizeLevelMockMvc.perform(delete("/api/prize-levels/{id}", prizeLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrizeLevel> prizeLevelList = prizeLevelRepository.findAll();
        assertThat(prizeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
