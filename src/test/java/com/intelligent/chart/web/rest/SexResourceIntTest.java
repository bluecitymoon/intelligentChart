package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.Sex;
import com.intelligent.chart.repository.SexRepository;
import com.intelligent.chart.service.SexService;

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
 * Test class for the SexResource REST controller.
 *
 * @see SexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class SexResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Inject
    private SexRepository sexRepository;

    @Inject
    private SexService sexService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSexMockMvc;

    private Sex sex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SexResource sexResource = new SexResource();
        ReflectionTestUtils.setField(sexResource, "sexService", sexService);
        this.restSexMockMvc = MockMvcBuilders.standaloneSetup(sexResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sex createEntity(EntityManager em) {
        Sex sex = new Sex()
                .name(DEFAULT_NAME)
                .identifier(DEFAULT_IDENTIFIER);
        return sex;
    }

    @Before
    public void initTest() {
        sex = createEntity(em);
    }

    @Test
    @Transactional
    public void createSex() throws Exception {
        int databaseSizeBeforeCreate = sexRepository.findAll().size();

        // Create the Sex

        restSexMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sex)))
            .andExpect(status().isCreated());

        // Validate the Sex in the database
        List<Sex> sexList = sexRepository.findAll();
        assertThat(sexList).hasSize(databaseSizeBeforeCreate + 1);
        Sex testSex = sexList.get(sexList.size() - 1);
        assertThat(testSex.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSex.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createSexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sexRepository.findAll().size();

        // Create the Sex with an existing ID
        Sex existingSex = new Sex();
        existingSex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexMockMvc.perform(post("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSex)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sex> sexList = sexRepository.findAll();
        assertThat(sexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSexes() throws Exception {
        // Initialize the database
        sexRepository.saveAndFlush(sex);

        // Get all the sexList
        restSexMockMvc.perform(get("/api/sexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sex.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }

    @Test
    @Transactional
    public void getSex() throws Exception {
        // Initialize the database
        sexRepository.saveAndFlush(sex);

        // Get the sex
        restSexMockMvc.perform(get("/api/sexes/{id}", sex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sex.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSex() throws Exception {
        // Get the sex
        restSexMockMvc.perform(get("/api/sexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSex() throws Exception {
        // Initialize the database
        sexService.save(sex);

        int databaseSizeBeforeUpdate = sexRepository.findAll().size();

        // Update the sex
        Sex updatedSex = sexRepository.findOne(sex.getId());
        updatedSex
                .name(UPDATED_NAME)
                .identifier(UPDATED_IDENTIFIER);

        restSexMockMvc.perform(put("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSex)))
            .andExpect(status().isOk());

        // Validate the Sex in the database
        List<Sex> sexList = sexRepository.findAll();
        assertThat(sexList).hasSize(databaseSizeBeforeUpdate);
        Sex testSex = sexList.get(sexList.size() - 1);
        assertThat(testSex.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSex.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingSex() throws Exception {
        int databaseSizeBeforeUpdate = sexRepository.findAll().size();

        // Create the Sex

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSexMockMvc.perform(put("/api/sexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sex)))
            .andExpect(status().isCreated());

        // Validate the Sex in the database
        List<Sex> sexList = sexRepository.findAll();
        assertThat(sexList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSex() throws Exception {
        // Initialize the database
        sexService.save(sex);

        int databaseSizeBeforeDelete = sexRepository.findAll().size();

        // Get the sex
        restSexMockMvc.perform(delete("/api/sexes/{id}", sex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sex> sexList = sexRepository.findAll();
        assertThat(sexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
