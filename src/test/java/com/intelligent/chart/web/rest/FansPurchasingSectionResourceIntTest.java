package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.FansPurchasingSection;
import com.intelligent.chart.repository.FansPurchasingSectionRepository;
import com.intelligent.chart.service.FansPurchasingSectionService;

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
 * Test class for the FansPurchasingSectionResource REST controller.
 *
 * @see FansPurchasingSectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class FansPurchasingSectionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private FansPurchasingSectionRepository fansPurchasingSectionRepository;

    @Inject
    private FansPurchasingSectionService fansPurchasingSectionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFansPurchasingSectionMockMvc;

    private FansPurchasingSection fansPurchasingSection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FansPurchasingSectionResource fansPurchasingSectionResource = new FansPurchasingSectionResource();
        ReflectionTestUtils.setField(fansPurchasingSectionResource, "fansPurchasingSectionService", fansPurchasingSectionService);
        this.restFansPurchasingSectionMockMvc = MockMvcBuilders.standaloneSetup(fansPurchasingSectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FansPurchasingSection createEntity(EntityManager em) {
        FansPurchasingSection fansPurchasingSection = new FansPurchasingSection()
                .name(DEFAULT_NAME);
        return fansPurchasingSection;
    }

    @Before
    public void initTest() {
        fansPurchasingSection = createEntity(em);
    }

    @Test
    @Transactional
    public void createFansPurchasingSection() throws Exception {
        int databaseSizeBeforeCreate = fansPurchasingSectionRepository.findAll().size();

        // Create the FansPurchasingSection

        restFansPurchasingSectionMockMvc.perform(post("/api/fans-purchasing-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fansPurchasingSection)))
            .andExpect(status().isCreated());

        // Validate the FansPurchasingSection in the database
        List<FansPurchasingSection> fansPurchasingSectionList = fansPurchasingSectionRepository.findAll();
        assertThat(fansPurchasingSectionList).hasSize(databaseSizeBeforeCreate + 1);
        FansPurchasingSection testFansPurchasingSection = fansPurchasingSectionList.get(fansPurchasingSectionList.size() - 1);
        assertThat(testFansPurchasingSection.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFansPurchasingSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fansPurchasingSectionRepository.findAll().size();

        // Create the FansPurchasingSection with an existing ID
        FansPurchasingSection existingFansPurchasingSection = new FansPurchasingSection();
        existingFansPurchasingSection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFansPurchasingSectionMockMvc.perform(post("/api/fans-purchasing-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFansPurchasingSection)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FansPurchasingSection> fansPurchasingSectionList = fansPurchasingSectionRepository.findAll();
        assertThat(fansPurchasingSectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFansPurchasingSections() throws Exception {
        // Initialize the database
        fansPurchasingSectionRepository.saveAndFlush(fansPurchasingSection);

        // Get all the fansPurchasingSectionList
        restFansPurchasingSectionMockMvc.perform(get("/api/fans-purchasing-sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fansPurchasingSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFansPurchasingSection() throws Exception {
        // Initialize the database
        fansPurchasingSectionRepository.saveAndFlush(fansPurchasingSection);

        // Get the fansPurchasingSection
        restFansPurchasingSectionMockMvc.perform(get("/api/fans-purchasing-sections/{id}", fansPurchasingSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fansPurchasingSection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFansPurchasingSection() throws Exception {
        // Get the fansPurchasingSection
        restFansPurchasingSectionMockMvc.perform(get("/api/fans-purchasing-sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFansPurchasingSection() throws Exception {
        // Initialize the database
        fansPurchasingSectionService.save(fansPurchasingSection);

        int databaseSizeBeforeUpdate = fansPurchasingSectionRepository.findAll().size();

        // Update the fansPurchasingSection
        FansPurchasingSection updatedFansPurchasingSection = fansPurchasingSectionRepository.findOne(fansPurchasingSection.getId());
        updatedFansPurchasingSection
                .name(UPDATED_NAME);

        restFansPurchasingSectionMockMvc.perform(put("/api/fans-purchasing-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFansPurchasingSection)))
            .andExpect(status().isOk());

        // Validate the FansPurchasingSection in the database
        List<FansPurchasingSection> fansPurchasingSectionList = fansPurchasingSectionRepository.findAll();
        assertThat(fansPurchasingSectionList).hasSize(databaseSizeBeforeUpdate);
        FansPurchasingSection testFansPurchasingSection = fansPurchasingSectionList.get(fansPurchasingSectionList.size() - 1);
        assertThat(testFansPurchasingSection.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFansPurchasingSection() throws Exception {
        int databaseSizeBeforeUpdate = fansPurchasingSectionRepository.findAll().size();

        // Create the FansPurchasingSection

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFansPurchasingSectionMockMvc.perform(put("/api/fans-purchasing-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fansPurchasingSection)))
            .andExpect(status().isCreated());

        // Validate the FansPurchasingSection in the database
        List<FansPurchasingSection> fansPurchasingSectionList = fansPurchasingSectionRepository.findAll();
        assertThat(fansPurchasingSectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFansPurchasingSection() throws Exception {
        // Initialize the database
        fansPurchasingSectionService.save(fansPurchasingSection);

        int databaseSizeBeforeDelete = fansPurchasingSectionRepository.findAll().size();

        // Get the fansPurchasingSection
        restFansPurchasingSectionMockMvc.perform(delete("/api/fans-purchasing-sections/{id}", fansPurchasingSection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FansPurchasingSection> fansPurchasingSectionList = fansPurchasingSectionRepository.findAll();
        assertThat(fansPurchasingSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
