package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.Hobby;
import com.intelligent.chart.repository.HobbyRepository;
import com.intelligent.chart.service.HobbyService;

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
 * Test class for the HobbyResource REST controller.
 *
 * @see HobbyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class HobbyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private HobbyRepository hobbyRepository;

    @Inject
    private HobbyService hobbyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHobbyMockMvc;

    private Hobby hobby;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HobbyResource hobbyResource = new HobbyResource();
        ReflectionTestUtils.setField(hobbyResource, "hobbyService", hobbyService);
        this.restHobbyMockMvc = MockMvcBuilders.standaloneSetup(hobbyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hobby createEntity(EntityManager em) {
        Hobby hobby = new Hobby()
                .name(DEFAULT_NAME);
        return hobby;
    }

    @Before
    public void initTest() {
        hobby = createEntity(em);
    }

    @Test
    @Transactional
    public void createHobby() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby

        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isCreated());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate + 1);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createHobbyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby with an existing ID
        Hobby existingHobby = new Hobby();
        existingHobby.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHobby)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHobbies() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get all the hobbyList
        restHobbyMockMvc.perform(get("/api/hobbies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", hobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hobby.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHobby() throws Exception {
        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHobby() throws Exception {
        // Initialize the database
        hobbyService.save(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby
        Hobby updatedHobby = hobbyRepository.findOne(hobby.getId());
        updatedHobby
                .name(UPDATED_NAME);

        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHobby)))
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Create the Hobby

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isCreated());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHobby() throws Exception {
        // Initialize the database
        hobbyService.save(hobby);

        int databaseSizeBeforeDelete = hobbyRepository.findAll().size();

        // Get the hobby
        restHobbyMockMvc.perform(delete("/api/hobbies/{id}", hobby.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
