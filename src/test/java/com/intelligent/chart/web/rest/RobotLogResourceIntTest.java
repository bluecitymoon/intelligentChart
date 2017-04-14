package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.RobotLog;
import com.intelligent.chart.repository.RobotLogRepository;
import com.intelligent.chart.service.RobotLogService;

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

import com.intelligent.chart.domain.enumeration.RobotLogLevel;
/**
 * Test class for the RobotLogResource REST controller.
 *
 * @see RobotLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class RobotLogResourceIntTest {

    private static final RobotLogLevel DEFAULT_LEVEL = RobotLogLevel.error;
    private static final RobotLogLevel UPDATED_LEVEL = RobotLogLevel.success;

    private static final String DEFAULT_LOG_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_LOG_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private RobotLogRepository robotLogRepository;

    @Inject
    private RobotLogService robotLogService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRobotLogMockMvc;

    private RobotLog robotLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RobotLogResource robotLogResource = new RobotLogResource();
        ReflectionTestUtils.setField(robotLogResource, "robotLogService", robotLogService);
        this.restRobotLogMockMvc = MockMvcBuilders.standaloneSetup(robotLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RobotLog createEntity(EntityManager em) {
        RobotLog robotLog = new RobotLog()
                .level(DEFAULT_LEVEL)
                .logContent(DEFAULT_LOG_CONTENT)
                .createDate(DEFAULT_CREATE_DATE);
        return robotLog;
    }

    @Before
    public void initTest() {
        robotLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createRobotLog() throws Exception {
        int databaseSizeBeforeCreate = robotLogRepository.findAll().size();

        // Create the RobotLog

        restRobotLogMockMvc.perform(post("/api/robot-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotLog)))
            .andExpect(status().isCreated());

        // Validate the RobotLog in the database
        List<RobotLog> robotLogList = robotLogRepository.findAll();
        assertThat(robotLogList).hasSize(databaseSizeBeforeCreate + 1);
        RobotLog testRobotLog = robotLogList.get(robotLogList.size() - 1);
        assertThat(testRobotLog.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testRobotLog.getLogContent()).isEqualTo(DEFAULT_LOG_CONTENT);
        assertThat(testRobotLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createRobotLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = robotLogRepository.findAll().size();

        // Create the RobotLog with an existing ID
        RobotLog existingRobotLog = new RobotLog();
        existingRobotLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRobotLogMockMvc.perform(post("/api/robot-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRobotLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RobotLog> robotLogList = robotLogRepository.findAll();
        assertThat(robotLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRobotLogs() throws Exception {
        // Initialize the database
        robotLogRepository.saveAndFlush(robotLog);

        // Get all the robotLogList
        restRobotLogMockMvc.perform(get("/api/robot-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(robotLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].logContent").value(hasItem(DEFAULT_LOG_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getRobotLog() throws Exception {
        // Initialize the database
        robotLogRepository.saveAndFlush(robotLog);

        // Get the robotLog
        restRobotLogMockMvc.perform(get("/api/robot-logs/{id}", robotLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(robotLog.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.logContent").value(DEFAULT_LOG_CONTENT.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRobotLog() throws Exception {
        // Get the robotLog
        restRobotLogMockMvc.perform(get("/api/robot-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRobotLog() throws Exception {
        // Initialize the database
        robotLogService.save(robotLog);

        int databaseSizeBeforeUpdate = robotLogRepository.findAll().size();

        // Update the robotLog
        RobotLog updatedRobotLog = robotLogRepository.findOne(robotLog.getId());
        updatedRobotLog
                .level(UPDATED_LEVEL)
                .logContent(UPDATED_LOG_CONTENT)
                .createDate(UPDATED_CREATE_DATE);

        restRobotLogMockMvc.perform(put("/api/robot-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRobotLog)))
            .andExpect(status().isOk());

        // Validate the RobotLog in the database
        List<RobotLog> robotLogList = robotLogRepository.findAll();
        assertThat(robotLogList).hasSize(databaseSizeBeforeUpdate);
        RobotLog testRobotLog = robotLogList.get(robotLogList.size() - 1);
        assertThat(testRobotLog.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testRobotLog.getLogContent()).isEqualTo(UPDATED_LOG_CONTENT);
        assertThat(testRobotLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRobotLog() throws Exception {
        int databaseSizeBeforeUpdate = robotLogRepository.findAll().size();

        // Create the RobotLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRobotLogMockMvc.perform(put("/api/robot-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotLog)))
            .andExpect(status().isCreated());

        // Validate the RobotLog in the database
        List<RobotLog> robotLogList = robotLogRepository.findAll();
        assertThat(robotLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRobotLog() throws Exception {
        // Initialize the database
        robotLogService.save(robotLog);

        int databaseSizeBeforeDelete = robotLogRepository.findAll().size();

        // Get the robotLog
        restRobotLogMockMvc.perform(delete("/api/robot-logs/{id}", robotLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RobotLog> robotLogList = robotLogRepository.findAll();
        assertThat(robotLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
