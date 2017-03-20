package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PopularityType;
import com.intelligent.chart.repository.PopularityTypeRepository;
import com.intelligent.chart.service.PopularityTypeService;

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
 * Test class for the PopularityTypeResource REST controller.
 *
 * @see PopularityTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PopularityTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PopularityTypeRepository popularityTypeRepository;

    @Inject
    private PopularityTypeService popularityTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPopularityTypeMockMvc;

    private PopularityType popularityType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PopularityTypeResource popularityTypeResource = new PopularityTypeResource();
        ReflectionTestUtils.setField(popularityTypeResource, "popularityTypeService", popularityTypeService);
        this.restPopularityTypeMockMvc = MockMvcBuilders.standaloneSetup(popularityTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopularityType createEntity(EntityManager em) {
        PopularityType popularityType = new PopularityType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return popularityType;
    }

    @Before
    public void initTest() {
        popularityType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopularityType() throws Exception {
        int databaseSizeBeforeCreate = popularityTypeRepository.findAll().size();

        // Create the PopularityType

        restPopularityTypeMockMvc.perform(post("/api/popularity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popularityType)))
            .andExpect(status().isCreated());

        // Validate the PopularityType in the database
        List<PopularityType> popularityTypeList = popularityTypeRepository.findAll();
        assertThat(popularityTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PopularityType testPopularityType = popularityTypeList.get(popularityTypeList.size() - 1);
        assertThat(testPopularityType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPopularityType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPopularityTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popularityTypeRepository.findAll().size();

        // Create the PopularityType with an existing ID
        PopularityType existingPopularityType = new PopularityType();
        existingPopularityType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopularityTypeMockMvc.perform(post("/api/popularity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPopularityType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PopularityType> popularityTypeList = popularityTypeRepository.findAll();
        assertThat(popularityTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPopularityTypes() throws Exception {
        // Initialize the database
        popularityTypeRepository.saveAndFlush(popularityType);

        // Get all the popularityTypeList
        restPopularityTypeMockMvc.perform(get("/api/popularity-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popularityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPopularityType() throws Exception {
        // Initialize the database
        popularityTypeRepository.saveAndFlush(popularityType);

        // Get the popularityType
        restPopularityTypeMockMvc.perform(get("/api/popularity-types/{id}", popularityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(popularityType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPopularityType() throws Exception {
        // Get the popularityType
        restPopularityTypeMockMvc.perform(get("/api/popularity-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopularityType() throws Exception {
        // Initialize the database
        popularityTypeService.save(popularityType);

        int databaseSizeBeforeUpdate = popularityTypeRepository.findAll().size();

        // Update the popularityType
        PopularityType updatedPopularityType = popularityTypeRepository.findOne(popularityType.getId());
        updatedPopularityType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restPopularityTypeMockMvc.perform(put("/api/popularity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPopularityType)))
            .andExpect(status().isOk());

        // Validate the PopularityType in the database
        List<PopularityType> popularityTypeList = popularityTypeRepository.findAll();
        assertThat(popularityTypeList).hasSize(databaseSizeBeforeUpdate);
        PopularityType testPopularityType = popularityTypeList.get(popularityTypeList.size() - 1);
        assertThat(testPopularityType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPopularityType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPopularityType() throws Exception {
        int databaseSizeBeforeUpdate = popularityTypeRepository.findAll().size();

        // Create the PopularityType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPopularityTypeMockMvc.perform(put("/api/popularity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(popularityType)))
            .andExpect(status().isCreated());

        // Validate the PopularityType in the database
        List<PopularityType> popularityTypeList = popularityTypeRepository.findAll();
        assertThat(popularityTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePopularityType() throws Exception {
        // Initialize the database
        popularityTypeService.save(popularityType);

        int databaseSizeBeforeDelete = popularityTypeRepository.findAll().size();

        // Get the popularityType
        restPopularityTypeMockMvc.perform(delete("/api/popularity-types/{id}", popularityType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PopularityType> popularityTypeList = popularityTypeRepository.findAll();
        assertThat(popularityTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
