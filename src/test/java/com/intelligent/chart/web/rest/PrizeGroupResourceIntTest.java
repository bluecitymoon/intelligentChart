package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PrizeGroup;
import com.intelligent.chart.repository.PrizeGroupRepository;
import com.intelligent.chart.service.PrizeGroupService;

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
 * Test class for the PrizeGroupResource REST controller.
 *
 * @see PrizeGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PrizeGroupResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PrizeGroupRepository prizeGroupRepository;

    @Inject
    private PrizeGroupService prizeGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPrizeGroupMockMvc;

    private PrizeGroup prizeGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrizeGroupResource prizeGroupResource = new PrizeGroupResource();
        ReflectionTestUtils.setField(prizeGroupResource, "prizeGroupService", prizeGroupService);
        this.restPrizeGroupMockMvc = MockMvcBuilders.standaloneSetup(prizeGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrizeGroup createEntity(EntityManager em) {
        PrizeGroup prizeGroup = new PrizeGroup()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return prizeGroup;
    }

    @Before
    public void initTest() {
        prizeGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrizeGroup() throws Exception {
        int databaseSizeBeforeCreate = prizeGroupRepository.findAll().size();

        // Create the PrizeGroup

        restPrizeGroupMockMvc.perform(post("/api/prize-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeGroup)))
            .andExpect(status().isCreated());

        // Validate the PrizeGroup in the database
        List<PrizeGroup> prizeGroupList = prizeGroupRepository.findAll();
        assertThat(prizeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PrizeGroup testPrizeGroup = prizeGroupList.get(prizeGroupList.size() - 1);
        assertThat(testPrizeGroup.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPrizeGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPrizeGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prizeGroupRepository.findAll().size();

        // Create the PrizeGroup with an existing ID
        PrizeGroup existingPrizeGroup = new PrizeGroup();
        existingPrizeGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizeGroupMockMvc.perform(post("/api/prize-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPrizeGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PrizeGroup> prizeGroupList = prizeGroupRepository.findAll();
        assertThat(prizeGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrizeGroups() throws Exception {
        // Initialize the database
        prizeGroupRepository.saveAndFlush(prizeGroup);

        // Get all the prizeGroupList
        restPrizeGroupMockMvc.perform(get("/api/prize-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizeGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPrizeGroup() throws Exception {
        // Initialize the database
        prizeGroupRepository.saveAndFlush(prizeGroup);

        // Get the prizeGroup
        restPrizeGroupMockMvc.perform(get("/api/prize-groups/{id}", prizeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prizeGroup.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrizeGroup() throws Exception {
        // Get the prizeGroup
        restPrizeGroupMockMvc.perform(get("/api/prize-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrizeGroup() throws Exception {
        // Initialize the database
        prizeGroupService.save(prizeGroup);

        int databaseSizeBeforeUpdate = prizeGroupRepository.findAll().size();

        // Update the prizeGroup
        PrizeGroup updatedPrizeGroup = prizeGroupRepository.findOne(prizeGroup.getId());
        updatedPrizeGroup
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restPrizeGroupMockMvc.perform(put("/api/prize-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrizeGroup)))
            .andExpect(status().isOk());

        // Validate the PrizeGroup in the database
        List<PrizeGroup> prizeGroupList = prizeGroupRepository.findAll();
        assertThat(prizeGroupList).hasSize(databaseSizeBeforeUpdate);
        PrizeGroup testPrizeGroup = prizeGroupList.get(prizeGroupList.size() - 1);
        assertThat(testPrizeGroup.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPrizeGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrizeGroup() throws Exception {
        int databaseSizeBeforeUpdate = prizeGroupRepository.findAll().size();

        // Create the PrizeGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrizeGroupMockMvc.perform(put("/api/prize-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prizeGroup)))
            .andExpect(status().isCreated());

        // Validate the PrizeGroup in the database
        List<PrizeGroup> prizeGroupList = prizeGroupRepository.findAll();
        assertThat(prizeGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrizeGroup() throws Exception {
        // Initialize the database
        prizeGroupService.save(prizeGroup);

        int databaseSizeBeforeDelete = prizeGroupRepository.findAll().size();

        // Get the prizeGroup
        restPrizeGroupMockMvc.perform(delete("/api/prize-groups/{id}", prizeGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrizeGroup> prizeGroupList = prizeGroupRepository.findAll();
        assertThat(prizeGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
