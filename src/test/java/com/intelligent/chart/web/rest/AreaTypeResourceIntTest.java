package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.AreaType;
import com.intelligent.chart.repository.AreaTypeRepository;
import com.intelligent.chart.service.AreaTypeService;

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
 * Test class for the AreaTypeResource REST controller.
 *
 * @see AreaTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class AreaTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private AreaTypeRepository areaTypeRepository;

    @Inject
    private AreaTypeService areaTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAreaTypeMockMvc;

    private AreaType areaType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AreaTypeResource areaTypeResource = new AreaTypeResource();
        ReflectionTestUtils.setField(areaTypeResource, "areaTypeService", areaTypeService);
        this.restAreaTypeMockMvc = MockMvcBuilders.standaloneSetup(areaTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaType createEntity(EntityManager em) {
        AreaType areaType = new AreaType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return areaType;
    }

    @Before
    public void initTest() {
        areaType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaType() throws Exception {
        int databaseSizeBeforeCreate = areaTypeRepository.findAll().size();

        // Create the AreaType

        restAreaTypeMockMvc.perform(post("/api/area-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaType)))
            .andExpect(status().isCreated());

        // Validate the AreaType in the database
        List<AreaType> areaTypeList = areaTypeRepository.findAll();
        assertThat(areaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AreaType testAreaType = areaTypeList.get(areaTypeList.size() - 1);
        assertThat(testAreaType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testAreaType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAreaTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaTypeRepository.findAll().size();

        // Create the AreaType with an existing ID
        AreaType existingAreaType = new AreaType();
        existingAreaType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaTypeMockMvc.perform(post("/api/area-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAreaType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AreaType> areaTypeList = areaTypeRepository.findAll();
        assertThat(areaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAreaTypes() throws Exception {
        // Initialize the database
        areaTypeRepository.saveAndFlush(areaType);

        // Get all the areaTypeList
        restAreaTypeMockMvc.perform(get("/api/area-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAreaType() throws Exception {
        // Initialize the database
        areaTypeRepository.saveAndFlush(areaType);

        // Get the areaType
        restAreaTypeMockMvc.perform(get("/api/area-types/{id}", areaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(areaType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAreaType() throws Exception {
        // Get the areaType
        restAreaTypeMockMvc.perform(get("/api/area-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaType() throws Exception {
        // Initialize the database
        areaTypeService.save(areaType);

        int databaseSizeBeforeUpdate = areaTypeRepository.findAll().size();

        // Update the areaType
        AreaType updatedAreaType = areaTypeRepository.findOne(areaType.getId());
        updatedAreaType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restAreaTypeMockMvc.perform(put("/api/area-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAreaType)))
            .andExpect(status().isOk());

        // Validate the AreaType in the database
        List<AreaType> areaTypeList = areaTypeRepository.findAll();
        assertThat(areaTypeList).hasSize(databaseSizeBeforeUpdate);
        AreaType testAreaType = areaTypeList.get(areaTypeList.size() - 1);
        assertThat(testAreaType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testAreaType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaType() throws Exception {
        int databaseSizeBeforeUpdate = areaTypeRepository.findAll().size();

        // Create the AreaType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAreaTypeMockMvc.perform(put("/api/area-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaType)))
            .andExpect(status().isCreated());

        // Validate the AreaType in the database
        List<AreaType> areaTypeList = areaTypeRepository.findAll();
        assertThat(areaTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAreaType() throws Exception {
        // Initialize the database
        areaTypeService.save(areaType);

        int databaseSizeBeforeDelete = areaTypeRepository.findAll().size();

        // Get the areaType
        restAreaTypeMockMvc.perform(delete("/api/area-types/{id}", areaType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AreaType> areaTypeList = areaTypeRepository.findAll();
        assertThat(areaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
