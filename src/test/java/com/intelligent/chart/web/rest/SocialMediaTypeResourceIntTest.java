package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.SocialMediaType;
import com.intelligent.chart.repository.SocialMediaTypeRepository;
import com.intelligent.chart.service.SocialMediaTypeService;

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

import com.intelligent.chart.domain.enumeration.PanelStyle;
/**
 * Test class for the SocialMediaTypeResource REST controller.
 *
 * @see SocialMediaTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class SocialMediaTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PanelStyle DEFAULT_STYLE = PanelStyle.danger;
    private static final PanelStyle UPDATED_STYLE = PanelStyle.dark;

    @Inject
    private SocialMediaTypeRepository socialMediaTypeRepository;

    @Inject
    private SocialMediaTypeService socialMediaTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSocialMediaTypeMockMvc;

    private SocialMediaType socialMediaType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SocialMediaTypeResource socialMediaTypeResource = new SocialMediaTypeResource();
        ReflectionTestUtils.setField(socialMediaTypeResource, "socialMediaTypeService", socialMediaTypeService);
        this.restSocialMediaTypeMockMvc = MockMvcBuilders.standaloneSetup(socialMediaTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMediaType createEntity(EntityManager em) {
        SocialMediaType socialMediaType = new SocialMediaType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME)
                .style(DEFAULT_STYLE);
        return socialMediaType;
    }

    @Before
    public void initTest() {
        socialMediaType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialMediaType() throws Exception {
        int databaseSizeBeforeCreate = socialMediaTypeRepository.findAll().size();

        // Create the SocialMediaType

        restSocialMediaTypeMockMvc.perform(post("/api/social-media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaType)))
            .andExpect(status().isCreated());

        // Validate the SocialMediaType in the database
        List<SocialMediaType> socialMediaTypeList = socialMediaTypeRepository.findAll();
        assertThat(socialMediaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SocialMediaType testSocialMediaType = socialMediaTypeList.get(socialMediaTypeList.size() - 1);
        assertThat(testSocialMediaType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testSocialMediaType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialMediaType.getStyle()).isEqualTo(DEFAULT_STYLE);
    }

    @Test
    @Transactional
    public void createSocialMediaTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialMediaTypeRepository.findAll().size();

        // Create the SocialMediaType with an existing ID
        SocialMediaType existingSocialMediaType = new SocialMediaType();
        existingSocialMediaType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMediaTypeMockMvc.perform(post("/api/social-media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSocialMediaType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SocialMediaType> socialMediaTypeList = socialMediaTypeRepository.findAll();
        assertThat(socialMediaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialMediaTypes() throws Exception {
        // Initialize the database
        socialMediaTypeRepository.saveAndFlush(socialMediaType);

        // Get all the socialMediaTypeList
        restSocialMediaTypeMockMvc.perform(get("/api/social-media-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMediaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));
    }

    @Test
    @Transactional
    public void getSocialMediaType() throws Exception {
        // Initialize the database
        socialMediaTypeRepository.saveAndFlush(socialMediaType);

        // Get the socialMediaType
        restSocialMediaTypeMockMvc.perform(get("/api/social-media-types/{id}", socialMediaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialMediaType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialMediaType() throws Exception {
        // Get the socialMediaType
        restSocialMediaTypeMockMvc.perform(get("/api/social-media-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialMediaType() throws Exception {
        // Initialize the database
        socialMediaTypeService.save(socialMediaType);

        int databaseSizeBeforeUpdate = socialMediaTypeRepository.findAll().size();

        // Update the socialMediaType
        SocialMediaType updatedSocialMediaType = socialMediaTypeRepository.findOne(socialMediaType.getId());
        updatedSocialMediaType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME)
                .style(UPDATED_STYLE);

        restSocialMediaTypeMockMvc.perform(put("/api/social-media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialMediaType)))
            .andExpect(status().isOk());

        // Validate the SocialMediaType in the database
        List<SocialMediaType> socialMediaTypeList = socialMediaTypeRepository.findAll();
        assertThat(socialMediaTypeList).hasSize(databaseSizeBeforeUpdate);
        SocialMediaType testSocialMediaType = socialMediaTypeList.get(socialMediaTypeList.size() - 1);
        assertThat(testSocialMediaType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testSocialMediaType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialMediaType.getStyle()).isEqualTo(UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialMediaType() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaTypeRepository.findAll().size();

        // Create the SocialMediaType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialMediaTypeMockMvc.perform(put("/api/social-media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaType)))
            .andExpect(status().isCreated());

        // Validate the SocialMediaType in the database
        List<SocialMediaType> socialMediaTypeList = socialMediaTypeRepository.findAll();
        assertThat(socialMediaTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocialMediaType() throws Exception {
        // Initialize the database
        socialMediaTypeService.save(socialMediaType);

        int databaseSizeBeforeDelete = socialMediaTypeRepository.findAll().size();

        // Get the socialMediaType
        restSocialMediaTypeMockMvc.perform(delete("/api/social-media-types/{id}", socialMediaType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialMediaType> socialMediaTypeList = socialMediaTypeRepository.findAll();
        assertThat(socialMediaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
