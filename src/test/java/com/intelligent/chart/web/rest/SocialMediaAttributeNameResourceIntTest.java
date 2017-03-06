package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.SocialMediaAttributeName;
import com.intelligent.chart.repository.SocialMediaAttributeNameRepository;
import com.intelligent.chart.service.SocialMediaAttributeNameService;

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
 * Test class for the SocialMediaAttributeNameResource REST controller.
 *
 * @see SocialMediaAttributeNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class SocialMediaAttributeNameResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private SocialMediaAttributeNameRepository socialMediaAttributeNameRepository;

    @Inject
    private SocialMediaAttributeNameService socialMediaAttributeNameService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSocialMediaAttributeNameMockMvc;

    private SocialMediaAttributeName socialMediaAttributeName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SocialMediaAttributeNameResource socialMediaAttributeNameResource = new SocialMediaAttributeNameResource();
        ReflectionTestUtils.setField(socialMediaAttributeNameResource, "socialMediaAttributeNameService", socialMediaAttributeNameService);
        this.restSocialMediaAttributeNameMockMvc = MockMvcBuilders.standaloneSetup(socialMediaAttributeNameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMediaAttributeName createEntity(EntityManager em) {
        SocialMediaAttributeName socialMediaAttributeName = new SocialMediaAttributeName()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return socialMediaAttributeName;
    }

    @Before
    public void initTest() {
        socialMediaAttributeName = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialMediaAttributeName() throws Exception {
        int databaseSizeBeforeCreate = socialMediaAttributeNameRepository.findAll().size();

        // Create the SocialMediaAttributeName

        restSocialMediaAttributeNameMockMvc.perform(post("/api/social-media-attribute-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaAttributeName)))
            .andExpect(status().isCreated());

        // Validate the SocialMediaAttributeName in the database
        List<SocialMediaAttributeName> socialMediaAttributeNameList = socialMediaAttributeNameRepository.findAll();
        assertThat(socialMediaAttributeNameList).hasSize(databaseSizeBeforeCreate + 1);
        SocialMediaAttributeName testSocialMediaAttributeName = socialMediaAttributeNameList.get(socialMediaAttributeNameList.size() - 1);
        assertThat(testSocialMediaAttributeName.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testSocialMediaAttributeName.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSocialMediaAttributeNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialMediaAttributeNameRepository.findAll().size();

        // Create the SocialMediaAttributeName with an existing ID
        SocialMediaAttributeName existingSocialMediaAttributeName = new SocialMediaAttributeName();
        existingSocialMediaAttributeName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMediaAttributeNameMockMvc.perform(post("/api/social-media-attribute-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSocialMediaAttributeName)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SocialMediaAttributeName> socialMediaAttributeNameList = socialMediaAttributeNameRepository.findAll();
        assertThat(socialMediaAttributeNameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialMediaAttributeNames() throws Exception {
        // Initialize the database
        socialMediaAttributeNameRepository.saveAndFlush(socialMediaAttributeName);

        // Get all the socialMediaAttributeNameList
        restSocialMediaAttributeNameMockMvc.perform(get("/api/social-media-attribute-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMediaAttributeName.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSocialMediaAttributeName() throws Exception {
        // Initialize the database
        socialMediaAttributeNameRepository.saveAndFlush(socialMediaAttributeName);

        // Get the socialMediaAttributeName
        restSocialMediaAttributeNameMockMvc.perform(get("/api/social-media-attribute-names/{id}", socialMediaAttributeName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialMediaAttributeName.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialMediaAttributeName() throws Exception {
        // Get the socialMediaAttributeName
        restSocialMediaAttributeNameMockMvc.perform(get("/api/social-media-attribute-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialMediaAttributeName() throws Exception {
        // Initialize the database
        socialMediaAttributeNameService.save(socialMediaAttributeName);

        int databaseSizeBeforeUpdate = socialMediaAttributeNameRepository.findAll().size();

        // Update the socialMediaAttributeName
        SocialMediaAttributeName updatedSocialMediaAttributeName = socialMediaAttributeNameRepository.findOne(socialMediaAttributeName.getId());
        updatedSocialMediaAttributeName
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restSocialMediaAttributeNameMockMvc.perform(put("/api/social-media-attribute-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialMediaAttributeName)))
            .andExpect(status().isOk());

        // Validate the SocialMediaAttributeName in the database
        List<SocialMediaAttributeName> socialMediaAttributeNameList = socialMediaAttributeNameRepository.findAll();
        assertThat(socialMediaAttributeNameList).hasSize(databaseSizeBeforeUpdate);
        SocialMediaAttributeName testSocialMediaAttributeName = socialMediaAttributeNameList.get(socialMediaAttributeNameList.size() - 1);
        assertThat(testSocialMediaAttributeName.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testSocialMediaAttributeName.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialMediaAttributeName() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaAttributeNameRepository.findAll().size();

        // Create the SocialMediaAttributeName

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialMediaAttributeNameMockMvc.perform(put("/api/social-media-attribute-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaAttributeName)))
            .andExpect(status().isCreated());

        // Validate the SocialMediaAttributeName in the database
        List<SocialMediaAttributeName> socialMediaAttributeNameList = socialMediaAttributeNameRepository.findAll();
        assertThat(socialMediaAttributeNameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocialMediaAttributeName() throws Exception {
        // Initialize the database
        socialMediaAttributeNameService.save(socialMediaAttributeName);

        int databaseSizeBeforeDelete = socialMediaAttributeNameRepository.findAll().size();

        // Get the socialMediaAttributeName
        restSocialMediaAttributeNameMockMvc.perform(delete("/api/social-media-attribute-names/{id}", socialMediaAttributeName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialMediaAttributeName> socialMediaAttributeNameList = socialMediaAttributeNameRepository.findAll();
        assertThat(socialMediaAttributeNameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
