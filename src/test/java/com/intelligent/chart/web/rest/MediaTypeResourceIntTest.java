package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.MediaType;
import com.intelligent.chart.repository.MediaTypeRepository;
import com.intelligent.chart.service.MediaTypeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
 * Test class for the MediaTypeResource REST controller.
 *
 * @see MediaTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MediaTypeResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private MediaTypeRepository mediaTypeRepository;

    @Inject
    private MediaTypeService mediaTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMediaTypeMockMvc;

    private com.intelligent.chart.domain.MediaType mediaType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MediaTypeResource mediaTypeResource = new MediaTypeResource();
        ReflectionTestUtils.setField(mediaTypeResource, "mediaTypeService", mediaTypeService);
        this.restMediaTypeMockMvc = MockMvcBuilders.standaloneSetup(mediaTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static com.intelligent.chart.domain.MediaType createEntity(EntityManager em) {
        com.intelligent.chart.domain.MediaType mediaType = new com.intelligent.chart.domain.MediaType()
                .identifier(DEFAULT_IDENTIFIER)
                .name(DEFAULT_NAME);
        return mediaType;
    }

    @Before
    public void initTest() {
        mediaType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaType() throws Exception {
        int databaseSizeBeforeCreate = mediaTypeRepository.findAll().size();

        // Create the MediaType

        restMediaTypeMockMvc.perform(post("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isCreated());

        // Validate the MediaType in the database
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MediaType testMediaType = mediaTypeList.get(mediaTypeList.size() - 1);
        assertThat(testMediaType.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testMediaType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMediaTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaTypeRepository.findAll().size();

        // Create the MediaType with an existing ID
        MediaType existingMediaType = new MediaType();
        existingMediaType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaTypeMockMvc.perform(post("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMediaType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMediaTypes() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);

        // Get all the mediaTypeList
        restMediaTypeMockMvc.perform(get("/api/media-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMediaType() throws Exception {
        // Initialize the database
        mediaTypeRepository.saveAndFlush(mediaType);

        // Get the mediaType
        restMediaTypeMockMvc.perform(get("/api/media-types/{id}", mediaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mediaType.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaType() throws Exception {
        // Get the mediaType
        restMediaTypeMockMvc.perform(get("/api/media-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaType() throws Exception {
        // Initialize the database
        mediaTypeService.save(mediaType);

        int databaseSizeBeforeUpdate = mediaTypeRepository.findAll().size();

        // Update the mediaType
        MediaType updatedMediaType = mediaTypeRepository.findOne(mediaType.getId());
        updatedMediaType
                .identifier(UPDATED_IDENTIFIER)
                .name(UPDATED_NAME);

        restMediaTypeMockMvc.perform(put("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMediaType)))
            .andExpect(status().isOk());

        // Validate the MediaType in the database
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeUpdate);
        MediaType testMediaType = mediaTypeList.get(mediaTypeList.size() - 1);
        assertThat(testMediaType.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testMediaType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaType() throws Exception {
        int databaseSizeBeforeUpdate = mediaTypeRepository.findAll().size();

        // Create the MediaType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaTypeMockMvc.perform(put("/api/media-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaType)))
            .andExpect(status().isCreated());

        // Validate the MediaType in the database
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMediaType() throws Exception {
        // Initialize the database
        mediaTypeService.save(mediaType);

        int databaseSizeBeforeDelete = mediaTypeRepository.findAll().size();

        // Get the mediaType
        restMediaTypeMockMvc.perform(delete("/api/media-types/{id}", mediaType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MediaType> mediaTypeList = mediaTypeRepository.findAll();
        assertThat(mediaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
