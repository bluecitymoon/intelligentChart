package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.MenuGroup;
import com.intelligent.chart.repository.MenuGroupRepository;
import com.intelligent.chart.service.MenuGroupService;

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
 * Test class for the MenuGroupResource REST controller.
 *
 * @see MenuGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class MenuGroupResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Inject
    private MenuGroupRepository menuGroupRepository;

    @Inject
    private MenuGroupService menuGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMenuGroupMockMvc;

    private MenuGroup menuGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MenuGroupResource menuGroupResource = new MenuGroupResource();
        ReflectionTestUtils.setField(menuGroupResource, "menuGroupService", menuGroupService);
        this.restMenuGroupMockMvc = MockMvcBuilders.standaloneSetup(menuGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuGroup createEntity(EntityManager em) {
        MenuGroup menuGroup = new MenuGroup()
                .title(DEFAULT_TITLE)
                .icon(DEFAULT_ICON);
        return menuGroup;
    }

    @Before
    public void initTest() {
        menuGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenuGroup() throws Exception {
        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();

        // Create the MenuGroup

        restMenuGroupMockMvc.perform(post("/api/menu-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuGroup)))
            .andExpect(status().isCreated());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMenuGroup.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createMenuGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();

        // Create the MenuGroup with an existing ID
        MenuGroup existingMenuGroup = new MenuGroup();
        existingMenuGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuGroupMockMvc.perform(post("/api/menu-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMenuGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenuGroups() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get all the menuGroupList
        restMenuGroupMockMvc.perform(get("/api/menu-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())));
    }

    @Test
    @Transactional
    public void getMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get the menuGroup
        restMenuGroupMockMvc.perform(get("/api/menu-groups/{id}", menuGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menuGroup.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenuGroup() throws Exception {
        // Get the menuGroup
        restMenuGroupMockMvc.perform(get("/api/menu-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenuGroup() throws Exception {
        // Initialize the database
        menuGroupService.save(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup
        MenuGroup updatedMenuGroup = menuGroupRepository.findOne(menuGroup.getId());
        updatedMenuGroup
                .title(UPDATED_TITLE)
                .icon(UPDATED_ICON);

        restMenuGroupMockMvc.perform(put("/api/menu-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenuGroup)))
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMenuGroup.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Create the MenuGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenuGroupMockMvc.perform(put("/api/menu-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuGroup)))
            .andExpect(status().isCreated());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMenuGroup() throws Exception {
        // Initialize the database
        menuGroupService.save(menuGroup);

        int databaseSizeBeforeDelete = menuGroupRepository.findAll().size();

        // Get the menuGroup
        restMenuGroupMockMvc.perform(delete("/api/menu-groups/{id}", menuGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
