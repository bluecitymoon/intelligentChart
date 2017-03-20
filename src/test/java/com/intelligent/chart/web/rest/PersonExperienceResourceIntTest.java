package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonExperience;
import com.intelligent.chart.repository.PersonExperienceRepository;
import com.intelligent.chart.service.PersonExperienceService;

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
 * Test class for the PersonExperienceResource REST controller.
 *
 * @see PersonExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonExperienceResourceIntTest {

    private static final String DEFAULT_EXP_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_EXP_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private PersonExperienceRepository personExperienceRepository;

    @Inject
    private PersonExperienceService personExperienceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonExperienceMockMvc;

    private PersonExperience personExperience;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonExperienceResource personExperienceResource = new PersonExperienceResource();
        ReflectionTestUtils.setField(personExperienceResource, "personExperienceService", personExperienceService);
        this.restPersonExperienceMockMvc = MockMvcBuilders.standaloneSetup(personExperienceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonExperience createEntity(EntityManager em) {
        PersonExperience personExperience = new PersonExperience()
                .expYear(DEFAULT_EXP_YEAR)
                .description(DEFAULT_DESCRIPTION);
        return personExperience;
    }

    @Before
    public void initTest() {
        personExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonExperience() throws Exception {
        int databaseSizeBeforeCreate = personExperienceRepository.findAll().size();

        // Create the PersonExperience

        restPersonExperienceMockMvc.perform(post("/api/person-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personExperience)))
            .andExpect(status().isCreated());

        // Validate the PersonExperience in the database
        List<PersonExperience> personExperienceList = personExperienceRepository.findAll();
        assertThat(personExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        PersonExperience testPersonExperience = personExperienceList.get(personExperienceList.size() - 1);
        assertThat(testPersonExperience.getExpYear()).isEqualTo(DEFAULT_EXP_YEAR);
        assertThat(testPersonExperience.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPersonExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personExperienceRepository.findAll().size();

        // Create the PersonExperience with an existing ID
        PersonExperience existingPersonExperience = new PersonExperience();
        existingPersonExperience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonExperienceMockMvc.perform(post("/api/person-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonExperience)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonExperience> personExperienceList = personExperienceRepository.findAll();
        assertThat(personExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonExperiences() throws Exception {
        // Initialize the database
        personExperienceRepository.saveAndFlush(personExperience);

        // Get all the personExperienceList
        restPersonExperienceMockMvc.perform(get("/api/person-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].expYear").value(hasItem(DEFAULT_EXP_YEAR.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonExperience() throws Exception {
        // Initialize the database
        personExperienceRepository.saveAndFlush(personExperience);

        // Get the personExperience
        restPersonExperienceMockMvc.perform(get("/api/person-experiences/{id}", personExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personExperience.getId().intValue()))
            .andExpect(jsonPath("$.expYear").value(DEFAULT_EXP_YEAR.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonExperience() throws Exception {
        // Get the personExperience
        restPersonExperienceMockMvc.perform(get("/api/person-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonExperience() throws Exception {
        // Initialize the database
        personExperienceService.save(personExperience);

        int databaseSizeBeforeUpdate = personExperienceRepository.findAll().size();

        // Update the personExperience
        PersonExperience updatedPersonExperience = personExperienceRepository.findOne(personExperience.getId());
        updatedPersonExperience
                .expYear(UPDATED_EXP_YEAR)
                .description(UPDATED_DESCRIPTION);

        restPersonExperienceMockMvc.perform(put("/api/person-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonExperience)))
            .andExpect(status().isOk());

        // Validate the PersonExperience in the database
        List<PersonExperience> personExperienceList = personExperienceRepository.findAll();
        assertThat(personExperienceList).hasSize(databaseSizeBeforeUpdate);
        PersonExperience testPersonExperience = personExperienceList.get(personExperienceList.size() - 1);
        assertThat(testPersonExperience.getExpYear()).isEqualTo(UPDATED_EXP_YEAR);
        assertThat(testPersonExperience.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonExperience() throws Exception {
        int databaseSizeBeforeUpdate = personExperienceRepository.findAll().size();

        // Create the PersonExperience

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonExperienceMockMvc.perform(put("/api/person-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personExperience)))
            .andExpect(status().isCreated());

        // Validate the PersonExperience in the database
        List<PersonExperience> personExperienceList = personExperienceRepository.findAll();
        assertThat(personExperienceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonExperience() throws Exception {
        // Initialize the database
        personExperienceService.save(personExperience);

        int databaseSizeBeforeDelete = personExperienceRepository.findAll().size();

        // Get the personExperience
        restPersonExperienceMockMvc.perform(delete("/api/person-experiences/{id}", personExperience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonExperience> personExperienceList = personExperienceRepository.findAll();
        assertThat(personExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
