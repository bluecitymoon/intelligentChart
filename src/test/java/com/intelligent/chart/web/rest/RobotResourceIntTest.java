package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.Robot;
import com.intelligent.chart.repository.RobotRepository;
import com.intelligent.chart.service.RobotService;

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

import com.intelligent.chart.domain.enumeration.RobotStatus;
/**
 * Test class for the RobotResource REST controller.
 *
 * @see RobotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class RobotResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROBOT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ROBOT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_STOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_STOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final RobotStatus DEFAULT_STATUS = RobotStatus.standBy;
    private static final RobotStatus UPDATED_STATUS = RobotStatus.running;

    @Inject
    private RobotRepository robotRepository;

    @Inject
    private RobotService robotService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRobotMockMvc;

    private Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RobotResource robotResource = new RobotResource();
        ReflectionTestUtils.setField(robotResource, "robotService", robotService);
        this.restRobotMockMvc = MockMvcBuilders.standaloneSetup(robotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Robot createEntity(EntityManager em) {
        Robot robot = new Robot()
                .name(DEFAULT_NAME)
                .robotDescription(DEFAULT_ROBOT_DESCRIPTION)
                .identifier(DEFAULT_IDENTIFIER)
                .lastStart(DEFAULT_LAST_START)
                .lastStop(DEFAULT_LAST_STOP)
                .status(DEFAULT_STATUS);
        return robot;
    }

    @Before
    public void initTest() {
        robot = createEntity(em);
    }

    @Test
    @Transactional
    public void createRobot() throws Exception {
        int databaseSizeBeforeCreate = robotRepository.findAll().size();

        // Create the Robot

        restRobotMockMvc.perform(post("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robot)))
            .andExpect(status().isCreated());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeCreate + 1);
        Robot testRobot = robotList.get(robotList.size() - 1);
        assertThat(testRobot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRobot.getRobotDescription()).isEqualTo(DEFAULT_ROBOT_DESCRIPTION);
        assertThat(testRobot.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testRobot.getLastStart()).isEqualTo(DEFAULT_LAST_START);
        assertThat(testRobot.getLastStop()).isEqualTo(DEFAULT_LAST_STOP);
        assertThat(testRobot.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRobotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = robotRepository.findAll().size();

        // Create the Robot with an existing ID
        Robot existingRobot = new Robot();
        existingRobot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRobotMockMvc.perform(post("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRobot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRobots() throws Exception {
        // Initialize the database
        robotRepository.saveAndFlush(robot);

        // Get all the robotList
        restRobotMockMvc.perform(get("/api/robots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(robot.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].robotDescription").value(hasItem(DEFAULT_ROBOT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].lastStart").value(hasItem(sameInstant(DEFAULT_LAST_START))))
            .andExpect(jsonPath("$.[*].lastStop").value(hasItem(sameInstant(DEFAULT_LAST_STOP))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getRobot() throws Exception {
        // Initialize the database
        robotRepository.saveAndFlush(robot);

        // Get the robot
        restRobotMockMvc.perform(get("/api/robots/{id}", robot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(robot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.robotDescription").value(DEFAULT_ROBOT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.lastStart").value(sameInstant(DEFAULT_LAST_START)))
            .andExpect(jsonPath("$.lastStop").value(sameInstant(DEFAULT_LAST_STOP)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRobot() throws Exception {
        // Get the robot
        restRobotMockMvc.perform(get("/api/robots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRobot() throws Exception {
        // Initialize the database
        robotService.save(robot);

        int databaseSizeBeforeUpdate = robotRepository.findAll().size();

        // Update the robot
        Robot updatedRobot = robotRepository.findOne(robot.getId());
        updatedRobot
                .name(UPDATED_NAME)
                .robotDescription(UPDATED_ROBOT_DESCRIPTION)
                .identifier(UPDATED_IDENTIFIER)
                .lastStart(UPDATED_LAST_START)
                .lastStop(UPDATED_LAST_STOP)
                .status(UPDATED_STATUS);

        restRobotMockMvc.perform(put("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRobot)))
            .andExpect(status().isOk());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeUpdate);
        Robot testRobot = robotList.get(robotList.size() - 1);
        assertThat(testRobot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRobot.getRobotDescription()).isEqualTo(UPDATED_ROBOT_DESCRIPTION);
        assertThat(testRobot.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testRobot.getLastStart()).isEqualTo(UPDATED_LAST_START);
        assertThat(testRobot.getLastStop()).isEqualTo(UPDATED_LAST_STOP);
        assertThat(testRobot.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRobot() throws Exception {
        int databaseSizeBeforeUpdate = robotRepository.findAll().size();

        // Create the Robot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRobotMockMvc.perform(put("/api/robots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robot)))
            .andExpect(status().isCreated());

        // Validate the Robot in the database
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRobot() throws Exception {
        // Initialize the database
        robotService.save(robot);

        int databaseSizeBeforeDelete = robotRepository.findAll().size();

        // Get the robot
        restRobotMockMvc.perform(delete("/api/robots/{id}", robot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Robot> robotList = robotRepository.findAll();
        assertThat(robotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
