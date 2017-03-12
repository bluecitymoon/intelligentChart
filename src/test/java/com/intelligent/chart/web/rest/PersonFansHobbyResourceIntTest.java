package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFansHobby;
import com.intelligent.chart.repository.PersonFansHobbyRepository;
import com.intelligent.chart.service.PersonFansHobbyService;

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
 * Test class for the PersonFansHobbyResource REST controller.
 *
 * @see PersonFansHobbyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFansHobbyResourceIntTest {

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    @Inject
    private PersonFansHobbyRepository personFansHobbyRepository;

    @Inject
    private PersonFansHobbyService personFansHobbyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFansHobbyMockMvc;

    private PersonFansHobby personFansHobby;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFansHobbyResource personFansHobbyResource = new PersonFansHobbyResource();
        ReflectionTestUtils.setField(personFansHobbyResource, "personFansHobbyService", personFansHobbyService);
        this.restPersonFansHobbyMockMvc = MockMvcBuilders.standaloneSetup(personFansHobbyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFansHobby createEntity(EntityManager em) {
        PersonFansHobby personFansHobby = new PersonFansHobby()
                .count(DEFAULT_COUNT);
        return personFansHobby;
    }

    @Before
    public void initTest() {
        personFansHobby = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFansHobby() throws Exception {
        int databaseSizeBeforeCreate = personFansHobbyRepository.findAll().size();

        // Create the PersonFansHobby

        restPersonFansHobbyMockMvc.perform(post("/api/person-fans-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansHobby)))
            .andExpect(status().isCreated());

        // Validate the PersonFansHobby in the database
        List<PersonFansHobby> personFansHobbyList = personFansHobbyRepository.findAll();
        assertThat(personFansHobbyList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFansHobby testPersonFansHobby = personFansHobbyList.get(personFansHobbyList.size() - 1);
        assertThat(testPersonFansHobby.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFansHobbyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFansHobbyRepository.findAll().size();

        // Create the PersonFansHobby with an existing ID
        PersonFansHobby existingPersonFansHobby = new PersonFansHobby();
        existingPersonFansHobby.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFansHobbyMockMvc.perform(post("/api/person-fans-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFansHobby)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFansHobby> personFansHobbyList = personFansHobbyRepository.findAll();
        assertThat(personFansHobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFansHobbies() throws Exception {
        // Initialize the database
        personFansHobbyRepository.saveAndFlush(personFansHobby);

        // Get all the personFansHobbyList
        restPersonFansHobbyMockMvc.perform(get("/api/person-fans-hobbies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFansHobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFansHobby() throws Exception {
        // Initialize the database
        personFansHobbyRepository.saveAndFlush(personFansHobby);

        // Get the personFansHobby
        restPersonFansHobbyMockMvc.perform(get("/api/person-fans-hobbies/{id}", personFansHobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFansHobby.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFansHobby() throws Exception {
        // Get the personFansHobby
        restPersonFansHobbyMockMvc.perform(get("/api/person-fans-hobbies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFansHobby() throws Exception {
        // Initialize the database
        personFansHobbyService.save(personFansHobby);

        int databaseSizeBeforeUpdate = personFansHobbyRepository.findAll().size();

        // Update the personFansHobby
        PersonFansHobby updatedPersonFansHobby = personFansHobbyRepository.findOne(personFansHobby.getId());
        updatedPersonFansHobby
                .count(UPDATED_COUNT);

        restPersonFansHobbyMockMvc.perform(put("/api/person-fans-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFansHobby)))
            .andExpect(status().isOk());

        // Validate the PersonFansHobby in the database
        List<PersonFansHobby> personFansHobbyList = personFansHobbyRepository.findAll();
        assertThat(personFansHobbyList).hasSize(databaseSizeBeforeUpdate);
        PersonFansHobby testPersonFansHobby = personFansHobbyList.get(personFansHobbyList.size() - 1);
        assertThat(testPersonFansHobby.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFansHobby() throws Exception {
        int databaseSizeBeforeUpdate = personFansHobbyRepository.findAll().size();

        // Create the PersonFansHobby

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFansHobbyMockMvc.perform(put("/api/person-fans-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFansHobby)))
            .andExpect(status().isCreated());

        // Validate the PersonFansHobby in the database
        List<PersonFansHobby> personFansHobbyList = personFansHobbyRepository.findAll();
        assertThat(personFansHobbyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFansHobby() throws Exception {
        // Initialize the database
        personFansHobbyService.save(personFansHobby);

        int databaseSizeBeforeDelete = personFansHobbyRepository.findAll().size();

        // Get the personFansHobby
        restPersonFansHobbyMockMvc.perform(delete("/api/person-fans-hobbies/{id}", personFansHobby.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFansHobby> personFansHobbyList = personFansHobbyRepository.findAll();
        assertThat(personFansHobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
