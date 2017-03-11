package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonSocialHotDiscuss;
import com.intelligent.chart.repository.PersonSocialHotDiscussRepository;
import com.intelligent.chart.service.PersonSocialHotDiscussService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonSocialHotDiscussResource REST controller.
 *
 * @see PersonSocialHotDiscussResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonSocialHotDiscussResourceIntTest {

    private static final String DEFAULT_ARTICLE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ARTICLE_COUNT = 1;
    private static final Integer UPDATED_ARTICLE_COUNT = 2;

    @Inject
    private PersonSocialHotDiscussRepository personSocialHotDiscussRepository;

    @Inject
    private PersonSocialHotDiscussService personSocialHotDiscussService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonSocialHotDiscussMockMvc;

    private PersonSocialHotDiscuss personSocialHotDiscuss;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonSocialHotDiscussResource personSocialHotDiscussResource = new PersonSocialHotDiscussResource();
        ReflectionTestUtils.setField(personSocialHotDiscussResource, "personSocialHotDiscussService", personSocialHotDiscussService);
        this.restPersonSocialHotDiscussMockMvc = MockMvcBuilders.standaloneSetup(personSocialHotDiscussResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonSocialHotDiscuss createEntity(EntityManager em) {
        PersonSocialHotDiscuss personSocialHotDiscuss = new PersonSocialHotDiscuss()
                .articleTitle(DEFAULT_ARTICLE_TITLE)
                .mediaName(DEFAULT_MEDIA_NAME)
                .createDate(DEFAULT_CREATE_DATE)
                .articleCount(DEFAULT_ARTICLE_COUNT);
        return personSocialHotDiscuss;
    }

    @Before
    public void initTest() {
        personSocialHotDiscuss = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonSocialHotDiscuss() throws Exception {
        int databaseSizeBeforeCreate = personSocialHotDiscussRepository.findAll().size();

        // Create the PersonSocialHotDiscuss

        restPersonSocialHotDiscussMockMvc.perform(post("/api/person-social-hot-discusses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSocialHotDiscuss)))
            .andExpect(status().isCreated());

        // Validate the PersonSocialHotDiscuss in the database
        List<PersonSocialHotDiscuss> personSocialHotDiscussList = personSocialHotDiscussRepository.findAll();
        assertThat(personSocialHotDiscussList).hasSize(databaseSizeBeforeCreate + 1);
        PersonSocialHotDiscuss testPersonSocialHotDiscuss = personSocialHotDiscussList.get(personSocialHotDiscussList.size() - 1);
        assertThat(testPersonSocialHotDiscuss.getArticleTitle()).isEqualTo(DEFAULT_ARTICLE_TITLE);
        assertThat(testPersonSocialHotDiscuss.getMediaName()).isEqualTo(DEFAULT_MEDIA_NAME);
        assertThat(testPersonSocialHotDiscuss.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonSocialHotDiscuss.getArticleCount()).isEqualTo(DEFAULT_ARTICLE_COUNT);
    }

    @Test
    @Transactional
    public void createPersonSocialHotDiscussWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personSocialHotDiscussRepository.findAll().size();

        // Create the PersonSocialHotDiscuss with an existing ID
        PersonSocialHotDiscuss existingPersonSocialHotDiscuss = new PersonSocialHotDiscuss();
        existingPersonSocialHotDiscuss.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonSocialHotDiscussMockMvc.perform(post("/api/person-social-hot-discusses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonSocialHotDiscuss)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonSocialHotDiscuss> personSocialHotDiscussList = personSocialHotDiscussRepository.findAll();
        assertThat(personSocialHotDiscussList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonSocialHotDiscusses() throws Exception {
        // Initialize the database
        personSocialHotDiscussRepository.saveAndFlush(personSocialHotDiscuss);

        // Get all the personSocialHotDiscussList
        restPersonSocialHotDiscussMockMvc.perform(get("/api/person-social-hot-discusses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personSocialHotDiscuss.getId().intValue())))
            .andExpect(jsonPath("$.[*].articleTitle").value(hasItem(DEFAULT_ARTICLE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].mediaName").value(hasItem(DEFAULT_MEDIA_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].articleCount").value(hasItem(DEFAULT_ARTICLE_COUNT)));
    }

    @Test
    @Transactional
    public void getPersonSocialHotDiscuss() throws Exception {
        // Initialize the database
        personSocialHotDiscussRepository.saveAndFlush(personSocialHotDiscuss);

        // Get the personSocialHotDiscuss
        restPersonSocialHotDiscussMockMvc.perform(get("/api/person-social-hot-discusses/{id}", personSocialHotDiscuss.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personSocialHotDiscuss.getId().intValue()))
            .andExpect(jsonPath("$.articleTitle").value(DEFAULT_ARTICLE_TITLE.toString()))
            .andExpect(jsonPath("$.mediaName").value(DEFAULT_MEDIA_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.articleCount").value(DEFAULT_ARTICLE_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPersonSocialHotDiscuss() throws Exception {
        // Get the personSocialHotDiscuss
        restPersonSocialHotDiscussMockMvc.perform(get("/api/person-social-hot-discusses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonSocialHotDiscuss() throws Exception {
        // Initialize the database
        personSocialHotDiscussService.save(personSocialHotDiscuss);

        int databaseSizeBeforeUpdate = personSocialHotDiscussRepository.findAll().size();

        // Update the personSocialHotDiscuss
        PersonSocialHotDiscuss updatedPersonSocialHotDiscuss = personSocialHotDiscussRepository.findOne(personSocialHotDiscuss.getId());
        updatedPersonSocialHotDiscuss
                .articleTitle(UPDATED_ARTICLE_TITLE)
                .mediaName(UPDATED_MEDIA_NAME)
                .createDate(UPDATED_CREATE_DATE)
                .articleCount(UPDATED_ARTICLE_COUNT);

        restPersonSocialHotDiscussMockMvc.perform(put("/api/person-social-hot-discusses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonSocialHotDiscuss)))
            .andExpect(status().isOk());

        // Validate the PersonSocialHotDiscuss in the database
        List<PersonSocialHotDiscuss> personSocialHotDiscussList = personSocialHotDiscussRepository.findAll();
        assertThat(personSocialHotDiscussList).hasSize(databaseSizeBeforeUpdate);
        PersonSocialHotDiscuss testPersonSocialHotDiscuss = personSocialHotDiscussList.get(personSocialHotDiscussList.size() - 1);
        assertThat(testPersonSocialHotDiscuss.getArticleTitle()).isEqualTo(UPDATED_ARTICLE_TITLE);
        assertThat(testPersonSocialHotDiscuss.getMediaName()).isEqualTo(UPDATED_MEDIA_NAME);
        assertThat(testPersonSocialHotDiscuss.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonSocialHotDiscuss.getArticleCount()).isEqualTo(UPDATED_ARTICLE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonSocialHotDiscuss() throws Exception {
        int databaseSizeBeforeUpdate = personSocialHotDiscussRepository.findAll().size();

        // Create the PersonSocialHotDiscuss

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonSocialHotDiscussMockMvc.perform(put("/api/person-social-hot-discusses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSocialHotDiscuss)))
            .andExpect(status().isCreated());

        // Validate the PersonSocialHotDiscuss in the database
        List<PersonSocialHotDiscuss> personSocialHotDiscussList = personSocialHotDiscussRepository.findAll();
        assertThat(personSocialHotDiscussList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonSocialHotDiscuss() throws Exception {
        // Initialize the database
        personSocialHotDiscussService.save(personSocialHotDiscuss);

        int databaseSizeBeforeDelete = personSocialHotDiscussRepository.findAll().size();

        // Get the personSocialHotDiscuss
        restPersonSocialHotDiscussMockMvc.perform(delete("/api/person-social-hot-discusses/{id}", personSocialHotDiscuss.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonSocialHotDiscuss> personSocialHotDiscussList = personSocialHotDiscussRepository.findAll();
        assertThat(personSocialHotDiscussList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
