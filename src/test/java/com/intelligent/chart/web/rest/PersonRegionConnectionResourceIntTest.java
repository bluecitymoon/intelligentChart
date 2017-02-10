package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonRegionConnection;
import com.intelligent.chart.repository.PersonRegionConnectionRepository;
import com.intelligent.chart.service.PersonRegionConnectionService;

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
 * Test class for the PersonRegionConnectionResource REST controller.
 *
 * @see PersonRegionConnectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonRegionConnectionResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private PersonRegionConnectionRepository personRegionConnectionRepository;

    @Inject
    private PersonRegionConnectionService personRegionConnectionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonRegionConnectionMockMvc;

    private PersonRegionConnection personRegionConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonRegionConnectionResource personRegionConnectionResource = new PersonRegionConnectionResource();
        ReflectionTestUtils.setField(personRegionConnectionResource, "personRegionConnectionService", personRegionConnectionService);
        this.restPersonRegionConnectionMockMvc = MockMvcBuilders.standaloneSetup(personRegionConnectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonRegionConnection createEntity(EntityManager em) {
        PersonRegionConnection personRegionConnection = new PersonRegionConnection()
                .count(DEFAULT_COUNT);
        return personRegionConnection;
    }

    @Before
    public void initTest() {
        personRegionConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonRegionConnection() throws Exception {
        int databaseSizeBeforeCreate = personRegionConnectionRepository.findAll().size();

        // Create the PersonRegionConnection

        restPersonRegionConnectionMockMvc.perform(post("/api/person-region-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRegionConnection)))
            .andExpect(status().isCreated());

        // Validate the PersonRegionConnection in the database
        List<PersonRegionConnection> personRegionConnectionList = personRegionConnectionRepository.findAll();
        assertThat(personRegionConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        PersonRegionConnection testPersonRegionConnection = personRegionConnectionList.get(personRegionConnectionList.size() - 1);
        assertThat(testPersonRegionConnection.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonRegionConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRegionConnectionRepository.findAll().size();

        // Create the PersonRegionConnection with an existing ID
        PersonRegionConnection existingPersonRegionConnection = new PersonRegionConnection();
        existingPersonRegionConnection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonRegionConnectionMockMvc.perform(post("/api/person-region-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonRegionConnection)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonRegionConnection> personRegionConnectionList = personRegionConnectionRepository.findAll();
        assertThat(personRegionConnectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonRegionConnections() throws Exception {
        // Initialize the database
        personRegionConnectionRepository.saveAndFlush(personRegionConnection);

        // Get all the personRegionConnectionList
        restPersonRegionConnectionMockMvc.perform(get("/api/person-region-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personRegionConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonRegionConnection() throws Exception {
        // Initialize the database
        personRegionConnectionRepository.saveAndFlush(personRegionConnection);

        // Get the personRegionConnection
        restPersonRegionConnectionMockMvc.perform(get("/api/person-region-connections/{id}", personRegionConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personRegionConnection.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonRegionConnection() throws Exception {
        // Get the personRegionConnection
        restPersonRegionConnectionMockMvc.perform(get("/api/person-region-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonRegionConnection() throws Exception {
        // Initialize the database
        personRegionConnectionService.save(personRegionConnection);

        int databaseSizeBeforeUpdate = personRegionConnectionRepository.findAll().size();

        // Update the personRegionConnection
        PersonRegionConnection updatedPersonRegionConnection = personRegionConnectionRepository.findOne(personRegionConnection.getId());
        updatedPersonRegionConnection
                .count(UPDATED_COUNT);

        restPersonRegionConnectionMockMvc.perform(put("/api/person-region-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonRegionConnection)))
            .andExpect(status().isOk());

        // Validate the PersonRegionConnection in the database
        List<PersonRegionConnection> personRegionConnectionList = personRegionConnectionRepository.findAll();
        assertThat(personRegionConnectionList).hasSize(databaseSizeBeforeUpdate);
        PersonRegionConnection testPersonRegionConnection = personRegionConnectionList.get(personRegionConnectionList.size() - 1);
        assertThat(testPersonRegionConnection.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonRegionConnection() throws Exception {
        int databaseSizeBeforeUpdate = personRegionConnectionRepository.findAll().size();

        // Create the PersonRegionConnection

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonRegionConnectionMockMvc.perform(put("/api/person-region-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personRegionConnection)))
            .andExpect(status().isCreated());

        // Validate the PersonRegionConnection in the database
        List<PersonRegionConnection> personRegionConnectionList = personRegionConnectionRepository.findAll();
        assertThat(personRegionConnectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonRegionConnection() throws Exception {
        // Initialize the database
        personRegionConnectionService.save(personRegionConnection);

        int databaseSizeBeforeDelete = personRegionConnectionRepository.findAll().size();

        // Get the personRegionConnection
        restPersonRegionConnectionMockMvc.perform(delete("/api/person-region-connections/{id}", personRegionConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonRegionConnection> personRegionConnectionList = personRegionConnectionRepository.findAll();
        assertThat(personRegionConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
