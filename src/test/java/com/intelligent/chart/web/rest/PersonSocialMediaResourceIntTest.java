package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonSocialMedia;
import com.intelligent.chart.repository.PersonSocialMediaRepository;
import com.intelligent.chart.service.PersonSocialMediaService;

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
 * Test class for the PersonSocialMediaResource REST controller.
 *
 * @see PersonSocialMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonSocialMediaResourceIntTest {

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    @Inject
    private PersonSocialMediaRepository personSocialMediaRepository;

    @Inject
    private PersonSocialMediaService personSocialMediaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonSocialMediaMockMvc;

    private PersonSocialMedia personSocialMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonSocialMediaResource personSocialMediaResource = new PersonSocialMediaResource();
        ReflectionTestUtils.setField(personSocialMediaResource, "personSocialMediaService", personSocialMediaService);
        this.restPersonSocialMediaMockMvc = MockMvcBuilders.standaloneSetup(personSocialMediaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonSocialMedia createEntity(EntityManager em) {
        PersonSocialMedia personSocialMedia = new PersonSocialMedia()
                .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return personSocialMedia;
    }

    @Before
    public void initTest() {
        personSocialMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonSocialMedia() throws Exception {
        int databaseSizeBeforeCreate = personSocialMediaRepository.findAll().size();

        // Create the PersonSocialMedia

        restPersonSocialMediaMockMvc.perform(post("/api/person-social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSocialMedia)))
            .andExpect(status().isCreated());

        // Validate the PersonSocialMedia in the database
        List<PersonSocialMedia> personSocialMediaList = personSocialMediaRepository.findAll();
        assertThat(personSocialMediaList).hasSize(databaseSizeBeforeCreate + 1);
        PersonSocialMedia testPersonSocialMedia = personSocialMediaList.get(personSocialMediaList.size() - 1);
        assertThat(testPersonSocialMedia.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createPersonSocialMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personSocialMediaRepository.findAll().size();

        // Create the PersonSocialMedia with an existing ID
        PersonSocialMedia existingPersonSocialMedia = new PersonSocialMedia();
        existingPersonSocialMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonSocialMediaMockMvc.perform(post("/api/person-social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonSocialMedia)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonSocialMedia> personSocialMediaList = personSocialMediaRepository.findAll();
        assertThat(personSocialMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonSocialMedias() throws Exception {
        // Initialize the database
        personSocialMediaRepository.saveAndFlush(personSocialMedia);

        // Get all the personSocialMediaList
        restPersonSocialMediaMockMvc.perform(get("/api/person-social-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personSocialMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getPersonSocialMedia() throws Exception {
        // Initialize the database
        personSocialMediaRepository.saveAndFlush(personSocialMedia);

        // Get the personSocialMedia
        restPersonSocialMediaMockMvc.perform(get("/api/person-social-medias/{id}", personSocialMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personSocialMedia.getId().intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonSocialMedia() throws Exception {
        // Get the personSocialMedia
        restPersonSocialMediaMockMvc.perform(get("/api/person-social-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonSocialMedia() throws Exception {
        // Initialize the database
        personSocialMediaService.save(personSocialMedia);

        int databaseSizeBeforeUpdate = personSocialMediaRepository.findAll().size();

        // Update the personSocialMedia
        PersonSocialMedia updatedPersonSocialMedia = personSocialMediaRepository.findOne(personSocialMedia.getId());
        updatedPersonSocialMedia
                .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restPersonSocialMediaMockMvc.perform(put("/api/person-social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonSocialMedia)))
            .andExpect(status().isOk());

        // Validate the PersonSocialMedia in the database
        List<PersonSocialMedia> personSocialMediaList = personSocialMediaRepository.findAll();
        assertThat(personSocialMediaList).hasSize(databaseSizeBeforeUpdate);
        PersonSocialMedia testPersonSocialMedia = personSocialMediaList.get(personSocialMediaList.size() - 1);
        assertThat(testPersonSocialMedia.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = personSocialMediaRepository.findAll().size();

        // Create the PersonSocialMedia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonSocialMediaMockMvc.perform(put("/api/person-social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personSocialMedia)))
            .andExpect(status().isCreated());

        // Validate the PersonSocialMedia in the database
        List<PersonSocialMedia> personSocialMediaList = personSocialMediaRepository.findAll();
        assertThat(personSocialMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonSocialMedia() throws Exception {
        // Initialize the database
        personSocialMediaService.save(personSocialMedia);

        int databaseSizeBeforeDelete = personSocialMediaRepository.findAll().size();

        // Get the personSocialMedia
        restPersonSocialMediaMockMvc.perform(delete("/api/person-social-medias/{id}", personSocialMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonSocialMedia> personSocialMediaList = personSocialMediaRepository.findAll();
        assertThat(personSocialMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
