package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.repository.DoubanMovieTagRepository;
import com.intelligent.chart.service.DoubanMovieTagService;

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
 * Test class for the DoubanMovieTagResource REST controller.
 *
 * @see DoubanMovieTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class DoubanMovieTagResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_PAGE_COUNT = 1;
    private static final Integer UPDATED_MAX_PAGE_COUNT = 2;

    @Inject
    private DoubanMovieTagRepository doubanMovieTagRepository;

    @Inject
    private DoubanMovieTagService doubanMovieTagService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDoubanMovieTagMockMvc;

    private DoubanMovieTag doubanMovieTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoubanMovieTagResource doubanMovieTagResource = new DoubanMovieTagResource();
        ReflectionTestUtils.setField(doubanMovieTagResource, "doubanMovieTagService", doubanMovieTagService);
        this.restDoubanMovieTagMockMvc = MockMvcBuilders.standaloneSetup(doubanMovieTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoubanMovieTag createEntity(EntityManager em) {
        DoubanMovieTag doubanMovieTag = new DoubanMovieTag()
                .name(DEFAULT_NAME)
                .maxPageCount(DEFAULT_MAX_PAGE_COUNT);
        return doubanMovieTag;
    }

    @Before
    public void initTest() {
        doubanMovieTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoubanMovieTag() throws Exception {
        int databaseSizeBeforeCreate = doubanMovieTagRepository.findAll().size();

        // Create the DoubanMovieTag

        restDoubanMovieTagMockMvc.perform(post("/api/douban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doubanMovieTag)))
            .andExpect(status().isCreated());

        // Validate the DoubanMovieTag in the database
        List<DoubanMovieTag> doubanMovieTagList = doubanMovieTagRepository.findAll();
        assertThat(doubanMovieTagList).hasSize(databaseSizeBeforeCreate + 1);
        DoubanMovieTag testDoubanMovieTag = doubanMovieTagList.get(doubanMovieTagList.size() - 1);
        assertThat(testDoubanMovieTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoubanMovieTag.getMaxPageCount()).isEqualTo(DEFAULT_MAX_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void createDoubanMovieTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doubanMovieTagRepository.findAll().size();

        // Create the DoubanMovieTag with an existing ID
        DoubanMovieTag existingDoubanMovieTag = new DoubanMovieTag();
        existingDoubanMovieTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoubanMovieTagMockMvc.perform(post("/api/douban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDoubanMovieTag)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DoubanMovieTag> doubanMovieTagList = doubanMovieTagRepository.findAll();
        assertThat(doubanMovieTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoubanMovieTags() throws Exception {
        // Initialize the database
        doubanMovieTagRepository.saveAndFlush(doubanMovieTag);

        // Get all the doubanMovieTagList
        restDoubanMovieTagMockMvc.perform(get("/api/douban-movie-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doubanMovieTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].maxPageCount").value(hasItem(DEFAULT_MAX_PAGE_COUNT)));
    }

    @Test
    @Transactional
    public void getDoubanMovieTag() throws Exception {
        // Initialize the database
        doubanMovieTagRepository.saveAndFlush(doubanMovieTag);

        // Get the doubanMovieTag
        restDoubanMovieTagMockMvc.perform(get("/api/douban-movie-tags/{id}", doubanMovieTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doubanMovieTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.maxPageCount").value(DEFAULT_MAX_PAGE_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingDoubanMovieTag() throws Exception {
        // Get the doubanMovieTag
        restDoubanMovieTagMockMvc.perform(get("/api/douban-movie-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoubanMovieTag() throws Exception {
        // Initialize the database
        doubanMovieTagService.save(doubanMovieTag);

        int databaseSizeBeforeUpdate = doubanMovieTagRepository.findAll().size();

        // Update the doubanMovieTag
        DoubanMovieTag updatedDoubanMovieTag = doubanMovieTagRepository.findOne(doubanMovieTag.getId());
        updatedDoubanMovieTag
                .name(UPDATED_NAME)
                .maxPageCount(UPDATED_MAX_PAGE_COUNT);

        restDoubanMovieTagMockMvc.perform(put("/api/douban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoubanMovieTag)))
            .andExpect(status().isOk());

        // Validate the DoubanMovieTag in the database
        List<DoubanMovieTag> doubanMovieTagList = doubanMovieTagRepository.findAll();
        assertThat(doubanMovieTagList).hasSize(databaseSizeBeforeUpdate);
        DoubanMovieTag testDoubanMovieTag = doubanMovieTagList.get(doubanMovieTagList.size() - 1);
        assertThat(testDoubanMovieTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoubanMovieTag.getMaxPageCount()).isEqualTo(UPDATED_MAX_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingDoubanMovieTag() throws Exception {
        int databaseSizeBeforeUpdate = doubanMovieTagRepository.findAll().size();

        // Create the DoubanMovieTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoubanMovieTagMockMvc.perform(put("/api/douban-movie-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doubanMovieTag)))
            .andExpect(status().isCreated());

        // Validate the DoubanMovieTag in the database
        List<DoubanMovieTag> doubanMovieTagList = doubanMovieTagRepository.findAll();
        assertThat(doubanMovieTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoubanMovieTag() throws Exception {
        // Initialize the database
        doubanMovieTagService.save(doubanMovieTag);

        int databaseSizeBeforeDelete = doubanMovieTagRepository.findAll().size();

        // Get the doubanMovieTag
        restDoubanMovieTagMockMvc.perform(delete("/api/douban-movie-tags/{id}", doubanMovieTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoubanMovieTag> doubanMovieTagList = doubanMovieTagRepository.findAll();
        assertThat(doubanMovieTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
