package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.LawBusinessType;
import com.intelligent.chart.repository.LawBusinessTypeRepository;
import com.intelligent.chart.service.LawBusinessTypeService;

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
 * Test class for the LawBusinessTypeResource REST controller.
 *
 * @see LawBusinessTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class LawBusinessTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private LawBusinessTypeRepository lawBusinessTypeRepository;

    @Inject
    private LawBusinessTypeService lawBusinessTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLawBusinessTypeMockMvc;

    private LawBusinessType lawBusinessType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LawBusinessTypeResource lawBusinessTypeResource = new LawBusinessTypeResource();
        ReflectionTestUtils.setField(lawBusinessTypeResource, "lawBusinessTypeService", lawBusinessTypeService);
        this.restLawBusinessTypeMockMvc = MockMvcBuilders.standaloneSetup(lawBusinessTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LawBusinessType createEntity(EntityManager em) {
        LawBusinessType lawBusinessType = new LawBusinessType()
                .name(DEFAULT_NAME);
        return lawBusinessType;
    }

    @Before
    public void initTest() {
        lawBusinessType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLawBusinessType() throws Exception {
        int databaseSizeBeforeCreate = lawBusinessTypeRepository.findAll().size();

        // Create the LawBusinessType

        restLawBusinessTypeMockMvc.perform(post("/api/law-business-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawBusinessType)))
            .andExpect(status().isCreated());

        // Validate the LawBusinessType in the database
        List<LawBusinessType> lawBusinessTypeList = lawBusinessTypeRepository.findAll();
        assertThat(lawBusinessTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LawBusinessType testLawBusinessType = lawBusinessTypeList.get(lawBusinessTypeList.size() - 1);
        assertThat(testLawBusinessType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLawBusinessTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lawBusinessTypeRepository.findAll().size();

        // Create the LawBusinessType with an existing ID
        LawBusinessType existingLawBusinessType = new LawBusinessType();
        existingLawBusinessType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLawBusinessTypeMockMvc.perform(post("/api/law-business-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLawBusinessType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LawBusinessType> lawBusinessTypeList = lawBusinessTypeRepository.findAll();
        assertThat(lawBusinessTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLawBusinessTypes() throws Exception {
        // Initialize the database
        lawBusinessTypeRepository.saveAndFlush(lawBusinessType);

        // Get all the lawBusinessTypeList
        restLawBusinessTypeMockMvc.perform(get("/api/law-business-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lawBusinessType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLawBusinessType() throws Exception {
        // Initialize the database
        lawBusinessTypeRepository.saveAndFlush(lawBusinessType);

        // Get the lawBusinessType
        restLawBusinessTypeMockMvc.perform(get("/api/law-business-types/{id}", lawBusinessType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lawBusinessType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLawBusinessType() throws Exception {
        // Get the lawBusinessType
        restLawBusinessTypeMockMvc.perform(get("/api/law-business-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLawBusinessType() throws Exception {
        // Initialize the database
        lawBusinessTypeService.save(lawBusinessType);

        int databaseSizeBeforeUpdate = lawBusinessTypeRepository.findAll().size();

        // Update the lawBusinessType
        LawBusinessType updatedLawBusinessType = lawBusinessTypeRepository.findOne(lawBusinessType.getId());
        updatedLawBusinessType
                .name(UPDATED_NAME);

        restLawBusinessTypeMockMvc.perform(put("/api/law-business-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLawBusinessType)))
            .andExpect(status().isOk());

        // Validate the LawBusinessType in the database
        List<LawBusinessType> lawBusinessTypeList = lawBusinessTypeRepository.findAll();
        assertThat(lawBusinessTypeList).hasSize(databaseSizeBeforeUpdate);
        LawBusinessType testLawBusinessType = lawBusinessTypeList.get(lawBusinessTypeList.size() - 1);
        assertThat(testLawBusinessType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLawBusinessType() throws Exception {
        int databaseSizeBeforeUpdate = lawBusinessTypeRepository.findAll().size();

        // Create the LawBusinessType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLawBusinessTypeMockMvc.perform(put("/api/law-business-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawBusinessType)))
            .andExpect(status().isCreated());

        // Validate the LawBusinessType in the database
        List<LawBusinessType> lawBusinessTypeList = lawBusinessTypeRepository.findAll();
        assertThat(lawBusinessTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLawBusinessType() throws Exception {
        // Initialize the database
        lawBusinessTypeService.save(lawBusinessType);

        int databaseSizeBeforeDelete = lawBusinessTypeRepository.findAll().size();

        // Get the lawBusinessType
        restLawBusinessTypeMockMvc.perform(delete("/api/law-business-types/{id}", lawBusinessType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LawBusinessType> lawBusinessTypeList = lawBusinessTypeRepository.findAll();
        assertThat(lawBusinessTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
