package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonFanSupportGroup;
import com.intelligent.chart.repository.PersonFanSupportGroupRepository;
import com.intelligent.chart.service.PersonFanSupportGroupService;

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
 * Test class for the PersonFanSupportGroupResource REST controller.
 *
 * @see PersonFanSupportGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonFanSupportGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEADER = "AAAAAAAAAA";
    private static final String UPDATED_LEADER = "BBBBBBBBBB";

    private static final Long DEFAULT_FANS_COUNT = 1L;
    private static final Long UPDATED_FANS_COUNT = 2L;

    @Inject
    private PersonFanSupportGroupRepository personFanSupportGroupRepository;

    @Inject
    private PersonFanSupportGroupService personFanSupportGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonFanSupportGroupMockMvc;

    private PersonFanSupportGroup personFanSupportGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonFanSupportGroupResource personFanSupportGroupResource = new PersonFanSupportGroupResource();
        ReflectionTestUtils.setField(personFanSupportGroupResource, "personFanSupportGroupService", personFanSupportGroupService);
        this.restPersonFanSupportGroupMockMvc = MockMvcBuilders.standaloneSetup(personFanSupportGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonFanSupportGroup createEntity(EntityManager em) {
        PersonFanSupportGroup personFanSupportGroup = new PersonFanSupportGroup()
                .name(DEFAULT_NAME)
                .leader(DEFAULT_LEADER)
                .fansCount(DEFAULT_FANS_COUNT);
        return personFanSupportGroup;
    }

    @Before
    public void initTest() {
        personFanSupportGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFanSupportGroup() throws Exception {
        int databaseSizeBeforeCreate = personFanSupportGroupRepository.findAll().size();

        // Create the PersonFanSupportGroup

        restPersonFanSupportGroupMockMvc.perform(post("/api/person-fan-support-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanSupportGroup)))
            .andExpect(status().isCreated());

        // Validate the PersonFanSupportGroup in the database
        List<PersonFanSupportGroup> personFanSupportGroupList = personFanSupportGroupRepository.findAll();
        assertThat(personFanSupportGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFanSupportGroup testPersonFanSupportGroup = personFanSupportGroupList.get(personFanSupportGroupList.size() - 1);
        assertThat(testPersonFanSupportGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonFanSupportGroup.getLeader()).isEqualTo(DEFAULT_LEADER);
        assertThat(testPersonFanSupportGroup.getFansCount()).isEqualTo(DEFAULT_FANS_COUNT);
    }

    @Test
    @Transactional
    public void createPersonFanSupportGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFanSupportGroupRepository.findAll().size();

        // Create the PersonFanSupportGroup with an existing ID
        PersonFanSupportGroup existingPersonFanSupportGroup = new PersonFanSupportGroup();
        existingPersonFanSupportGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFanSupportGroupMockMvc.perform(post("/api/person-fan-support-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonFanSupportGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonFanSupportGroup> personFanSupportGroupList = personFanSupportGroupRepository.findAll();
        assertThat(personFanSupportGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFanSupportGroups() throws Exception {
        // Initialize the database
        personFanSupportGroupRepository.saveAndFlush(personFanSupportGroup);

        // Get all the personFanSupportGroupList
        restPersonFanSupportGroupMockMvc.perform(get("/api/person-fan-support-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFanSupportGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].leader").value(hasItem(DEFAULT_LEADER.toString())))
            .andExpect(jsonPath("$.[*].fansCount").value(hasItem(DEFAULT_FANS_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFanSupportGroup() throws Exception {
        // Initialize the database
        personFanSupportGroupRepository.saveAndFlush(personFanSupportGroup);

        // Get the personFanSupportGroup
        restPersonFanSupportGroupMockMvc.perform(get("/api/person-fan-support-groups/{id}", personFanSupportGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFanSupportGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.leader").value(DEFAULT_LEADER.toString()))
            .andExpect(jsonPath("$.fansCount").value(DEFAULT_FANS_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFanSupportGroup() throws Exception {
        // Get the personFanSupportGroup
        restPersonFanSupportGroupMockMvc.perform(get("/api/person-fan-support-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFanSupportGroup() throws Exception {
        // Initialize the database
        personFanSupportGroupService.save(personFanSupportGroup);

        int databaseSizeBeforeUpdate = personFanSupportGroupRepository.findAll().size();

        // Update the personFanSupportGroup
        PersonFanSupportGroup updatedPersonFanSupportGroup = personFanSupportGroupRepository.findOne(personFanSupportGroup.getId());
        updatedPersonFanSupportGroup
                .name(UPDATED_NAME)
                .leader(UPDATED_LEADER)
                .fansCount(UPDATED_FANS_COUNT);

        restPersonFanSupportGroupMockMvc.perform(put("/api/person-fan-support-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFanSupportGroup)))
            .andExpect(status().isOk());

        // Validate the PersonFanSupportGroup in the database
        List<PersonFanSupportGroup> personFanSupportGroupList = personFanSupportGroupRepository.findAll();
        assertThat(personFanSupportGroupList).hasSize(databaseSizeBeforeUpdate);
        PersonFanSupportGroup testPersonFanSupportGroup = personFanSupportGroupList.get(personFanSupportGroupList.size() - 1);
        assertThat(testPersonFanSupportGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonFanSupportGroup.getLeader()).isEqualTo(UPDATED_LEADER);
        assertThat(testPersonFanSupportGroup.getFansCount()).isEqualTo(UPDATED_FANS_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFanSupportGroup() throws Exception {
        int databaseSizeBeforeUpdate = personFanSupportGroupRepository.findAll().size();

        // Create the PersonFanSupportGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFanSupportGroupMockMvc.perform(put("/api/person-fan-support-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFanSupportGroup)))
            .andExpect(status().isCreated());

        // Validate the PersonFanSupportGroup in the database
        List<PersonFanSupportGroup> personFanSupportGroupList = personFanSupportGroupRepository.findAll();
        assertThat(personFanSupportGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFanSupportGroup() throws Exception {
        // Initialize the database
        personFanSupportGroupService.save(personFanSupportGroup);

        int databaseSizeBeforeDelete = personFanSupportGroupRepository.findAll().size();

        // Get the personFanSupportGroup
        restPersonFanSupportGroupMockMvc.perform(delete("/api/person-fan-support-groups/{id}", personFanSupportGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFanSupportGroup> personFanSupportGroupList = personFanSupportGroupRepository.findAll();
        assertThat(personFanSupportGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
