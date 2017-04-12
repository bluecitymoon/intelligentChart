package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.DoubleMovieSubject;
import com.intelligent.chart.repository.DoubleMovieSubjectRepository;
import com.intelligent.chart.service.DoubleMovieSubjectService;

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
 * Test class for the DoubleMovieSubjectResource REST controller.
 *
 * @see DoubleMovieSubjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class DoubleMovieSubjectResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COVER = "AAAAAAAAAA";
    private static final String UPDATED_COVER = "BBBBBBBBBB";

    private static final String DEFAULT_RATE = "AAAAAAAAAA";
    private static final String UPDATED_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_DOUBAN_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOUBAN_ID = "BBBBBBBBBB";

    @Inject
    private DoubleMovieSubjectRepository doubleMovieSubjectRepository;

    @Inject
    private DoubleMovieSubjectService doubleMovieSubjectService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDoubleMovieSubjectMockMvc;

    private DoubleMovieSubject doubleMovieSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoubleMovieSubjectResource doubleMovieSubjectResource = new DoubleMovieSubjectResource();
        ReflectionTestUtils.setField(doubleMovieSubjectResource, "doubleMovieSubjectService", doubleMovieSubjectService);
        this.restDoubleMovieSubjectMockMvc = MockMvcBuilders.standaloneSetup(doubleMovieSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoubleMovieSubject createEntity(EntityManager em) {
        DoubleMovieSubject doubleMovieSubject = new DoubleMovieSubject()
                .title(DEFAULT_TITLE)
                .url(DEFAULT_URL)
                .cover(DEFAULT_COVER)
                .rate(DEFAULT_RATE)
                .doubanId(DEFAULT_DOUBAN_ID);
        return doubleMovieSubject;
    }

    @Before
    public void initTest() {
        doubleMovieSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoubleMovieSubject() throws Exception {
        int databaseSizeBeforeCreate = doubleMovieSubjectRepository.findAll().size();

        // Create the DoubleMovieSubject

        restDoubleMovieSubjectMockMvc.perform(post("/api/double-movie-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doubleMovieSubject)))
            .andExpect(status().isCreated());

        // Validate the DoubleMovieSubject in the database
        List<DoubleMovieSubject> doubleMovieSubjectList = doubleMovieSubjectRepository.findAll();
        assertThat(doubleMovieSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        DoubleMovieSubject testDoubleMovieSubject = doubleMovieSubjectList.get(doubleMovieSubjectList.size() - 1);
        assertThat(testDoubleMovieSubject.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDoubleMovieSubject.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDoubleMovieSubject.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testDoubleMovieSubject.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testDoubleMovieSubject.getDoubanId()).isEqualTo(DEFAULT_DOUBAN_ID);
    }

    @Test
    @Transactional
    public void createDoubleMovieSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doubleMovieSubjectRepository.findAll().size();

        // Create the DoubleMovieSubject with an existing ID
        DoubleMovieSubject existingDoubleMovieSubject = new DoubleMovieSubject();
        existingDoubleMovieSubject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoubleMovieSubjectMockMvc.perform(post("/api/double-movie-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDoubleMovieSubject)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoubleMovieSubject> doubleMovieSubjectList = doubleMovieSubjectRepository.findAll();
        assertThat(doubleMovieSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoubleMovieSubjects() throws Exception {
        // Initialize the database
        doubleMovieSubjectRepository.saveAndFlush(doubleMovieSubject);

        // Get all the doubleMovieSubjectList
        restDoubleMovieSubjectMockMvc.perform(get("/api/double-movie-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doubleMovieSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(DEFAULT_COVER.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.toString())))
            .andExpect(jsonPath("$.[*].doubanId").value(hasItem(DEFAULT_DOUBAN_ID.toString())));
    }

    @Test
    @Transactional
    public void getDoubleMovieSubject() throws Exception {
        // Initialize the database
        doubleMovieSubjectRepository.saveAndFlush(doubleMovieSubject);

        // Get the doubleMovieSubject
        restDoubleMovieSubjectMockMvc.perform(get("/api/double-movie-subjects/{id}", doubleMovieSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doubleMovieSubject.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.cover").value(DEFAULT_COVER.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.toString()))
            .andExpect(jsonPath("$.doubanId").value(DEFAULT_DOUBAN_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoubleMovieSubject() throws Exception {
        // Get the doubleMovieSubject
        restDoubleMovieSubjectMockMvc.perform(get("/api/double-movie-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoubleMovieSubject() throws Exception {
        // Initialize the database
        doubleMovieSubjectService.save(doubleMovieSubject);

        int databaseSizeBeforeUpdate = doubleMovieSubjectRepository.findAll().size();

        // Update the doubleMovieSubject
        DoubleMovieSubject updatedDoubleMovieSubject = doubleMovieSubjectRepository.findOne(doubleMovieSubject.getId());
        updatedDoubleMovieSubject
                .title(UPDATED_TITLE)
                .url(UPDATED_URL)
                .cover(UPDATED_COVER)
                .rate(UPDATED_RATE)
                .doubanId(UPDATED_DOUBAN_ID);

        restDoubleMovieSubjectMockMvc.perform(put("/api/double-movie-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoubleMovieSubject)))
            .andExpect(status().isOk());

        // Validate the DoubleMovieSubject in the database
        List<DoubleMovieSubject> doubleMovieSubjectList = doubleMovieSubjectRepository.findAll();
        assertThat(doubleMovieSubjectList).hasSize(databaseSizeBeforeUpdate);
        DoubleMovieSubject testDoubleMovieSubject = doubleMovieSubjectList.get(doubleMovieSubjectList.size() - 1);
        assertThat(testDoubleMovieSubject.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDoubleMovieSubject.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDoubleMovieSubject.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testDoubleMovieSubject.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testDoubleMovieSubject.getDoubanId()).isEqualTo(UPDATED_DOUBAN_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDoubleMovieSubject() throws Exception {
        int databaseSizeBeforeUpdate = doubleMovieSubjectRepository.findAll().size();

        // Create the DoubleMovieSubject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoubleMovieSubjectMockMvc.perform(put("/api/double-movie-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doubleMovieSubject)))
            .andExpect(status().isCreated());

        // Validate the DoubleMovieSubject in the database
        List<DoubleMovieSubject> doubleMovieSubjectList = doubleMovieSubjectRepository.findAll();
        assertThat(doubleMovieSubjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoubleMovieSubject() throws Exception {
        // Initialize the database
        doubleMovieSubjectService.save(doubleMovieSubject);

        int databaseSizeBeforeDelete = doubleMovieSubjectRepository.findAll().size();

        // Get the doubleMovieSubject
        restDoubleMovieSubjectMockMvc.perform(delete("/api/double-movie-subjects/{id}", doubleMovieSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoubleMovieSubject> doubleMovieSubjectList = doubleMovieSubjectRepository.findAll();
        assertThat(doubleMovieSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
