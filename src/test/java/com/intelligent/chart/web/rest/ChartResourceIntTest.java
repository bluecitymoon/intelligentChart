package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.Chart;
import com.intelligent.chart.repository.ChartRepository;
import com.intelligent.chart.service.ChartService;

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
 * Test class for the ChartResource REST controller.
 *
 * @see ChartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class ChartResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SOURCE_SQL = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SOURCE_SQL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_SQL = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_SQL = "BBBBBBBBBB";

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private ChartService chartService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restChartMockMvc;

    private Chart chart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChartResource chartResource = new ChartResource();
        ReflectionTestUtils.setField(chartResource, "chartService", chartService);
        this.restChartMockMvc = MockMvcBuilders.standaloneSetup(chartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chart createEntity(EntityManager em) {
        Chart chart = new Chart()
                .identifier(DEFAULT_IDENTIFIER)
                .type(DEFAULT_TYPE)
                .dataSourceSql(DEFAULT_DATA_SOURCE_SQL)
                .titleSql(DEFAULT_TITLE_SQL);
        return chart;
    }

    @Before
    public void initTest() {
        chart = createEntity(em);
    }

    @Test
    @Transactional
    public void createChart() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart

        restChartMockMvc.perform(post("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isCreated());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate + 1);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testChart.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testChart.getDataSourceSql()).isEqualTo(DEFAULT_DATA_SOURCE_SQL);
        assertThat(testChart.getTitleSql()).isEqualTo(DEFAULT_TITLE_SQL);
    }

    @Test
    @Transactional
    public void createChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart with an existing ID
        Chart existingChart = new Chart();
        existingChart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartMockMvc.perform(post("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingChart)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCharts() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList
        restChartMockMvc.perform(get("/api/charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chart.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dataSourceSql").value(hasItem(DEFAULT_DATA_SOURCE_SQL.toString())))
            .andExpect(jsonPath("$.[*].titleSql").value(hasItem(DEFAULT_TITLE_SQL.toString())));
    }

    @Test
    @Transactional
    public void getChart() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", chart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chart.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.dataSourceSql").value(DEFAULT_DATA_SOURCE_SQL.toString()))
            .andExpect(jsonPath("$.titleSql").value(DEFAULT_TITLE_SQL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChart() throws Exception {
        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Update the chart
        Chart updatedChart = chartRepository.findOne(chart.getId());
        updatedChart
                .identifier(UPDATED_IDENTIFIER)
                .type(UPDATED_TYPE)
                .dataSourceSql(UPDATED_DATA_SOURCE_SQL)
                .titleSql(UPDATED_TITLE_SQL);

        restChartMockMvc.perform(put("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChart)))
            .andExpect(status().isOk());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testChart.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testChart.getDataSourceSql()).isEqualTo(UPDATED_DATA_SOURCE_SQL);
        assertThat(testChart.getTitleSql()).isEqualTo(UPDATED_TITLE_SQL);
    }

    @Test
    @Transactional
    public void updateNonExistingChart() throws Exception {
        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Create the Chart

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChartMockMvc.perform(put("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isCreated());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeDelete = chartRepository.findAll().size();

        // Get the chart
        restChartMockMvc.perform(delete("/api/charts/{id}", chart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
