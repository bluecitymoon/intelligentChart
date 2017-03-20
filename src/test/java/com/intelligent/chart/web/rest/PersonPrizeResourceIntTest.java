package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonPrize;
import com.intelligent.chart.repository.PersonPrizeRepository;
import com.intelligent.chart.service.PersonPrizeService;

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
 * Test class for the PersonPrizeResource REST controller.
 *
 * @see PersonPrizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonPrizeResourceIntTest {

    private static final LocalDate DEFAULT_PRIZE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRIZE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private PersonPrizeRepository personPrizeRepository;

    @Inject
    private PersonPrizeService personPrizeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonPrizeMockMvc;

    private PersonPrize personPrize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonPrizeResource personPrizeResource = new PersonPrizeResource();
        ReflectionTestUtils.setField(personPrizeResource, "personPrizeService", personPrizeService);
        this.restPersonPrizeMockMvc = MockMvcBuilders.standaloneSetup(personPrizeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPrize createEntity(EntityManager em) {
        PersonPrize personPrize = new PersonPrize()
                .prizeDate(DEFAULT_PRIZE_DATE)
                .name(DEFAULT_NAME);
        return personPrize;
    }

    @Before
    public void initTest() {
        personPrize = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPrize() throws Exception {
        int databaseSizeBeforeCreate = personPrizeRepository.findAll().size();

        // Create the PersonPrize

        restPersonPrizeMockMvc.perform(post("/api/person-prizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPrize)))
            .andExpect(status().isCreated());

        // Validate the PersonPrize in the database
        List<PersonPrize> personPrizeList = personPrizeRepository.findAll();
        assertThat(personPrizeList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPrize testPersonPrize = personPrizeList.get(personPrizeList.size() - 1);
        assertThat(testPersonPrize.getPrizeDate()).isEqualTo(DEFAULT_PRIZE_DATE);
        assertThat(testPersonPrize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPersonPrizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPrizeRepository.findAll().size();

        // Create the PersonPrize with an existing ID
        PersonPrize existingPersonPrize = new PersonPrize();
        existingPersonPrize.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPrizeMockMvc.perform(post("/api/person-prizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonPrize)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonPrize> personPrizeList = personPrizeRepository.findAll();
        assertThat(personPrizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonPrizes() throws Exception {
        // Initialize the database
        personPrizeRepository.saveAndFlush(personPrize);

        // Get all the personPrizeList
        restPersonPrizeMockMvc.perform(get("/api/person-prizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPrize.getId().intValue())))
            .andExpect(jsonPath("$.[*].prizeDate").value(hasItem(DEFAULT_PRIZE_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPersonPrize() throws Exception {
        // Initialize the database
        personPrizeRepository.saveAndFlush(personPrize);

        // Get the personPrize
        restPersonPrizeMockMvc.perform(get("/api/person-prizes/{id}", personPrize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPrize.getId().intValue()))
            .andExpect(jsonPath("$.prizeDate").value(DEFAULT_PRIZE_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPrize() throws Exception {
        // Get the personPrize
        restPersonPrizeMockMvc.perform(get("/api/person-prizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPrize() throws Exception {
        // Initialize the database
        personPrizeService.save(personPrize);

        int databaseSizeBeforeUpdate = personPrizeRepository.findAll().size();

        // Update the personPrize
        PersonPrize updatedPersonPrize = personPrizeRepository.findOne(personPrize.getId());
        updatedPersonPrize
                .prizeDate(UPDATED_PRIZE_DATE)
                .name(UPDATED_NAME);

        restPersonPrizeMockMvc.perform(put("/api/person-prizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonPrize)))
            .andExpect(status().isOk());

        // Validate the PersonPrize in the database
        List<PersonPrize> personPrizeList = personPrizeRepository.findAll();
        assertThat(personPrizeList).hasSize(databaseSizeBeforeUpdate);
        PersonPrize testPersonPrize = personPrizeList.get(personPrizeList.size() - 1);
        assertThat(testPersonPrize.getPrizeDate()).isEqualTo(UPDATED_PRIZE_DATE);
        assertThat(testPersonPrize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPrize() throws Exception {
        int databaseSizeBeforeUpdate = personPrizeRepository.findAll().size();

        // Create the PersonPrize

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonPrizeMockMvc.perform(put("/api/person-prizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPrize)))
            .andExpect(status().isCreated());

        // Validate the PersonPrize in the database
        List<PersonPrize> personPrizeList = personPrizeRepository.findAll();
        assertThat(personPrizeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonPrize() throws Exception {
        // Initialize the database
        personPrizeService.save(personPrize);

        int databaseSizeBeforeDelete = personPrizeRepository.findAll().size();

        // Get the personPrize
        restPersonPrizeMockMvc.perform(delete("/api/person-prizes/{id}", personPrize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonPrize> personPrizeList = personPrizeRepository.findAll();
        assertThat(personPrizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
