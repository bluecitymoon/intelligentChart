package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.Movie;
import com.intelligent.chart.repository.MovieRepository;
import com.intelligent.chart.service.MovieService;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
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
 * Test class for the MovieResource REST controller.
 *
 * @see MovieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MovieResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOUBAN_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOUBAN_URL = "BBBBBBBBBB";

    private static final Float DEFAULT_RATE = 1F;
    private static final Float UPDATED_RATE = 2F;

    private static final String DEFAULT_COVER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RUN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RUN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private MovieRepository movieRepository;

    @Inject
    private MovieService movieService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMovieMockMvc;

    private Movie movie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MovieResource movieResource = new MovieResource();
        ReflectionTestUtils.setField(movieResource, "movieService", movieService);
        this.restMovieMockMvc = MockMvcBuilders.standaloneSetup(movieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
                .name(DEFAULT_NAME)
                .doubanUrl(DEFAULT_DOUBAN_URL)
                .rate(DEFAULT_RATE)
                .coverUrl(DEFAULT_COVER_URL)
                .area(DEFAULT_AREA)
                .language(DEFAULT_LANGUAGE)
                .runDate(DEFAULT_RUN_DATE)
                .term(DEFAULT_TERM)
                .createDate(DEFAULT_CREATE_DATE)
                .description(DEFAULT_DESCRIPTION);
        return movie;
    }

    @Before
    public void initTest() {
        movie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovie() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // Create the Movie

        restMovieMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate + 1);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovie.getDoubanUrl()).isEqualTo(DEFAULT_DOUBAN_URL);
        assertThat(testMovie.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testMovie.getCoverUrl()).isEqualTo(DEFAULT_COVER_URL);
        assertThat(testMovie.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testMovie.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testMovie.getRunDate()).isEqualTo(DEFAULT_RUN_DATE);
        assertThat(testMovie.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testMovie.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testMovie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // Create the Movie with an existing ID
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMovie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setName(null);

        // Create the Movie, which fails.

        restMovieMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc.perform(get("/api/movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].doubanUrl").value(hasItem(DEFAULT_DOUBAN_URL.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].runDate").value(hasItem(DEFAULT_RUN_DATE.toString())))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc.perform(get("/api/movies/{id}", movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.doubanUrl").value(DEFAULT_DOUBAN_URL.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.coverUrl").value(DEFAULT_COVER_URL.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.runDate").value(DEFAULT_RUN_DATE.toString()))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get("/api/movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovie() throws Exception {
        // Initialize the database
        movieService.save(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie
        Movie updatedMovie = movieRepository.findOne(movie.getId());
        updatedMovie
                .name(UPDATED_NAME)
                .doubanUrl(UPDATED_DOUBAN_URL)
                .rate(UPDATED_RATE)
                .coverUrl(UPDATED_COVER_URL)
                .area(UPDATED_AREA)
                .language(UPDATED_LANGUAGE)
                .runDate(UPDATED_RUN_DATE)
                .term(UPDATED_TERM)
                .createDate(UPDATED_CREATE_DATE)
                .description(UPDATED_DESCRIPTION);

        restMovieMockMvc.perform(put("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovie)))
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovie.getDoubanUrl()).isEqualTo(UPDATED_DOUBAN_URL);
        assertThat(testMovie.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testMovie.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testMovie.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testMovie.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testMovie.getRunDate()).isEqualTo(UPDATED_RUN_DATE);
        assertThat(testMovie.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testMovie.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testMovie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Create the Movie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieMockMvc.perform(put("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovie() throws Exception {
        // Initialize the database
        movieService.save(movie);

        int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Get the movie
        restMovieMockMvc.perform(delete("/api/movies/{id}", movie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
