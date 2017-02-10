package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PrizeType;
import com.intelligent.chart.repository.PrizeTypeRepository;
import com.intelligent.chart.service.PrizeTypeService;

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
 * Test class for the PrizeTypeResource REST controller.
 *
 * @see PrizeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PrizeTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PrizeTypeRepository prizeTypeRepository;

    @Inject
    private PrizeTypeService prizeTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPrizeTypeMockMvc;

    private PrizeType prizeType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrizeTypeResource prizeTypeResource = new PrizeTypeResource();
        ReflectionTestUtils.setField(prizeTypeResource, "prizeTypeService", prizeTypeService);
        this.restPrizeTypeMockMvc = MockMvcBuilders.standaloneSetup(prizeTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizeType createEntity(EntityManager em) {
        PrizeType prizeType = new PrizeType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return prizeType;
    }

    @Before
    public void initTest() {
        prizeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrizeType() throws Exception {
        int databaseSizeBeforeCreate = prizeTypeRepository.findAll().size();

        // Create the PrizeType

        restPrizeTypeMockMvc.perform(post("/api/prize-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeType)))
            .andExpect(status().isCreated());

        // Validate the PrizeType in the database
        List<PrizeType> prizeTypeList = prizeTypeRepository.findAll();
        assertThat(prizeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PrizeType testPrizeType = prizeTypeList.get(prizeTypeList.size() - 1);
        assertThat(testPrizeType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPrizeType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPrizeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prizeTypeRepository.findAll().size();

        // Create the PrizeType with an existing ID
        PrizeType existingPrizeType = new PrizeType();
        existingPrizeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeTypeMockMvc.perform(post("/api/prize-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPrizeType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PrizeType> prizeTypeList = prizeTypeRepository.findAll();
        assertThat(prizeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrizeTypes() throws Exception {
        // Initialize the database
        prizeTypeRepository.saveAndFlush(prizeType);

        // Get all the prizeTypeList
        restPrizeTypeMockMvc.perform(get("/api/prize-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPrizeType() throws Exception {
        // Initialize the database
        prizeTypeRepository.saveAndFlush(prizeType);

        // Get the prizeType
        restPrizeTypeMockMvc.perform(get("/api/prize-types/{id}", prizeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prizeType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrizeType() throws Exception {
        // Get the prizeType
        restPrizeTypeMockMvc.perform(get("/api/prize-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrizeType() throws Exception {
        // Initialize the database
        prizeTypeService.save(prizeType);

        int databaseSizeBeforeUpdate = prizeTypeRepository.findAll().size();

        // Update the prizeType
        PrizeType updatedPrizeType = prizeTypeRepository.findOne(prizeType.getId());
        updatedPrizeType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restPrizeTypeMockMvc.perform(put("/api/prize-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrizeType)))
            .andExpect(status().isOk());

        // Validate the PrizeType in the database
        List<PrizeType> prizeTypeList = prizeTypeRepository.findAll();
        assertThat(prizeTypeList).hasSize(databaseSizeBeforeUpdate);
        PrizeType testPrizeType = prizeTypeList.get(prizeTypeList.size() - 1);
        assertThat(testPrizeType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPrizeType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrizeType() throws Exception {
        int databaseSizeBeforeUpdate = prizeTypeRepository.findAll().size();

        // Create the PrizeType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrizeTypeMockMvc.perform(put("/api/prize-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeType)))
            .andExpect(status().isCreated());

        // Validate the PrizeType in the database
        List<PrizeType> prizeTypeList = prizeTypeRepository.findAll();
        assertThat(prizeTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrizeType() throws Exception {
        // Initialize the database
        prizeTypeService.save(prizeType);

        int databaseSizeBeforeDelete = prizeTypeRepository.findAll().size();

        // Get the prizeType
        restPrizeTypeMockMvc.perform(delete("/api/prize-types/{id}", prizeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrizeType> prizeTypeList = prizeTypeRepository.findAll();
        assertThat(prizeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
