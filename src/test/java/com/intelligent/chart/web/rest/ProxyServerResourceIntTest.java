package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.repository.ProxyServerRepository;
import com.intelligent.chart.service.ProxyServerService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.intelligent.chart.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intelligent.chart.domain.enumeration.ProxyServerCategory;
/**
 * Test class for the ProxyServerResource REST controller.
 *
 * @see ProxyServerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class ProxyServerResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final ProxyServerCategory DEFAULT_CATEGORY = ProxyServerCategory.inCountryVisible;
    private static final ProxyServerCategory UPDATED_CATEGORY = ProxyServerCategory.inCountryAnonymous;

    private static final String DEFAULT_ANONYMOUS_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_ANONYMOUS_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_HTTP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_HTTP_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Float DEFAULT_RESPONSE_SECOND = 1F;
    private static final Float UPDATED_RESPONSE_SECOND = 2F;

    private static final ZonedDateTime DEFAULT_LAST_VALIDATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_VALIDATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_SUCCESS_COUNT = 1;
    private static final Integer UPDATED_TOTAL_SUCCESS_COUNT = 2;

    private static final Integer DEFAULT_TOTAL_FAIL_COUNT = 1;
    private static final Integer UPDATED_TOTAL_FAIL_COUNT = 2;

    private static final ZonedDateTime DEFAULT_LAST_SUCCESS_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_SUCCESS_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_FAIL_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_FAIL_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private ProxyServerRepository proxyServerRepository;

    @Inject
    private ProxyServerService proxyServerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProxyServerMockMvc;

    private ProxyServer proxyServer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProxyServerResource proxyServerResource = new ProxyServerResource();
        ReflectionTestUtils.setField(proxyServerResource, "proxyServerService", proxyServerService);
        this.restProxyServerMockMvc = MockMvcBuilders.standaloneSetup(proxyServerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProxyServer createEntity(EntityManager em) {
        ProxyServer proxyServer = new ProxyServer()
                .address(DEFAULT_ADDRESS)
                .port(DEFAULT_PORT)
                .category(DEFAULT_CATEGORY)
                .anonymousLevel(DEFAULT_ANONYMOUS_LEVEL)
                .httpType(DEFAULT_HTTP_TYPE)
                .location(DEFAULT_LOCATION)
                .responseSecond(DEFAULT_RESPONSE_SECOND)
                .lastValidationDate(DEFAULT_LAST_VALIDATION_DATE)
                .totalSuccessCount(DEFAULT_TOTAL_SUCCESS_COUNT)
                .totalFailCount(DEFAULT_TOTAL_FAIL_COUNT)
                .lastSuccessDate(DEFAULT_LAST_SUCCESS_DATE)
                .lastFailDate(DEFAULT_LAST_FAIL_DATE)
                .createDate(DEFAULT_CREATE_DATE);
        return proxyServer;
    }

    @Before
    public void initTest() {
        proxyServer = createEntity(em);
    }

    @Test
    @Transactional
    public void createProxyServer() throws Exception {
        int databaseSizeBeforeCreate = proxyServerRepository.findAll().size();

        // Create the ProxyServer

        restProxyServerMockMvc.perform(post("/api/proxy-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proxyServer)))
            .andExpect(status().isCreated());

        // Validate the ProxyServer in the database
        List<ProxyServer> proxyServerList = proxyServerRepository.findAll();
        assertThat(proxyServerList).hasSize(databaseSizeBeforeCreate + 1);
        ProxyServer testProxyServer = proxyServerList.get(proxyServerList.size() - 1);
        assertThat(testProxyServer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testProxyServer.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testProxyServer.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProxyServer.getAnonymousLevel()).isEqualTo(DEFAULT_ANONYMOUS_LEVEL);
        assertThat(testProxyServer.getHttpType()).isEqualTo(DEFAULT_HTTP_TYPE);
        assertThat(testProxyServer.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProxyServer.getResponseSecond()).isEqualTo(DEFAULT_RESPONSE_SECOND);
        assertThat(testProxyServer.getLastValidationDate()).isEqualTo(DEFAULT_LAST_VALIDATION_DATE);
        assertThat(testProxyServer.getTotalSuccessCount()).isEqualTo(DEFAULT_TOTAL_SUCCESS_COUNT);
        assertThat(testProxyServer.getTotalFailCount()).isEqualTo(DEFAULT_TOTAL_FAIL_COUNT);
        assertThat(testProxyServer.getLastSuccessDate()).isEqualTo(DEFAULT_LAST_SUCCESS_DATE);
        assertThat(testProxyServer.getLastFailDate()).isEqualTo(DEFAULT_LAST_FAIL_DATE);
        assertThat(testProxyServer.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createProxyServerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proxyServerRepository.findAll().size();

        // Create the ProxyServer with an existing ID
        ProxyServer existingProxyServer = new ProxyServer();
        existingProxyServer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProxyServerMockMvc.perform(post("/api/proxy-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProxyServer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProxyServer> proxyServerList = proxyServerRepository.findAll();
        assertThat(proxyServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProxyServers() throws Exception {
        // Initialize the database
        proxyServerRepository.saveAndFlush(proxyServer);

        // Get all the proxyServerList
        restProxyServerMockMvc.perform(get("/api/proxy-servers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proxyServer.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].anonymousLevel").value(hasItem(DEFAULT_ANONYMOUS_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].httpType").value(hasItem(DEFAULT_HTTP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].responseSecond").value(hasItem(DEFAULT_RESPONSE_SECOND.doubleValue())))
            .andExpect(jsonPath("$.[*].lastValidationDate").value(hasItem(sameInstant(DEFAULT_LAST_VALIDATION_DATE))))
            .andExpect(jsonPath("$.[*].totalSuccessCount").value(hasItem(DEFAULT_TOTAL_SUCCESS_COUNT)))
            .andExpect(jsonPath("$.[*].totalFailCount").value(hasItem(DEFAULT_TOTAL_FAIL_COUNT)))
            .andExpect(jsonPath("$.[*].lastSuccessDate").value(hasItem(sameInstant(DEFAULT_LAST_SUCCESS_DATE))))
            .andExpect(jsonPath("$.[*].lastFailDate").value(hasItem(sameInstant(DEFAULT_LAST_FAIL_DATE))))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getProxyServer() throws Exception {
        // Initialize the database
        proxyServerRepository.saveAndFlush(proxyServer);

        // Get the proxyServer
        restProxyServerMockMvc.perform(get("/api/proxy-servers/{id}", proxyServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proxyServer.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.anonymousLevel").value(DEFAULT_ANONYMOUS_LEVEL.toString()))
            .andExpect(jsonPath("$.httpType").value(DEFAULT_HTTP_TYPE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.responseSecond").value(DEFAULT_RESPONSE_SECOND.doubleValue()))
            .andExpect(jsonPath("$.lastValidationDate").value(sameInstant(DEFAULT_LAST_VALIDATION_DATE)))
            .andExpect(jsonPath("$.totalSuccessCount").value(DEFAULT_TOTAL_SUCCESS_COUNT))
            .andExpect(jsonPath("$.totalFailCount").value(DEFAULT_TOTAL_FAIL_COUNT))
            .andExpect(jsonPath("$.lastSuccessDate").value(sameInstant(DEFAULT_LAST_SUCCESS_DATE)))
            .andExpect(jsonPath("$.lastFailDate").value(sameInstant(DEFAULT_LAST_FAIL_DATE)))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingProxyServer() throws Exception {
        // Get the proxyServer
        restProxyServerMockMvc.perform(get("/api/proxy-servers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProxyServer() throws Exception {
        // Initialize the database
        proxyServerService.save(proxyServer);

        int databaseSizeBeforeUpdate = proxyServerRepository.findAll().size();

        // Update the proxyServer
        ProxyServer updatedProxyServer = proxyServerRepository.findOne(proxyServer.getId());
        updatedProxyServer
                .address(UPDATED_ADDRESS)
                .port(UPDATED_PORT)
                .category(UPDATED_CATEGORY)
                .anonymousLevel(UPDATED_ANONYMOUS_LEVEL)
                .httpType(UPDATED_HTTP_TYPE)
                .location(UPDATED_LOCATION)
                .responseSecond(UPDATED_RESPONSE_SECOND)
                .lastValidationDate(UPDATED_LAST_VALIDATION_DATE)
                .totalSuccessCount(UPDATED_TOTAL_SUCCESS_COUNT)
                .totalFailCount(UPDATED_TOTAL_FAIL_COUNT)
                .lastSuccessDate(UPDATED_LAST_SUCCESS_DATE)
                .lastFailDate(UPDATED_LAST_FAIL_DATE)
                .createDate(UPDATED_CREATE_DATE);

        restProxyServerMockMvc.perform(put("/api/proxy-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProxyServer)))
            .andExpect(status().isOk());

        // Validate the ProxyServer in the database
        List<ProxyServer> proxyServerList = proxyServerRepository.findAll();
        assertThat(proxyServerList).hasSize(databaseSizeBeforeUpdate);
        ProxyServer testProxyServer = proxyServerList.get(proxyServerList.size() - 1);
        assertThat(testProxyServer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProxyServer.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testProxyServer.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProxyServer.getAnonymousLevel()).isEqualTo(UPDATED_ANONYMOUS_LEVEL);
        assertThat(testProxyServer.getHttpType()).isEqualTo(UPDATED_HTTP_TYPE);
        assertThat(testProxyServer.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProxyServer.getResponseSecond()).isEqualTo(UPDATED_RESPONSE_SECOND);
        assertThat(testProxyServer.getLastValidationDate()).isEqualTo(UPDATED_LAST_VALIDATION_DATE);
        assertThat(testProxyServer.getTotalSuccessCount()).isEqualTo(UPDATED_TOTAL_SUCCESS_COUNT);
        assertThat(testProxyServer.getTotalFailCount()).isEqualTo(UPDATED_TOTAL_FAIL_COUNT);
        assertThat(testProxyServer.getLastSuccessDate()).isEqualTo(UPDATED_LAST_SUCCESS_DATE);
        assertThat(testProxyServer.getLastFailDate()).isEqualTo(UPDATED_LAST_FAIL_DATE);
        assertThat(testProxyServer.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProxyServer() throws Exception {
        int databaseSizeBeforeUpdate = proxyServerRepository.findAll().size();

        // Create the ProxyServer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProxyServerMockMvc.perform(put("/api/proxy-servers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proxyServer)))
            .andExpect(status().isCreated());

        // Validate the ProxyServer in the database
        List<ProxyServer> proxyServerList = proxyServerRepository.findAll();
        assertThat(proxyServerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProxyServer() throws Exception {
        // Initialize the database
        proxyServerService.save(proxyServer);

        int databaseSizeBeforeDelete = proxyServerRepository.findAll().size();

        // Get the proxyServer
        restProxyServerMockMvc.perform(delete("/api/proxy-servers/{id}", proxyServer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProxyServer> proxyServerList = proxyServerRepository.findAll();
        assertThat(proxyServerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
