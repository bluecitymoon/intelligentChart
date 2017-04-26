package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;
import com.intelligent.chart.domain.WebClientCookie;
import com.intelligent.chart.repository.WebClientCookieRepository;
import com.intelligent.chart.service.WebClientCookieService;
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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WebClientCookieResource REST controller.
 *
 * @see WebClientCookieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class WebClientCookieResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SECURE = false;
    private static final Boolean UPDATED_SECURE = true;

    private static final Boolean DEFAULT_HTTP_ONLY = false;
    private static final Boolean UPDATED_HTTP_ONLY = true;

    private static final Date DEFAULT_EXPIRES = new Date();
    private static final Date UPDATED_EXPIRES = new Date();

    @Inject
    private WebClientCookieRepository webClientCookieRepository;

    @Inject
    private WebClientCookieService webClientCookieService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWebClientCookieMockMvc;

    private WebClientCookie webClientCookie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebClientCookieResource webClientCookieResource = new WebClientCookieResource();
        ReflectionTestUtils.setField(webClientCookieResource, "webClientCookieService", webClientCookieService);
        this.restWebClientCookieMockMvc = MockMvcBuilders.standaloneSetup(webClientCookieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebClientCookie createEntity(EntityManager em) {
        WebClientCookie webClientCookie = new WebClientCookie()
                .name(DEFAULT_NAME)
                .value(DEFAULT_VALUE)
                .domain(DEFAULT_DOMAIN)
                .path(DEFAULT_PATH)
                .secure(DEFAULT_SECURE)
                .httpOnly(DEFAULT_HTTP_ONLY)
                .expires(DEFAULT_EXPIRES);
        return webClientCookie;
    }

    @Before
    public void initTest() {
        webClientCookie = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebClientCookie() throws Exception {
        int databaseSizeBeforeCreate = webClientCookieRepository.findAll().size();

        // Create the WebClientCookie

        restWebClientCookieMockMvc.perform(post("/api/web-client-cookies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webClientCookie)))
            .andExpect(status().isCreated());

        // Validate the WebClientCookie in the database
        List<WebClientCookie> webClientCookieList = webClientCookieRepository.findAll();
        assertThat(webClientCookieList).hasSize(databaseSizeBeforeCreate + 1);
        WebClientCookie testWebClientCookie = webClientCookieList.get(webClientCookieList.size() - 1);
        assertThat(testWebClientCookie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebClientCookie.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWebClientCookie.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testWebClientCookie.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testWebClientCookie.isSecure()).isEqualTo(DEFAULT_SECURE);
        assertThat(testWebClientCookie.isHttpOnly()).isEqualTo(DEFAULT_HTTP_ONLY);
        assertThat(testWebClientCookie.getExpires()).isEqualTo(DEFAULT_EXPIRES);
    }

    @Test
    @Transactional
    public void createWebClientCookieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webClientCookieRepository.findAll().size();

        // Create the WebClientCookie with an existing ID
        WebClientCookie existingWebClientCookie = new WebClientCookie();
        existingWebClientCookie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebClientCookieMockMvc.perform(post("/api/web-client-cookies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWebClientCookie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebClientCookie> webClientCookieList = webClientCookieRepository.findAll();
        assertThat(webClientCookieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebClientCookies() throws Exception {
        // Initialize the database
        webClientCookieRepository.saveAndFlush(webClientCookie);

        // Get all the webClientCookieList
        restWebClientCookieMockMvc.perform(get("/api/web-client-cookies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webClientCookie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].secure").value(hasItem(DEFAULT_SECURE.booleanValue())))
            .andExpect(jsonPath("$.[*].httpOnly").value(hasItem(DEFAULT_HTTP_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].expires").value(hasItem(DEFAULT_EXPIRES.toString())));
    }

    @Test
    @Transactional
    public void getWebClientCookie() throws Exception {
        // Initialize the database
        webClientCookieRepository.saveAndFlush(webClientCookie);

        // Get the webClientCookie
        restWebClientCookieMockMvc.perform(get("/api/web-client-cookies/{id}", webClientCookie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webClientCookie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.secure").value(DEFAULT_SECURE.booleanValue()))
            .andExpect(jsonPath("$.httpOnly").value(DEFAULT_HTTP_ONLY.booleanValue()))
            .andExpect(jsonPath("$.expires").value(DEFAULT_EXPIRES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebClientCookie() throws Exception {
        // Get the webClientCookie
        restWebClientCookieMockMvc.perform(get("/api/web-client-cookies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebClientCookie() throws Exception {
        // Initialize the database
        webClientCookieService.save(webClientCookie);

        int databaseSizeBeforeUpdate = webClientCookieRepository.findAll().size();

        // Update the webClientCookie
        WebClientCookie updatedWebClientCookie = webClientCookieRepository.findOne(webClientCookie.getId());
        updatedWebClientCookie
                .name(UPDATED_NAME)
                .value(UPDATED_VALUE)
                .domain(UPDATED_DOMAIN)
                .path(UPDATED_PATH)
                .secure(UPDATED_SECURE)
                .httpOnly(UPDATED_HTTP_ONLY)
                .expires(UPDATED_EXPIRES);

        restWebClientCookieMockMvc.perform(put("/api/web-client-cookies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebClientCookie)))
            .andExpect(status().isOk());

        // Validate the WebClientCookie in the database
        List<WebClientCookie> webClientCookieList = webClientCookieRepository.findAll();
        assertThat(webClientCookieList).hasSize(databaseSizeBeforeUpdate);
        WebClientCookie testWebClientCookie = webClientCookieList.get(webClientCookieList.size() - 1);
        assertThat(testWebClientCookie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebClientCookie.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWebClientCookie.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testWebClientCookie.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testWebClientCookie.isSecure()).isEqualTo(UPDATED_SECURE);
        assertThat(testWebClientCookie.isHttpOnly()).isEqualTo(UPDATED_HTTP_ONLY);
        assertThat(testWebClientCookie.getExpires()).isEqualTo(UPDATED_EXPIRES);
    }

    @Test
    @Transactional
    public void updateNonExistingWebClientCookie() throws Exception {
        int databaseSizeBeforeUpdate = webClientCookieRepository.findAll().size();

        // Create the WebClientCookie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebClientCookieMockMvc.perform(put("/api/web-client-cookies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webClientCookie)))
            .andExpect(status().isCreated());

        // Validate the WebClientCookie in the database
        List<WebClientCookie> webClientCookieList = webClientCookieRepository.findAll();
        assertThat(webClientCookieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebClientCookie() throws Exception {
        // Initialize the database
        webClientCookieService.save(webClientCookie);

        int databaseSizeBeforeDelete = webClientCookieRepository.findAll().size();

        // Get the webClientCookie
        restWebClientCookieMockMvc.perform(delete("/api/web-client-cookies/{id}", webClientCookie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WebClientCookie> webClientCookieList = webClientCookieRepository.findAll();
        assertThat(webClientCookieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
