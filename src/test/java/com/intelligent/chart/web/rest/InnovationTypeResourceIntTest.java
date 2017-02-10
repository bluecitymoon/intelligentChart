package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.InnovationType;
import com.intelligent.chart.repository.InnovationTypeRepository;
import com.intelligent.chart.service.InnovationTypeService;

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
 * Test class for the InnovationTypeResource REST controller.
 *
 * @see InnovationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class InnovationTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private InnovationTypeRepository innovationTypeRepository;

    @Inject
    private InnovationTypeService innovationTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInnovationTypeMockMvc;

    private InnovationType innovationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InnovationTypeResource innovationTypeResource = new InnovationTypeResource();
        ReflectionTestUtils.setField(innovationTypeResource, "innovationTypeService", innovationTypeService);
        this.restInnovationTypeMockMvc = MockMvcBuilders.standaloneSetup(innovationTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InnovationType createEntity(EntityManager em) {
        InnovationType innovationType = new InnovationType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return innovationType;
    }

    @Before
    public void initTest() {
        innovationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInnovationType() throws Exception {
        int databaseSizeBeforeCreate = innovationTypeRepository.findAll().size();

        // Create the InnovationType

        restInnovationTypeMockMvc.perform(post("/api/innovation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(innovationType)))
            .andExpect(status().isCreated());

        // Validate the InnovationType in the database
        List<InnovationType> innovationTypeList = innovationTypeRepository.findAll();
        assertThat(innovationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InnovationType testInnovationType = innovationTypeList.get(innovationTypeList.size() - 1);
        assertThat(testInnovationType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testInnovationType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createInnovationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = innovationTypeRepository.findAll().size();

        // Create the InnovationType with an existing ID
        InnovationType existingInnovationType = new InnovationType();
        existingInnovationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInnovationTypeMockMvc.perform(post("/api/innovation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingInnovationType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InnovationType> innovationTypeList = innovationTypeRepository.findAll();
        assertThat(innovationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInnovationTypes() throws Exception {
        // Initialize the database
        innovationTypeRepository.saveAndFlush(innovationType);

        // Get all the innovationTypeList
        restInnovationTypeMockMvc.perform(get("/api/innovation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(innovationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getInnovationType() throws Exception {
        // Initialize the database
        innovationTypeRepository.saveAndFlush(innovationType);

        // Get the innovationType
        restInnovationTypeMockMvc.perform(get("/api/innovation-types/{id}", innovationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(innovationType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInnovationType() throws Exception {
        // Get the innovationType
        restInnovationTypeMockMvc.perform(get("/api/innovation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInnovationType() throws Exception {
        // Initialize the database
        innovationTypeService.save(innovationType);

        int databaseSizeBeforeUpdate = innovationTypeRepository.findAll().size();

        // Update the innovationType
        InnovationType updatedInnovationType = innovationTypeRepository.findOne(innovationType.getId());
        updatedInnovationType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restInnovationTypeMockMvc.perform(put("/api/innovation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInnovationType)))
            .andExpect(status().isOk());

        // Validate the InnovationType in the database
        List<InnovationType> innovationTypeList = innovationTypeRepository.findAll();
        assertThat(innovationTypeList).hasSize(databaseSizeBeforeUpdate);
        InnovationType testInnovationType = innovationTypeList.get(innovationTypeList.size() - 1);
        assertThat(testInnovationType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testInnovationType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingInnovationType() throws Exception {
        int databaseSizeBeforeUpdate = innovationTypeRepository.findAll().size();

        // Create the InnovationType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInnovationTypeMockMvc.perform(put("/api/innovation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(innovationType)))
            .andExpect(status().isCreated());

        // Validate the InnovationType in the database
        List<InnovationType> innovationTypeList = innovationTypeRepository.findAll();
        assertThat(innovationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInnovationType() throws Exception {
        // Initialize the database
        innovationTypeService.save(innovationType);

        int databaseSizeBeforeDelete = innovationTypeRepository.findAll().size();

        // Get the innovationType
        restInnovationTypeMockMvc.perform(delete("/api/innovation-types/{id}", innovationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InnovationType> innovationTypeList = innovationTypeRepository.findAll();
        assertThat(innovationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
