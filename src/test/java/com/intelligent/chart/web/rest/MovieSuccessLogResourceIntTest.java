package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.MovieSuccessLog;
import com.intelligent.chart.repository.MovieSuccessLogRepository;
import com.intelligent.chart.service.MovieSuccessLogService;

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
 * Test class for the MovieSuccessLogResource REST controller.
 *
 * @see MovieSuccessLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MovieSuccessLogResourceIntTest {

    private static final Long DEFAULT_MOVIE_ID = 1L;
    private static final Long UPDATED_MOVIE_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOUBAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOUBAN_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private MovieSuccessLogRepository movieSuccessLogRepository;

    @Inject
    private MovieSuccessLogService movieSuccessLogService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMovieSuccessLogMockMvc;

    private MovieSuccessLog movieSuccessLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MovieSuccessLogResource movieSuccessLogResource = new MovieSuccessLogResource();
        ReflectionTestUtils.setField(movieSuccessLogResource, "movieSuccessLogService", movieSuccessLogService);
        this.restMovieSuccessLogMockMvc = MockMvcBuilders.standaloneSetup(movieSuccessLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieSuccessLog createEntity(EntityManager em) {
        MovieSuccessLog movieSuccessLog = new MovieSuccessLog()
                .movieId(DEFAULT_MOVIE_ID)
                .name(DEFAULT_NAME)
                .doubanId(DEFAULT_DOUBAN_ID)
                .createDate(DEFAULT_CREATE_DATE);
        return movieSuccessLog;
    }

    @Before
    public void initTest() {
        movieSuccessLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieSuccessLog() throws Exception {
        int databaseSizeBeforeCreate = movieSuccessLogRepository.findAll().size();

        // Create the MovieSuccessLog

        restMovieSuccessLogMockMvc.perform(post("/api/movie-success-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieSuccessLog)))
            .andExpect(status().isCreated());

        // Validate the MovieSuccessLog in the database
        List<MovieSuccessLog> movieSuccessLogList = movieSuccessLogRepository.findAll();
        assertThat(movieSuccessLogList).hasSize(databaseSizeBeforeCreate + 1);
        MovieSuccessLog testMovieSuccessLog = movieSuccessLogList.get(movieSuccessLogList.size() - 1);
        assertThat(testMovieSuccessLog.getMovieId()).isEqualTo(DEFAULT_MOVIE_ID);
        assertThat(testMovieSuccessLog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovieSuccessLog.getDoubanId()).isEqualTo(DEFAULT_DOUBAN_ID);
        assertThat(testMovieSuccessLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMovieSuccessLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieSuccessLogRepository.findAll().size();

        // Create the MovieSuccessLog with an existing ID
        MovieSuccessLog existingMovieSuccessLog = new MovieSuccessLog();
        existingMovieSuccessLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieSuccessLogMockMvc.perform(post("/api/movie-success-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMovieSuccessLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MovieSuccessLog> movieSuccessLogList = movieSuccessLogRepository.findAll();
        assertThat(movieSuccessLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieSuccessLogs() throws Exception {
        // Initialize the database
        movieSuccessLogRepository.saveAndFlush(movieSuccessLog);

        // Get all the movieSuccessLogList
        restMovieSuccessLogMockMvc.perform(get("/api/movie-success-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieSuccessLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].movieId").value(hasItem(DEFAULT_MOVIE_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].doubanId").value(hasItem(DEFAULT_DOUBAN_ID.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getMovieSuccessLog() throws Exception {
        // Initialize the database
        movieSuccessLogRepository.saveAndFlush(movieSuccessLog);

        // Get the movieSuccessLog
        restMovieSuccessLogMockMvc.perform(get("/api/movie-success-logs/{id}", movieSuccessLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieSuccessLog.getId().intValue()))
            .andExpect(jsonPath("$.movieId").value(DEFAULT_MOVIE_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.doubanId").value(DEFAULT_DOUBAN_ID.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingMovieSuccessLog() throws Exception {
        // Get the movieSuccessLog
        restMovieSuccessLogMockMvc.perform(get("/api/movie-success-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieSuccessLog() throws Exception {
        // Initialize the database
        movieSuccessLogService.save(movieSuccessLog);

        int databaseSizeBeforeUpdate = movieSuccessLogRepository.findAll().size();

        // Update the movieSuccessLog
        MovieSuccessLog updatedMovieSuccessLog = movieSuccessLogRepository.findOne(movieSuccessLog.getId());
        updatedMovieSuccessLog
                .movieId(UPDATED_MOVIE_ID)
                .name(UPDATED_NAME)
                .doubanId(UPDATED_DOUBAN_ID)
                .createDate(UPDATED_CREATE_DATE);

        restMovieSuccessLogMockMvc.perform(put("/api/movie-success-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieSuccessLog)))
            .andExpect(status().isOk());

        // Validate the MovieSuccessLog in the database
        List<MovieSuccessLog> movieSuccessLogList = movieSuccessLogRepository.findAll();
        assertThat(movieSuccessLogList).hasSize(databaseSizeBeforeUpdate);
        MovieSuccessLog testMovieSuccessLog = movieSuccessLogList.get(movieSuccessLogList.size() - 1);
        assertThat(testMovieSuccessLog.getMovieId()).isEqualTo(UPDATED_MOVIE_ID);
        assertThat(testMovieSuccessLog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovieSuccessLog.getDoubanId()).isEqualTo(UPDATED_DOUBAN_ID);
        assertThat(testMovieSuccessLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieSuccessLog() throws Exception {
        int databaseSizeBeforeUpdate = movieSuccessLogRepository.findAll().size();

        // Create the MovieSuccessLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieSuccessLogMockMvc.perform(put("/api/movie-success-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieSuccessLog)))
            .andExpect(status().isCreated());

        // Validate the MovieSuccessLog in the database
        List<MovieSuccessLog> movieSuccessLogList = movieSuccessLogRepository.findAll();
        assertThat(movieSuccessLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieSuccessLog() throws Exception {
        // Initialize the database
        movieSuccessLogService.save(movieSuccessLog);

        int databaseSizeBeforeDelete = movieSuccessLogRepository.findAll().size();

        // Get the movieSuccessLog
        restMovieSuccessLogMockMvc.perform(delete("/api/movie-success-logs/{id}", movieSuccessLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieSuccessLog> movieSuccessLogList = movieSuccessLogRepository.findAll();
        assertThat(movieSuccessLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
