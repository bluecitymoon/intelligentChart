package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.RobotMovieSubjectFailPage;
import com.intelligent.chart.repository.RobotMovieSubjectFailPageRepository;
import com.intelligent.chart.service.RobotMovieSubjectFailPageService;

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

/**
 * Test class for the RobotMovieSubjectFailPageResource REST controller.
 *
 * @see RobotMovieSubjectFailPageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class RobotMovieSubjectFailPageResourceIntTest {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Inject
    private RobotMovieSubjectFailPageRepository robotMovieSubjectFailPageRepository;

    @Inject
    private RobotMovieSubjectFailPageService robotMovieSubjectFailPageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRobotMovieSubjectFailPageMockMvc;

    private RobotMovieSubjectFailPage robotMovieSubjectFailPage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RobotMovieSubjectFailPageResource robotMovieSubjectFailPageResource = new RobotMovieSubjectFailPageResource();
        ReflectionTestUtils.setField(robotMovieSubjectFailPageResource, "robotMovieSubjectFailPageService", robotMovieSubjectFailPageService);
        this.restRobotMovieSubjectFailPageMockMvc = MockMvcBuilders.standaloneSetup(robotMovieSubjectFailPageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RobotMovieSubjectFailPage createEntity(EntityManager em) {
        RobotMovieSubjectFailPage robotMovieSubjectFailPage = new RobotMovieSubjectFailPage()
                .tag(DEFAULT_TAG)
                .pageNumber(DEFAULT_PAGE_NUMBER)
                .createDate(DEFAULT_CREATE_DATE)
                .reason(DEFAULT_REASON);
        return robotMovieSubjectFailPage;
    }

    @Before
    public void initTest() {
        robotMovieSubjectFailPage = createEntity(em);
    }

    @Test
    @Transactional
    public void createRobotMovieSubjectFailPage() throws Exception {
        int databaseSizeBeforeCreate = robotMovieSubjectFailPageRepository.findAll().size();

        // Create the RobotMovieSubjectFailPage

        restRobotMovieSubjectFailPageMockMvc.perform(post("/api/robot-movie-subject-fail-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotMovieSubjectFailPage)))
            .andExpect(status().isCreated());

        // Validate the RobotMovieSubjectFailPage in the database
        List<RobotMovieSubjectFailPage> robotMovieSubjectFailPageList = robotMovieSubjectFailPageRepository.findAll();
        assertThat(robotMovieSubjectFailPageList).hasSize(databaseSizeBeforeCreate + 1);
        RobotMovieSubjectFailPage testRobotMovieSubjectFailPage = robotMovieSubjectFailPageList.get(robotMovieSubjectFailPageList.size() - 1);
        assertThat(testRobotMovieSubjectFailPage.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testRobotMovieSubjectFailPage.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testRobotMovieSubjectFailPage.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRobotMovieSubjectFailPage.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createRobotMovieSubjectFailPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = robotMovieSubjectFailPageRepository.findAll().size();

        // Create the RobotMovieSubjectFailPage with an existing ID
        RobotMovieSubjectFailPage existingRobotMovieSubjectFailPage = new RobotMovieSubjectFailPage();
        existingRobotMovieSubjectFailPage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRobotMovieSubjectFailPageMockMvc.perform(post("/api/robot-movie-subject-fail-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRobotMovieSubjectFailPage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RobotMovieSubjectFailPage> robotMovieSubjectFailPageList = robotMovieSubjectFailPageRepository.findAll();
        assertThat(robotMovieSubjectFailPageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRobotMovieSubjectFailPages() throws Exception {
        // Initialize the database
        robotMovieSubjectFailPageRepository.saveAndFlush(robotMovieSubjectFailPage);

        // Get all the robotMovieSubjectFailPageList
        restRobotMovieSubjectFailPageMockMvc.perform(get("/api/robot-movie-subject-fail-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(robotMovieSubjectFailPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getRobotMovieSubjectFailPage() throws Exception {
        // Initialize the database
        robotMovieSubjectFailPageRepository.saveAndFlush(robotMovieSubjectFailPage);

        // Get the robotMovieSubjectFailPage
        restRobotMovieSubjectFailPageMockMvc.perform(get("/api/robot-movie-subject-fail-pages/{id}", robotMovieSubjectFailPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(robotMovieSubjectFailPage.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRobotMovieSubjectFailPage() throws Exception {
        // Get the robotMovieSubjectFailPage
        restRobotMovieSubjectFailPageMockMvc.perform(get("/api/robot-movie-subject-fail-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRobotMovieSubjectFailPage() throws Exception {
        // Initialize the database
        robotMovieSubjectFailPageService.save(robotMovieSubjectFailPage);

        int databaseSizeBeforeUpdate = robotMovieSubjectFailPageRepository.findAll().size();

        // Update the robotMovieSubjectFailPage
        RobotMovieSubjectFailPage updatedRobotMovieSubjectFailPage = robotMovieSubjectFailPageRepository.findOne(robotMovieSubjectFailPage.getId());
        updatedRobotMovieSubjectFailPage
                .tag(UPDATED_TAG)
                .pageNumber(UPDATED_PAGE_NUMBER)
                .createDate(UPDATED_CREATE_DATE)
                .reason(UPDATED_REASON);

        restRobotMovieSubjectFailPageMockMvc.perform(put("/api/robot-movie-subject-fail-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRobotMovieSubjectFailPage)))
            .andExpect(status().isOk());

        // Validate the RobotMovieSubjectFailPage in the database
        List<RobotMovieSubjectFailPage> robotMovieSubjectFailPageList = robotMovieSubjectFailPageRepository.findAll();
        assertThat(robotMovieSubjectFailPageList).hasSize(databaseSizeBeforeUpdate);
        RobotMovieSubjectFailPage testRobotMovieSubjectFailPage = robotMovieSubjectFailPageList.get(robotMovieSubjectFailPageList.size() - 1);
        assertThat(testRobotMovieSubjectFailPage.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testRobotMovieSubjectFailPage.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testRobotMovieSubjectFailPage.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRobotMovieSubjectFailPage.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingRobotMovieSubjectFailPage() throws Exception {
        int databaseSizeBeforeUpdate = robotMovieSubjectFailPageRepository.findAll().size();

        // Create the RobotMovieSubjectFailPage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRobotMovieSubjectFailPageMockMvc.perform(put("/api/robot-movie-subject-fail-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotMovieSubjectFailPage)))
            .andExpect(status().isCreated());

        // Validate the RobotMovieSubjectFailPage in the database
        List<RobotMovieSubjectFailPage> robotMovieSubjectFailPageList = robotMovieSubjectFailPageRepository.findAll();
        assertThat(robotMovieSubjectFailPageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRobotMovieSubjectFailPage() throws Exception {
        // Initialize the database
        robotMovieSubjectFailPageService.save(robotMovieSubjectFailPage);

        int databaseSizeBeforeDelete = robotMovieSubjectFailPageRepository.findAll().size();

        // Get the robotMovieSubjectFailPage
        restRobotMovieSubjectFailPageMockMvc.perform(delete("/api/robot-movie-subject-fail-pages/{id}", robotMovieSubjectFailPage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RobotMovieSubjectFailPage> robotMovieSubjectFailPageList = robotMovieSubjectFailPageRepository.findAll();
        assertThat(robotMovieSubjectFailPageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
