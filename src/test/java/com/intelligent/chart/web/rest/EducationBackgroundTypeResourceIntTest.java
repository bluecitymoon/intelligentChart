package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.EducationBackgroundType;
import com.intelligent.chart.repository.EducationBackgroundTypeRepository;
import com.intelligent.chart.service.EducationBackgroundTypeService;

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
 * Test class for the EducationBackgroundTypeResource REST controller.
 *
 * @see EducationBackgroundTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class EducationBackgroundTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private EducationBackgroundTypeRepository educationBackgroundTypeRepository;

    @Inject
    private EducationBackgroundTypeService educationBackgroundTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEducationBackgroundTypeMockMvc;

    private EducationBackgroundType educationBackgroundType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationBackgroundTypeResource educationBackgroundTypeResource = new EducationBackgroundTypeResource();
        ReflectionTestUtils.setField(educationBackgroundTypeResource, "educationBackgroundTypeService", educationBackgroundTypeService);
        this.restEducationBackgroundTypeMockMvc = MockMvcBuilders.standaloneSetup(educationBackgroundTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationBackgroundType createEntity(EntityManager em) {
        EducationBackgroundType educationBackgroundType = new EducationBackgroundType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return educationBackgroundType;
    }

    @Before
    public void initTest() {
        educationBackgroundType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationBackgroundType() throws Exception {
        int databaseSizeBeforeCreate = educationBackgroundTypeRepository.findAll().size();

        // Create the EducationBackgroundType

        restEducationBackgroundTypeMockMvc.perform(post("/api/education-background-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationBackgroundType)))
            .andExpect(status().isCreated());

        // Validate the EducationBackgroundType in the database
        List<EducationBackgroundType> educationBackgroundTypeList = educationBackgroundTypeRepository.findAll();
        assertThat(educationBackgroundTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EducationBackgroundType testEducationBackgroundType = educationBackgroundTypeList.get(educationBackgroundTypeList.size() - 1);
        assertThat(testEducationBackgroundType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testEducationBackgroundType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEducationBackgroundTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationBackgroundTypeRepository.findAll().size();

        // Create the EducationBackgroundType with an existing ID
        EducationBackgroundType existingEducationBackgroundType = new EducationBackgroundType();
        existingEducationBackgroundType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationBackgroundTypeMockMvc.perform(post("/api/education-background-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEducationBackgroundType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EducationBackgroundType> educationBackgroundTypeList = educationBackgroundTypeRepository.findAll();
        assertThat(educationBackgroundTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEducationBackgroundTypes() throws Exception {
        // Initialize the database
        educationBackgroundTypeRepository.saveAndFlush(educationBackgroundType);

        // Get all the educationBackgroundTypeList
        restEducationBackgroundTypeMockMvc.perform(get("/api/education-background-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationBackgroundType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEducationBackgroundType() throws Exception {
        // Initialize the database
        educationBackgroundTypeRepository.saveAndFlush(educationBackgroundType);

        // Get the educationBackgroundType
        restEducationBackgroundTypeMockMvc.perform(get("/api/education-background-types/{id}", educationBackgroundType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationBackgroundType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducationBackgroundType() throws Exception {
        // Get the educationBackgroundType
        restEducationBackgroundTypeMockMvc.perform(get("/api/education-background-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationBackgroundType() throws Exception {
        // Initialize the database
        educationBackgroundTypeService.save(educationBackgroundType);

        int databaseSizeBeforeUpdate = educationBackgroundTypeRepository.findAll().size();

        // Update the educationBackgroundType
        EducationBackgroundType updatedEducationBackgroundType = educationBackgroundTypeRepository.findOne(educationBackgroundType.getId());
        updatedEducationBackgroundType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restEducationBackgroundTypeMockMvc.perform(put("/api/education-background-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationBackgroundType)))
            .andExpect(status().isOk());

        // Validate the EducationBackgroundType in the database
        List<EducationBackgroundType> educationBackgroundTypeList = educationBackgroundTypeRepository.findAll();
        assertThat(educationBackgroundTypeList).hasSize(databaseSizeBeforeUpdate);
        EducationBackgroundType testEducationBackgroundType = educationBackgroundTypeList.get(educationBackgroundTypeList.size() - 1);
        assertThat(testEducationBackgroundType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testEducationBackgroundType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationBackgroundType() throws Exception {
        int databaseSizeBeforeUpdate = educationBackgroundTypeRepository.findAll().size();

        // Create the EducationBackgroundType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationBackgroundTypeMockMvc.perform(put("/api/education-background-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationBackgroundType)))
            .andExpect(status().isCreated());

        // Validate the EducationBackgroundType in the database
        List<EducationBackgroundType> educationBackgroundTypeList = educationBackgroundTypeRepository.findAll();
        assertThat(educationBackgroundTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationBackgroundType() throws Exception {
        // Initialize the database
        educationBackgroundTypeService.save(educationBackgroundType);

        int databaseSizeBeforeDelete = educationBackgroundTypeRepository.findAll().size();

        // Get the educationBackgroundType
        restEducationBackgroundTypeMockMvc.perform(delete("/api/education-background-types/{id}", educationBackgroundType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationBackgroundType> educationBackgroundTypeList = educationBackgroundTypeRepository.findAll();
        assertThat(educationBackgroundTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
