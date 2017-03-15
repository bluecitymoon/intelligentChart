package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonWechatArticle;
import com.intelligent.chart.repository.PersonWechatArticleRepository;
import com.intelligent.chart.service.PersonWechatArticleService;

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
 * Test class for the PersonWechatArticleResource REST controller.
 *
 * @see PersonWechatArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonWechatArticleResourceIntTest {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    private static final Integer DEFAULT_INCREAD_BY_LAST_MONTH = 1;
    private static final Integer UPDATED_INCREAD_BY_LAST_MONTH = 2;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PersonWechatArticleRepository personWechatArticleRepository;

    @Inject
    private PersonWechatArticleService personWechatArticleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonWechatArticleMockMvc;

    private PersonWechatArticle personWechatArticle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonWechatArticleResource personWechatArticleResource = new PersonWechatArticleResource();
        ReflectionTestUtils.setField(personWechatArticleResource, "personWechatArticleService", personWechatArticleService);
        this.restPersonWechatArticleMockMvc = MockMvcBuilders.standaloneSetup(personWechatArticleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonWechatArticle createEntity(EntityManager em) {
        PersonWechatArticle personWechatArticle = new PersonWechatArticle()
                .count(DEFAULT_COUNT)
                .increadByLastMonth(DEFAULT_INCREAD_BY_LAST_MONTH)
                .createDate(DEFAULT_CREATE_DATE);
        return personWechatArticle;
    }

    @Before
    public void initTest() {
        personWechatArticle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonWechatArticle() throws Exception {
        int databaseSizeBeforeCreate = personWechatArticleRepository.findAll().size();

        // Create the PersonWechatArticle

        restPersonWechatArticleMockMvc.perform(post("/api/person-wechat-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personWechatArticle)))
            .andExpect(status().isCreated());

        // Validate the PersonWechatArticle in the database
        List<PersonWechatArticle> personWechatArticleList = personWechatArticleRepository.findAll();
        assertThat(personWechatArticleList).hasSize(databaseSizeBeforeCreate + 1);
        PersonWechatArticle testPersonWechatArticle = personWechatArticleList.get(personWechatArticleList.size() - 1);
        assertThat(testPersonWechatArticle.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testPersonWechatArticle.getIncreadByLastMonth()).isEqualTo(DEFAULT_INCREAD_BY_LAST_MONTH);
        assertThat(testPersonWechatArticle.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createPersonWechatArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personWechatArticleRepository.findAll().size();

        // Create the PersonWechatArticle with an existing ID
        PersonWechatArticle existingPersonWechatArticle = new PersonWechatArticle();
        existingPersonWechatArticle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonWechatArticleMockMvc.perform(post("/api/person-wechat-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonWechatArticle)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonWechatArticle> personWechatArticleList = personWechatArticleRepository.findAll();
        assertThat(personWechatArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonWechatArticles() throws Exception {
        // Initialize the database
        personWechatArticleRepository.saveAndFlush(personWechatArticle);

        // Get all the personWechatArticleList
        restPersonWechatArticleMockMvc.perform(get("/api/person-wechat-articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personWechatArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].increadByLastMonth").value(hasItem(DEFAULT_INCREAD_BY_LAST_MONTH)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPersonWechatArticle() throws Exception {
        // Initialize the database
        personWechatArticleRepository.saveAndFlush(personWechatArticle);

        // Get the personWechatArticle
        restPersonWechatArticleMockMvc.perform(get("/api/person-wechat-articles/{id}", personWechatArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personWechatArticle.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()))
            .andExpect(jsonPath("$.increadByLastMonth").value(DEFAULT_INCREAD_BY_LAST_MONTH))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonWechatArticle() throws Exception {
        // Get the personWechatArticle
        restPersonWechatArticleMockMvc.perform(get("/api/person-wechat-articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonWechatArticle() throws Exception {
        // Initialize the database
        personWechatArticleService.save(personWechatArticle);

        int databaseSizeBeforeUpdate = personWechatArticleRepository.findAll().size();

        // Update the personWechatArticle
        PersonWechatArticle updatedPersonWechatArticle = personWechatArticleRepository.findOne(personWechatArticle.getId());
        updatedPersonWechatArticle
                .count(UPDATED_COUNT)
                .increadByLastMonth(UPDATED_INCREAD_BY_LAST_MONTH)
                .createDate(UPDATED_CREATE_DATE);

        restPersonWechatArticleMockMvc.perform(put("/api/person-wechat-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonWechatArticle)))
            .andExpect(status().isOk());

        // Validate the PersonWechatArticle in the database
        List<PersonWechatArticle> personWechatArticleList = personWechatArticleRepository.findAll();
        assertThat(personWechatArticleList).hasSize(databaseSizeBeforeUpdate);
        PersonWechatArticle testPersonWechatArticle = personWechatArticleList.get(personWechatArticleList.size() - 1);
        assertThat(testPersonWechatArticle.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testPersonWechatArticle.getIncreadByLastMonth()).isEqualTo(UPDATED_INCREAD_BY_LAST_MONTH);
        assertThat(testPersonWechatArticle.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonWechatArticle() throws Exception {
        int databaseSizeBeforeUpdate = personWechatArticleRepository.findAll().size();

        // Create the PersonWechatArticle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonWechatArticleMockMvc.perform(put("/api/person-wechat-articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personWechatArticle)))
            .andExpect(status().isCreated());

        // Validate the PersonWechatArticle in the database
        List<PersonWechatArticle> personWechatArticleList = personWechatArticleRepository.findAll();
        assertThat(personWechatArticleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonWechatArticle() throws Exception {
        // Initialize the database
        personWechatArticleService.save(personWechatArticle);

        int databaseSizeBeforeDelete = personWechatArticleRepository.findAll().size();

        // Get the personWechatArticle
        restPersonWechatArticleMockMvc.perform(delete("/api/person-wechat-articles/{id}", personWechatArticle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonWechatArticle> personWechatArticleList = personWechatArticleRepository.findAll();
        assertThat(personWechatArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
