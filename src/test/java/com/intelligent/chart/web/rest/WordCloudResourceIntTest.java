package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.WordCloud;
import com.intelligent.chart.repository.WordCloudRepository;
import com.intelligent.chart.service.WordCloudService;

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
 * Test class for the WordCloudResource REST controller.
 *
 * @see WordCloudResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class WordCloudResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private WordCloudRepository wordCloudRepository;

    @Inject
    private WordCloudService wordCloudService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWordCloudMockMvc;

    private WordCloud wordCloud;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WordCloudResource wordCloudResource = new WordCloudResource();
        ReflectionTestUtils.setField(wordCloudResource, "wordCloudService", wordCloudService);
        this.restWordCloudMockMvc = MockMvcBuilders.standaloneSetup(wordCloudResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordCloud createEntity(EntityManager em) {
        WordCloud wordCloud = new WordCloud()
                .name(DEFAULT_NAME);
        return wordCloud;
    }

    @Before
    public void initTest() {
        wordCloud = createEntity(em);
    }

    @Test
    @Transactional
    public void createWordCloud() throws Exception {
        int databaseSizeBeforeCreate = wordCloudRepository.findAll().size();

        // Create the WordCloud

        restWordCloudMockMvc.perform(post("/api/word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordCloud)))
            .andExpect(status().isCreated());

        // Validate the WordCloud in the database
        List<WordCloud> wordCloudList = wordCloudRepository.findAll();
        assertThat(wordCloudList).hasSize(databaseSizeBeforeCreate + 1);
        WordCloud testWordCloud = wordCloudList.get(wordCloudList.size() - 1);
        assertThat(testWordCloud.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWordCloudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordCloudRepository.findAll().size();

        // Create the WordCloud with an existing ID
        WordCloud existingWordCloud = new WordCloud();
        existingWordCloud.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordCloudMockMvc.perform(post("/api/word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWordCloud)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WordCloud> wordCloudList = wordCloudRepository.findAll();
        assertThat(wordCloudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWordClouds() throws Exception {
        // Initialize the database
        wordCloudRepository.saveAndFlush(wordCloud);

        // Get all the wordCloudList
        restWordCloudMockMvc.perform(get("/api/word-clouds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordCloud.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWordCloud() throws Exception {
        // Initialize the database
        wordCloudRepository.saveAndFlush(wordCloud);

        // Get the wordCloud
        restWordCloudMockMvc.perform(get("/api/word-clouds/{id}", wordCloud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wordCloud.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWordCloud() throws Exception {
        // Get the wordCloud
        restWordCloudMockMvc.perform(get("/api/word-clouds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWordCloud() throws Exception {
        // Initialize the database
        wordCloudService.save(wordCloud);

        int databaseSizeBeforeUpdate = wordCloudRepository.findAll().size();

        // Update the wordCloud
        WordCloud updatedWordCloud = wordCloudRepository.findOne(wordCloud.getId());
        updatedWordCloud
                .name(UPDATED_NAME);

        restWordCloudMockMvc.perform(put("/api/word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWordCloud)))
            .andExpect(status().isOk());

        // Validate the WordCloud in the database
        List<WordCloud> wordCloudList = wordCloudRepository.findAll();
        assertThat(wordCloudList).hasSize(databaseSizeBeforeUpdate);
        WordCloud testWordCloud = wordCloudList.get(wordCloudList.size() - 1);
        assertThat(testWordCloud.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWordCloud() throws Exception {
        int databaseSizeBeforeUpdate = wordCloudRepository.findAll().size();

        // Create the WordCloud

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWordCloudMockMvc.perform(put("/api/word-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordCloud)))
            .andExpect(status().isCreated());

        // Validate the WordCloud in the database
        List<WordCloud> wordCloudList = wordCloudRepository.findAll();
        assertThat(wordCloudList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWordCloud() throws Exception {
        // Initialize the database
        wordCloudService.save(wordCloud);

        int databaseSizeBeforeDelete = wordCloudRepository.findAll().size();

        // Get the wordCloud
        restWordCloudMockMvc.perform(delete("/api/word-clouds/{id}", wordCloud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WordCloud> wordCloudList = wordCloudRepository.findAll();
        assertThat(wordCloudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
