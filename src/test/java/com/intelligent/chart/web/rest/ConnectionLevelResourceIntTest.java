package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.ConnectionLevel;
import com.intelligent.chart.repository.ConnectionLevelRepository;
import com.intelligent.chart.service.ConnectionLevelService;

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
 * Test class for the ConnectionLevelResource REST controller.
 *
 * @see ConnectionLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class ConnectionLevelResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private ConnectionLevelRepository connectionLevelRepository;

    @Inject
    private ConnectionLevelService connectionLevelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConnectionLevelMockMvc;

    private ConnectionLevel connectionLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConnectionLevelResource connectionLevelResource = new ConnectionLevelResource();
        ReflectionTestUtils.setField(connectionLevelResource, "connectionLevelService", connectionLevelService);
        this.restConnectionLevelMockMvc = MockMvcBuilders.standaloneSetup(connectionLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectionLevel createEntity(EntityManager em) {
        ConnectionLevel connectionLevel = new ConnectionLevel()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return connectionLevel;
    }

    @Before
    public void initTest() {
        connectionLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectionLevel() throws Exception {
        int databaseSizeBeforeCreate = connectionLevelRepository.findAll().size();

        // Create the ConnectionLevel

        restConnectionLevelMockMvc.perform(post("/api/connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionLevel)))
            .andExpect(status().isCreated());

        // Validate the ConnectionLevel in the database
        List<ConnectionLevel> connectionLevelList = connectionLevelRepository.findAll();
        assertThat(connectionLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectionLevel testConnectionLevel = connectionLevelList.get(connectionLevelList.size() - 1);
        assertThat(testConnectionLevel.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testConnectionLevel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createConnectionLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectionLevelRepository.findAll().size();

        // Create the ConnectionLevel with an existing ID
        ConnectionLevel existingConnectionLevel = new ConnectionLevel();
        existingConnectionLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionLevelMockMvc.perform(post("/api/connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingConnectionLevel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ConnectionLevel> connectionLevelList = connectionLevelRepository.findAll();
        assertThat(connectionLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConnectionLevels() throws Exception {
        // Initialize the database
        connectionLevelRepository.saveAndFlush(connectionLevel);

        // Get all the connectionLevelList
        restConnectionLevelMockMvc.perform(get("/api/connection-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectionLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getConnectionLevel() throws Exception {
        // Initialize the database
        connectionLevelRepository.saveAndFlush(connectionLevel);

        // Get the connectionLevel
        restConnectionLevelMockMvc.perform(get("/api/connection-levels/{id}", connectionLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(connectionLevel.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConnectionLevel() throws Exception {
        // Get the connectionLevel
        restConnectionLevelMockMvc.perform(get("/api/connection-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectionLevel() throws Exception {
        // Initialize the database
        connectionLevelService.save(connectionLevel);

        int databaseSizeBeforeUpdate = connectionLevelRepository.findAll().size();

        // Update the connectionLevel
        ConnectionLevel updatedConnectionLevel = connectionLevelRepository.findOne(connectionLevel.getId());
        updatedConnectionLevel
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restConnectionLevelMockMvc.perform(put("/api/connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnectionLevel)))
            .andExpect(status().isOk());

        // Validate the ConnectionLevel in the database
        List<ConnectionLevel> connectionLevelList = connectionLevelRepository.findAll();
        assertThat(connectionLevelList).hasSize(databaseSizeBeforeUpdate);
        ConnectionLevel testConnectionLevel = connectionLevelList.get(connectionLevelList.size() - 1);
        assertThat(testConnectionLevel.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testConnectionLevel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectionLevel() throws Exception {
        int databaseSizeBeforeUpdate = connectionLevelRepository.findAll().size();

        // Create the ConnectionLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConnectionLevelMockMvc.perform(put("/api/connection-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionLevel)))
            .andExpect(status().isCreated());

        // Validate the ConnectionLevel in the database
        List<ConnectionLevel> connectionLevelList = connectionLevelRepository.findAll();
        assertThat(connectionLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConnectionLevel() throws Exception {
        // Initialize the database
        connectionLevelService.save(connectionLevel);

        int databaseSizeBeforeDelete = connectionLevelRepository.findAll().size();

        // Get the connectionLevel
        restConnectionLevelMockMvc.perform(delete("/api/connection-levels/{id}", connectionLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConnectionLevel> connectionLevelList = connectionLevelRepository.findAll();
        assertThat(connectionLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
