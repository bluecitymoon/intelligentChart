package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.NetworkTexiCompany;
import com.intelligent.chart.repository.NetworkTexiCompanyRepository;
import com.intelligent.chart.service.NetworkTexiCompanyService;

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
 * Test class for the NetworkTexiCompanyResource REST controller.
 *
 * @see NetworkTexiCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class NetworkTexiCompanyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private NetworkTexiCompanyRepository networkTexiCompanyRepository;

    @Inject
    private NetworkTexiCompanyService networkTexiCompanyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNetworkTexiCompanyMockMvc;

    private NetworkTexiCompany networkTexiCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NetworkTexiCompanyResource networkTexiCompanyResource = new NetworkTexiCompanyResource();
        ReflectionTestUtils.setField(networkTexiCompanyResource, "networkTexiCompanyService", networkTexiCompanyService);
        this.restNetworkTexiCompanyMockMvc = MockMvcBuilders.standaloneSetup(networkTexiCompanyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkTexiCompany createEntity(EntityManager em) {
        NetworkTexiCompany networkTexiCompany = new NetworkTexiCompany()
                .name(DEFAULT_NAME);
        return networkTexiCompany;
    }

    @Before
    public void initTest() {
        networkTexiCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworkTexiCompany() throws Exception {
        int databaseSizeBeforeCreate = networkTexiCompanyRepository.findAll().size();

        // Create the NetworkTexiCompany

        restNetworkTexiCompanyMockMvc.perform(post("/api/network-texi-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkTexiCompany)))
            .andExpect(status().isCreated());

        // Validate the NetworkTexiCompany in the database
        List<NetworkTexiCompany> networkTexiCompanyList = networkTexiCompanyRepository.findAll();
        assertThat(networkTexiCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkTexiCompany testNetworkTexiCompany = networkTexiCompanyList.get(networkTexiCompanyList.size() - 1);
        assertThat(testNetworkTexiCompany.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createNetworkTexiCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networkTexiCompanyRepository.findAll().size();

        // Create the NetworkTexiCompany with an existing ID
        NetworkTexiCompany existingNetworkTexiCompany = new NetworkTexiCompany();
        existingNetworkTexiCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkTexiCompanyMockMvc.perform(post("/api/network-texi-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNetworkTexiCompany)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NetworkTexiCompany> networkTexiCompanyList = networkTexiCompanyRepository.findAll();
        assertThat(networkTexiCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNetworkTexiCompanies() throws Exception {
        // Initialize the database
        networkTexiCompanyRepository.saveAndFlush(networkTexiCompany);

        // Get all the networkTexiCompanyList
        restNetworkTexiCompanyMockMvc.perform(get("/api/network-texi-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkTexiCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getNetworkTexiCompany() throws Exception {
        // Initialize the database
        networkTexiCompanyRepository.saveAndFlush(networkTexiCompany);

        // Get the networkTexiCompany
        restNetworkTexiCompanyMockMvc.perform(get("/api/network-texi-companies/{id}", networkTexiCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networkTexiCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNetworkTexiCompany() throws Exception {
        // Get the networkTexiCompany
        restNetworkTexiCompanyMockMvc.perform(get("/api/network-texi-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworkTexiCompany() throws Exception {
        // Initialize the database
        networkTexiCompanyService.save(networkTexiCompany);

        int databaseSizeBeforeUpdate = networkTexiCompanyRepository.findAll().size();

        // Update the networkTexiCompany
        NetworkTexiCompany updatedNetworkTexiCompany = networkTexiCompanyRepository.findOne(networkTexiCompany.getId());
        updatedNetworkTexiCompany
                .name(UPDATED_NAME);

        restNetworkTexiCompanyMockMvc.perform(put("/api/network-texi-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetworkTexiCompany)))
            .andExpect(status().isOk());

        // Validate the NetworkTexiCompany in the database
        List<NetworkTexiCompany> networkTexiCompanyList = networkTexiCompanyRepository.findAll();
        assertThat(networkTexiCompanyList).hasSize(databaseSizeBeforeUpdate);
        NetworkTexiCompany testNetworkTexiCompany = networkTexiCompanyList.get(networkTexiCompanyList.size() - 1);
        assertThat(testNetworkTexiCompany.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworkTexiCompany() throws Exception {
        int databaseSizeBeforeUpdate = networkTexiCompanyRepository.findAll().size();

        // Create the NetworkTexiCompany

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNetworkTexiCompanyMockMvc.perform(put("/api/network-texi-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkTexiCompany)))
            .andExpect(status().isCreated());

        // Validate the NetworkTexiCompany in the database
        List<NetworkTexiCompany> networkTexiCompanyList = networkTexiCompanyRepository.findAll();
        assertThat(networkTexiCompanyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNetworkTexiCompany() throws Exception {
        // Initialize the database
        networkTexiCompanyService.save(networkTexiCompany);

        int databaseSizeBeforeDelete = networkTexiCompanyRepository.findAll().size();

        // Get the networkTexiCompany
        restNetworkTexiCompanyMockMvc.perform(delete("/api/network-texi-companies/{id}", networkTexiCompany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NetworkTexiCompany> networkTexiCompanyList = networkTexiCompanyRepository.findAll();
        assertThat(networkTexiCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
