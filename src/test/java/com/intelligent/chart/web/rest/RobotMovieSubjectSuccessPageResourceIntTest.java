package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.RobotMovieSubjectSuccessPage;
import com.intelligent.chart.repository.RobotMovieSubjectSuccessPageRepository;
import com.intelligent.chart.service.RobotMovieSubjectSuccessPageService;

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
 * Test class for the RobotMovieSubjectSuccessPageResource REST controller.
 *
 * @see RobotMovieSubjectSuccessPageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class RobotMovieSubjectSuccessPageResourceIntTest {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private RobotMovieSubjectSuccessPageRepository robotMovieSubjectSuccessPageRepository;

    @Inject
    private RobotMovieSubjectSuccessPageService robotMovieSubjectSuccessPageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRobotMovieSubjectSuccessPageMockMvc;

    private RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RobotMovieSubjectSuccessPageResource robotMovieSubjectSuccessPageResource = new RobotMovieSubjectSuccessPageResource();
        ReflectionTestUtils.setField(robotMovieSubjectSuccessPageResource, "robotMovieSubjectSuccessPageService", robotMovieSubjectSuccessPageService);
        this.restRobotMovieSubjectSuccessPageMockMvc = MockMvcBuilders.standaloneSetup(robotMovieSubjectSuccessPageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RobotMovieSubjectSuccessPage createEntity(EntityManager em) {
        RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage = new RobotMovieSubjectSuccessPage()
                .tag(DEFAULT_TAG)
                .pageNumber(DEFAULT_PAGE_NUMBER)
                .createDate(DEFAULT_CREATE_DATE);
        return robotMovieSubjectSuccessPage;
    }

    @Before
    public void initTest() {
        robotMovieSubjectSuccessPage = createEntity(em);
    }

    @Test
    @Transactional
    public void createRobotMovieSubjectSuccessPage() throws Exception {
        int databaseSizeBeforeCreate = robotMovieSubjectSuccessPageRepository.findAll().size();

        // Create the RobotMovieSubjectSuccessPage

        restRobotMovieSubjectSuccessPageMockMvc.perform(post("/api/robot-movie-subject-success-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotMovieSubjectSuccessPage)))
            .andExpect(status().isCreated());

        // Validate the RobotMovieSubjectSuccessPage in the database
        List<RobotMovieSubjectSuccessPage> robotMovieSubjectSuccessPageList = robotMovieSubjectSuccessPageRepository.findAll();
        assertThat(robotMovieSubjectSuccessPageList).hasSize(databaseSizeBeforeCreate + 1);
        RobotMovieSubjectSuccessPage testRobotMovieSubjectSuccessPage = robotMovieSubjectSuccessPageList.get(robotMovieSubjectSuccessPageList.size() - 1);
        assertThat(testRobotMovieSubjectSuccessPage.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testRobotMovieSubjectSuccessPage.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testRobotMovieSubjectSuccessPage.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createRobotMovieSubjectSuccessPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = robotMovieSubjectSuccessPageRepository.findAll().size();

        // Create the RobotMovieSubjectSuccessPage with an existing ID
        RobotMovieSubjectSuccessPage existingRobotMovieSubjectSuccessPage = new RobotMovieSubjectSuccessPage();
        existingRobotMovieSubjectSuccessPage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRobotMovieSubjectSuccessPageMockMvc.perform(post("/api/robot-movie-subject-success-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRobotMovieSubjectSuccessPage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RobotMovieSubjectSuccessPage> robotMovieSubjectSuccessPageList = robotMovieSubjectSuccessPageRepository.findAll();
        assertThat(robotMovieSubjectSuccessPageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRobotMovieSubjectSuccessPages() throws Exception {
        // Initialize the database
        robotMovieSubjectSuccessPageRepository.saveAndFlush(robotMovieSubjectSuccessPage);

        // Get all the robotMovieSubjectSuccessPageList
        restRobotMovieSubjectSuccessPageMockMvc.perform(get("/api/robot-movie-subject-success-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(robotMovieSubjectSuccessPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getRobotMovieSubjectSuccessPage() throws Exception {
        // Initialize the database
        robotMovieSubjectSuccessPageRepository.saveAndFlush(robotMovieSubjectSuccessPage);

        // Get the robotMovieSubjectSuccessPage
        restRobotMovieSubjectSuccessPageMockMvc.perform(get("/api/robot-movie-subject-success-pages/{id}", robotMovieSubjectSuccessPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(robotMovieSubjectSuccessPage.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRobotMovieSubjectSuccessPage() throws Exception {
        // Get the robotMovieSubjectSuccessPage
        restRobotMovieSubjectSuccessPageMockMvc.perform(get("/api/robot-movie-subject-success-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRobotMovieSubjectSuccessPage() throws Exception {
        // Initialize the database
        robotMovieSubjectSuccessPageService.save(robotMovieSubjectSuccessPage);

        int databaseSizeBeforeUpdate = robotMovieSubjectSuccessPageRepository.findAll().size();

        // Update the robotMovieSubjectSuccessPage
        RobotMovieSubjectSuccessPage updatedRobotMovieSubjectSuccessPage = robotMovieSubjectSuccessPageRepository.findOne(robotMovieSubjectSuccessPage.getId());
        updatedRobotMovieSubjectSuccessPage
                .tag(UPDATED_TAG)
                .pageNumber(UPDATED_PAGE_NUMBER)
                .createDate(UPDATED_CREATE_DATE);

        restRobotMovieSubjectSuccessPageMockMvc.perform(put("/api/robot-movie-subject-success-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRobotMovieSubjectSuccessPage)))
            .andExpect(status().isOk());

        // Validate the RobotMovieSubjectSuccessPage in the database
        List<RobotMovieSubjectSuccessPage> robotMovieSubjectSuccessPageList = robotMovieSubjectSuccessPageRepository.findAll();
        assertThat(robotMovieSubjectSuccessPageList).hasSize(databaseSizeBeforeUpdate);
        RobotMovieSubjectSuccessPage testRobotMovieSubjectSuccessPage = robotMovieSubjectSuccessPageList.get(robotMovieSubjectSuccessPageList.size() - 1);
        assertThat(testRobotMovieSubjectSuccessPage.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testRobotMovieSubjectSuccessPage.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testRobotMovieSubjectSuccessPage.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRobotMovieSubjectSuccessPage() throws Exception {
        int databaseSizeBeforeUpdate = robotMovieSubjectSuccessPageRepository.findAll().size();

        // Create the RobotMovieSubjectSuccessPage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRobotMovieSubjectSuccessPageMockMvc.perform(put("/api/robot-movie-subject-success-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(robotMovieSubjectSuccessPage)))
            .andExpect(status().isCreated());

        // Validate the RobotMovieSubjectSuccessPage in the database
        List<RobotMovieSubjectSuccessPage> robotMovieSubjectSuccessPageList = robotMovieSubjectSuccessPageRepository.findAll();
        assertThat(robotMovieSubjectSuccessPageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRobotMovieSubjectSuccessPage() throws Exception {
        // Initialize the database
        robotMovieSubjectSuccessPageService.save(robotMovieSubjectSuccessPage);

        int databaseSizeBeforeDelete = robotMovieSubjectSuccessPageRepository.findAll().size();

        // Get the robotMovieSubjectSuccessPage
        restRobotMovieSubjectSuccessPageMockMvc.perform(delete("/api/robot-movie-subject-success-pages/{id}", robotMovieSubjectSuccessPage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RobotMovieSubjectSuccessPage> robotMovieSubjectSuccessPageList = robotMovieSubjectSuccessPageRepository.findAll();
        assertThat(robotMovieSubjectSuccessPageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
