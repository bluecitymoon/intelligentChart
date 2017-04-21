package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.MovieCategory;
import com.intelligent.chart.repository.MovieCategoryRepository;
import com.intelligent.chart.service.MovieCategoryService;

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
 * Test class for the MovieCategoryResource REST controller.
 *
 * @see MovieCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MovieCategoryResourceIntTest {

    @Inject
    private MovieCategoryRepository movieCategoryRepository;

    @Inject
    private MovieCategoryService movieCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMovieCategoryMockMvc;

    private MovieCategory movieCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MovieCategoryResource movieCategoryResource = new MovieCategoryResource();
        ReflectionTestUtils.setField(movieCategoryResource, "movieCategoryService", movieCategoryService);
        this.restMovieCategoryMockMvc = MockMvcBuilders.standaloneSetup(movieCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieCategory createEntity(EntityManager em) {
        MovieCategory movieCategory = new MovieCategory();
        return movieCategory;
    }

    @Before
    public void initTest() {
        movieCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieCategory() throws Exception {
        int databaseSizeBeforeCreate = movieCategoryRepository.findAll().size();

        // Create the MovieCategory

        restMovieCategoryMockMvc.perform(post("/api/movie-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieCategory)))
            .andExpect(status().isCreated());

        // Validate the MovieCategory in the database
        List<MovieCategory> movieCategoryList = movieCategoryRepository.findAll();
        assertThat(movieCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MovieCategory testMovieCategory = movieCategoryList.get(movieCategoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createMovieCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieCategoryRepository.findAll().size();

        // Create the MovieCategory with an existing ID
        MovieCategory existingMovieCategory = new MovieCategory();
        existingMovieCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieCategoryMockMvc.perform(post("/api/movie-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMovieCategory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MovieCategory> movieCategoryList = movieCategoryRepository.findAll();
        assertThat(movieCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieCategories() throws Exception {
        // Initialize the database
        movieCategoryRepository.saveAndFlush(movieCategory);

        // Get all the movieCategoryList
        restMovieCategoryMockMvc.perform(get("/api/movie-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieCategory.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMovieCategory() throws Exception {
        // Initialize the database
        movieCategoryRepository.saveAndFlush(movieCategory);

        // Get the movieCategory
        restMovieCategoryMockMvc.perform(get("/api/movie-categories/{id}", movieCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieCategory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieCategory() throws Exception {
        // Get the movieCategory
        restMovieCategoryMockMvc.perform(get("/api/movie-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieCategory() throws Exception {
        // Initialize the database
        movieCategoryService.save(movieCategory);

        int databaseSizeBeforeUpdate = movieCategoryRepository.findAll().size();

        // Update the movieCategory
        MovieCategory updatedMovieCategory = movieCategoryRepository.findOne(movieCategory.getId());

        restMovieCategoryMockMvc.perform(put("/api/movie-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieCategory)))
            .andExpect(status().isOk());

        // Validate the MovieCategory in the database
        List<MovieCategory> movieCategoryList = movieCategoryRepository.findAll();
        assertThat(movieCategoryList).hasSize(databaseSizeBeforeUpdate);
        MovieCategory testMovieCategory = movieCategoryList.get(movieCategoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieCategory() throws Exception {
        int databaseSizeBeforeUpdate = movieCategoryRepository.findAll().size();

        // Create the MovieCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieCategoryMockMvc.perform(put("/api/movie-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieCategory)))
            .andExpect(status().isCreated());

        // Validate the MovieCategory in the database
        List<MovieCategory> movieCategoryList = movieCategoryRepository.findAll();
        assertThat(movieCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieCategory() throws Exception {
        // Initialize the database
        movieCategoryService.save(movieCategory);

        int databaseSizeBeforeDelete = movieCategoryRepository.findAll().size();

        // Get the movieCategory
        restMovieCategoryMockMvc.perform(delete("/api/movie-categories/{id}", movieCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieCategory> movieCategoryList = movieCategoryRepository.findAll();
        assertThat(movieCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
