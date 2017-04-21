package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.MovieParticipant;
import com.intelligent.chart.repository.MovieParticipantRepository;
import com.intelligent.chart.service.MovieParticipantService;

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
 * Test class for the MovieParticipantResource REST controller.
 *
 * @see MovieParticipantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MovieParticipantResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private MovieParticipantRepository movieParticipantRepository;

    @Inject
    private MovieParticipantService movieParticipantService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMovieParticipantMockMvc;

    private MovieParticipant movieParticipant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MovieParticipantResource movieParticipantResource = new MovieParticipantResource();
        ReflectionTestUtils.setField(movieParticipantResource, "movieParticipantService", movieParticipantService);
        this.restMovieParticipantMockMvc = MockMvcBuilders.standaloneSetup(movieParticipantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieParticipant createEntity(EntityManager em) {
        MovieParticipant movieParticipant = new MovieParticipant()
                .createDate(DEFAULT_CREATE_DATE);
        return movieParticipant;
    }

    @Before
    public void initTest() {
        movieParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieParticipant() throws Exception {
        int databaseSizeBeforeCreate = movieParticipantRepository.findAll().size();

        // Create the MovieParticipant

        restMovieParticipantMockMvc.perform(post("/api/movie-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieParticipant)))
            .andExpect(status().isCreated());

        // Validate the MovieParticipant in the database
        List<MovieParticipant> movieParticipantList = movieParticipantRepository.findAll();
        assertThat(movieParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        MovieParticipant testMovieParticipant = movieParticipantList.get(movieParticipantList.size() - 1);
        assertThat(testMovieParticipant.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMovieParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieParticipantRepository.findAll().size();

        // Create the MovieParticipant with an existing ID
        MovieParticipant existingMovieParticipant = new MovieParticipant();
        existingMovieParticipant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieParticipantMockMvc.perform(post("/api/movie-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMovieParticipant)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MovieParticipant> movieParticipantList = movieParticipantRepository.findAll();
        assertThat(movieParticipantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieParticipants() throws Exception {
        // Initialize the database
        movieParticipantRepository.saveAndFlush(movieParticipant);

        // Get all the movieParticipantList
        restMovieParticipantMockMvc.perform(get("/api/movie-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getMovieParticipant() throws Exception {
        // Initialize the database
        movieParticipantRepository.saveAndFlush(movieParticipant);

        // Get the movieParticipant
        restMovieParticipantMockMvc.perform(get("/api/movie-participants/{id}", movieParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieParticipant.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingMovieParticipant() throws Exception {
        // Get the movieParticipant
        restMovieParticipantMockMvc.perform(get("/api/movie-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieParticipant() throws Exception {
        // Initialize the database
        movieParticipantService.save(movieParticipant);

        int databaseSizeBeforeUpdate = movieParticipantRepository.findAll().size();

        // Update the movieParticipant
        MovieParticipant updatedMovieParticipant = movieParticipantRepository.findOne(movieParticipant.getId());
        updatedMovieParticipant
                .createDate(UPDATED_CREATE_DATE);

        restMovieParticipantMockMvc.perform(put("/api/movie-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieParticipant)))
            .andExpect(status().isOk());

        // Validate the MovieParticipant in the database
        List<MovieParticipant> movieParticipantList = movieParticipantRepository.findAll();
        assertThat(movieParticipantList).hasSize(databaseSizeBeforeUpdate);
        MovieParticipant testMovieParticipant = movieParticipantList.get(movieParticipantList.size() - 1);
        assertThat(testMovieParticipant.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieParticipant() throws Exception {
        int databaseSizeBeforeUpdate = movieParticipantRepository.findAll().size();

        // Create the MovieParticipant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieParticipantMockMvc.perform(put("/api/movie-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieParticipant)))
            .andExpect(status().isCreated());

        // Validate the MovieParticipant in the database
        List<MovieParticipant> movieParticipantList = movieParticipantRepository.findAll();
        assertThat(movieParticipantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieParticipant() throws Exception {
        // Initialize the database
        movieParticipantService.save(movieParticipant);

        int databaseSizeBeforeDelete = movieParticipantRepository.findAll().size();

        // Get the movieParticipant
        restMovieParticipantMockMvc.perform(delete("/api/movie-participants/{id}", movieParticipant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieParticipant> movieParticipantList = movieParticipantRepository.findAll();
        assertThat(movieParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
