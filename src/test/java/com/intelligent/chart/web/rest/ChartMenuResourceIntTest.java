package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.ChartMenu;
import com.intelligent.chart.repository.ChartMenuRepository;
import com.intelligent.chart.service.ChartMenuService;

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
 * Test class for the ChartMenuResource REST controller.
 *
 * @see ChartMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class ChartMenuResourceIntTest {

    @Inject
    private ChartMenuRepository chartMenuRepository;

    @Inject
    private ChartMenuService chartMenuService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restChartMenuMockMvc;

    private ChartMenu chartMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChartMenuResource chartMenuResource = new ChartMenuResource();
        ReflectionTestUtils.setField(chartMenuResource, "chartMenuService", chartMenuService);
        this.restChartMenuMockMvc = MockMvcBuilders.standaloneSetup(chartMenuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChartMenu createEntity(EntityManager em) {
        ChartMenu chartMenu = new ChartMenu();
        return chartMenu;
    }

    @Before
    public void initTest() {
        chartMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createChartMenu() throws Exception {
        int databaseSizeBeforeCreate = chartMenuRepository.findAll().size();

        // Create the ChartMenu

        restChartMenuMockMvc.perform(post("/api/chart-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartMenu)))
            .andExpect(status().isCreated());

        // Validate the ChartMenu in the database
        List<ChartMenu> chartMenuList = chartMenuRepository.findAll();
        assertThat(chartMenuList).hasSize(databaseSizeBeforeCreate + 1);
        ChartMenu testChartMenu = chartMenuList.get(chartMenuList.size() - 1);
    }

    @Test
    @Transactional
    public void createChartMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chartMenuRepository.findAll().size();

        // Create the ChartMenu with an existing ID
        ChartMenu existingChartMenu = new ChartMenu();
        existingChartMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartMenuMockMvc.perform(post("/api/chart-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingChartMenu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ChartMenu> chartMenuList = chartMenuRepository.findAll();
        assertThat(chartMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChartMenus() throws Exception {
        // Initialize the database
        chartMenuRepository.saveAndFlush(chartMenu);

        // Get all the chartMenuList
        restChartMenuMockMvc.perform(get("/api/chart-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chartMenu.getId().intValue())));
    }

    @Test
    @Transactional
    public void getChartMenu() throws Exception {
        // Initialize the database
        chartMenuRepository.saveAndFlush(chartMenu);

        // Get the chartMenu
        restChartMenuMockMvc.perform(get("/api/chart-menus/{id}", chartMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chartMenu.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChartMenu() throws Exception {
        // Get the chartMenu
        restChartMenuMockMvc.perform(get("/api/chart-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChartMenu() throws Exception {
        // Initialize the database
        chartMenuService.save(chartMenu);

        int databaseSizeBeforeUpdate = chartMenuRepository.findAll().size();

        // Update the chartMenu
        ChartMenu updatedChartMenu = chartMenuRepository.findOne(chartMenu.getId());

        restChartMenuMockMvc.perform(put("/api/chart-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChartMenu)))
            .andExpect(status().isOk());

        // Validate the ChartMenu in the database
        List<ChartMenu> chartMenuList = chartMenuRepository.findAll();
        assertThat(chartMenuList).hasSize(databaseSizeBeforeUpdate);
        ChartMenu testChartMenu = chartMenuList.get(chartMenuList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingChartMenu() throws Exception {
        int databaseSizeBeforeUpdate = chartMenuRepository.findAll().size();

        // Create the ChartMenu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChartMenuMockMvc.perform(put("/api/chart-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartMenu)))
            .andExpect(status().isCreated());

        // Validate the ChartMenu in the database
        List<ChartMenu> chartMenuList = chartMenuRepository.findAll();
        assertThat(chartMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChartMenu() throws Exception {
        // Initialize the database
        chartMenuService.save(chartMenu);

        int databaseSizeBeforeDelete = chartMenuRepository.findAll().size();

        // Get the chartMenu
        restChartMenuMockMvc.perform(delete("/api/chart-menus/{id}", chartMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChartMenu> chartMenuList = chartMenuRepository.findAll();
        assertThat(chartMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
