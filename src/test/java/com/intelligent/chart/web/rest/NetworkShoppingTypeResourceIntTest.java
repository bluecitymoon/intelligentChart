package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.NetworkShoppingType;
import com.intelligent.chart.repository.NetworkShoppingTypeRepository;
import com.intelligent.chart.service.NetworkShoppingTypeService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NetworkShoppingTypeResource REST controller.
 *
 * @see NetworkShoppingTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class NetworkShoppingTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private NetworkShoppingTypeRepository networkShoppingTypeRepository;

    @Inject
    private NetworkShoppingTypeService networkShoppingTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNetworkShoppingTypeMockMvc;

    private NetworkShoppingType networkShoppingType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NetworkShoppingTypeResource networkShoppingTypeResource = new NetworkShoppingTypeResource();
        ReflectionTestUtils.setField(networkShoppingTypeResource, "networkShoppingTypeService", networkShoppingTypeService);
        this.restNetworkShoppingTypeMockMvc = MockMvcBuilders.standaloneSetup(networkShoppingTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkShoppingType createEntity(EntityManager em) {
        NetworkShoppingType networkShoppingType = new NetworkShoppingType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME)
                .createDate(DEFAULT_CREATE_DATE);
        return networkShoppingType;
    }

    @Before
    public void initTest() {
        networkShoppingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworkShoppingType() throws Exception {
        int databaseSizeBeforeCreate = networkShoppingTypeRepository.findAll().size();

        // Create the NetworkShoppingType

        restNetworkShoppingTypeMockMvc.perform(post("/api/network-shopping-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkShoppingType)))
            .andExpect(status().isCreated());

        // Validate the NetworkShoppingType in the database
        List<NetworkShoppingType> networkShoppingTypeList = networkShoppingTypeRepository.findAll();
        assertThat(networkShoppingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkShoppingType testNetworkShoppingType = networkShoppingTypeList.get(networkShoppingTypeList.size() - 1);
        assertThat(testNetworkShoppingType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testNetworkShoppingType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNetworkShoppingType.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createNetworkShoppingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networkShoppingTypeRepository.findAll().size();

        // Create the NetworkShoppingType with an existing ID
        NetworkShoppingType existingNetworkShoppingType = new NetworkShoppingType();
        existingNetworkShoppingType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkShoppingTypeMockMvc.perform(post("/api/network-shopping-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNetworkShoppingType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NetworkShoppingType> networkShoppingTypeList = networkShoppingTypeRepository.findAll();
        assertThat(networkShoppingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNetworkShoppingTypes() throws Exception {
        // Initialize the database
        networkShoppingTypeRepository.saveAndFlush(networkShoppingType);

        // Get all the networkShoppingTypeList
        restNetworkShoppingTypeMockMvc.perform(get("/api/network-shopping-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkShoppingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getNetworkShoppingType() throws Exception {
        // Initialize the database
        networkShoppingTypeRepository.saveAndFlush(networkShoppingType);

        // Get the networkShoppingType
        restNetworkShoppingTypeMockMvc.perform(get("/api/network-shopping-types/{id}", networkShoppingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networkShoppingType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNetworkShoppingType() throws Exception {
        // Get the networkShoppingType
        restNetworkShoppingTypeMockMvc.perform(get("/api/network-shopping-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworkShoppingType() throws Exception {
        // Initialize the database
        networkShoppingTypeService.save(networkShoppingType);

        int databaseSizeBeforeUpdate = networkShoppingTypeRepository.findAll().size();

        // Update the networkShoppingType
        NetworkShoppingType updatedNetworkShoppingType = networkShoppingTypeRepository.findOne(networkShoppingType.getId());
        updatedNetworkShoppingType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME)
                .createDate(UPDATED_CREATE_DATE);

        restNetworkShoppingTypeMockMvc.perform(put("/api/network-shopping-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetworkShoppingType)))
            .andExpect(status().isOk());

        // Validate the NetworkShoppingType in the database
        List<NetworkShoppingType> networkShoppingTypeList = networkShoppingTypeRepository.findAll();
        assertThat(networkShoppingTypeList).hasSize(databaseSizeBeforeUpdate);
        NetworkShoppingType testNetworkShoppingType = networkShoppingTypeList.get(networkShoppingTypeList.size() - 1);
        assertThat(testNetworkShoppingType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testNetworkShoppingType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNetworkShoppingType.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworkShoppingType() throws Exception {
        int databaseSizeBeforeUpdate = networkShoppingTypeRepository.findAll().size();

        // Create the NetworkShoppingType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNetworkShoppingTypeMockMvc.perform(put("/api/network-shopping-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkShoppingType)))
            .andExpect(status().isCreated());

        // Validate the NetworkShoppingType in the database
        List<NetworkShoppingType> networkShoppingTypeList = networkShoppingTypeRepository.findAll();
        assertThat(networkShoppingTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNetworkShoppingType() throws Exception {
        // Initialize the database
        networkShoppingTypeService.save(networkShoppingType);

        int databaseSizeBeforeDelete = networkShoppingTypeRepository.findAll().size();

        // Get the networkShoppingType
        restNetworkShoppingTypeMockMvc.perform(delete("/api/network-shopping-types/{id}", networkShoppingType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NetworkShoppingType> networkShoppingTypeList = networkShoppingTypeRepository.findAll();
        assertThat(networkShoppingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
