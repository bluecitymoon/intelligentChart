package com.intelligent.chart.web.rest;

import com.intelligent.chart.IntelligentChartApp;

import com.intelligent.chart.domain.PersonNetworkShopping;
import com.intelligent.chart.repository.PersonNetworkShoppingRepository;
import com.intelligent.chart.service.PersonNetworkShoppingService;

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
 * Test class for the PersonNetworkShoppingResource REST controller.
 *
 * @see PersonNetworkShoppingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntelligentChartApp.class)
public class PersonNetworkShoppingResourceIntTest {

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private PersonNetworkShoppingRepository personNetworkShoppingRepository;

    @Inject
    private PersonNetworkShoppingService personNetworkShoppingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonNetworkShoppingMockMvc;

    private PersonNetworkShopping personNetworkShopping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonNetworkShoppingResource personNetworkShoppingResource = new PersonNetworkShoppingResource();
        ReflectionTestUtils.setField(personNetworkShoppingResource, "personNetworkShoppingService", personNetworkShoppingService);
        this.restPersonNetworkShoppingMockMvc = MockMvcBuilders.standaloneSetup(personNetworkShoppingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonNetworkShopping createEntity(EntityManager em) {
        PersonNetworkShopping personNetworkShopping = new PersonNetworkShopping()
                .createDate(DEFAULT_CREATE_DATE)
                .amount(DEFAULT_AMOUNT)
                .description(DEFAULT_DESCRIPTION);
        return personNetworkShopping;
    }

    @Before
    public void initTest() {
        personNetworkShopping = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonNetworkShopping() throws Exception {
        int databaseSizeBeforeCreate = personNetworkShoppingRepository.findAll().size();

        // Create the PersonNetworkShopping

        restPersonNetworkShoppingMockMvc.perform(post("/api/person-network-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkShopping)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkShopping in the database
        List<PersonNetworkShopping> personNetworkShoppingList = personNetworkShoppingRepository.findAll();
        assertThat(personNetworkShoppingList).hasSize(databaseSizeBeforeCreate + 1);
        PersonNetworkShopping testPersonNetworkShopping = personNetworkShoppingList.get(personNetworkShoppingList.size() - 1);
        assertThat(testPersonNetworkShopping.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPersonNetworkShopping.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPersonNetworkShopping.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPersonNetworkShoppingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personNetworkShoppingRepository.findAll().size();

        // Create the PersonNetworkShopping with an existing ID
        PersonNetworkShopping existingPersonNetworkShopping = new PersonNetworkShopping();
        existingPersonNetworkShopping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonNetworkShoppingMockMvc.perform(post("/api/person-network-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonNetworkShopping)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonNetworkShopping> personNetworkShoppingList = personNetworkShoppingRepository.findAll();
        assertThat(personNetworkShoppingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonNetworkShoppings() throws Exception {
        // Initialize the database
        personNetworkShoppingRepository.saveAndFlush(personNetworkShopping);

        // Get all the personNetworkShoppingList
        restPersonNetworkShoppingMockMvc.perform(get("/api/person-network-shoppings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personNetworkShopping.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonNetworkShopping() throws Exception {
        // Initialize the database
        personNetworkShoppingRepository.saveAndFlush(personNetworkShopping);

        // Get the personNetworkShopping
        restPersonNetworkShoppingMockMvc.perform(get("/api/person-network-shoppings/{id}", personNetworkShopping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personNetworkShopping.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonNetworkShopping() throws Exception {
        // Get the personNetworkShopping
        restPersonNetworkShoppingMockMvc.perform(get("/api/person-network-shoppings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonNetworkShopping() throws Exception {
        // Initialize the database
        personNetworkShoppingService.save(personNetworkShopping);

        int databaseSizeBeforeUpdate = personNetworkShoppingRepository.findAll().size();

        // Update the personNetworkShopping
        PersonNetworkShopping updatedPersonNetworkShopping = personNetworkShoppingRepository.findOne(personNetworkShopping.getId());
        updatedPersonNetworkShopping
                .createDate(UPDATED_CREATE_DATE)
                .amount(UPDATED_AMOUNT)
                .description(UPDATED_DESCRIPTION);

        restPersonNetworkShoppingMockMvc.perform(put("/api/person-network-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonNetworkShopping)))
            .andExpect(status().isOk());

        // Validate the PersonNetworkShopping in the database
        List<PersonNetworkShopping> personNetworkShoppingList = personNetworkShoppingRepository.findAll();
        assertThat(personNetworkShoppingList).hasSize(databaseSizeBeforeUpdate);
        PersonNetworkShopping testPersonNetworkShopping = personNetworkShoppingList.get(personNetworkShoppingList.size() - 1);
        assertThat(testPersonNetworkShopping.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPersonNetworkShopping.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPersonNetworkShopping.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonNetworkShopping() throws Exception {
        int databaseSizeBeforeUpdate = personNetworkShoppingRepository.findAll().size();

        // Create the PersonNetworkShopping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonNetworkShoppingMockMvc.perform(put("/api/person-network-shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personNetworkShopping)))
            .andExpect(status().isCreated());

        // Validate the PersonNetworkShopping in the database
        List<PersonNetworkShopping> personNetworkShoppingList = personNetworkShoppingRepository.findAll();
        assertThat(personNetworkShoppingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonNetworkShopping() throws Exception {
        // Initialize the database
        personNetworkShoppingService.save(personNetworkShopping);

        int databaseSizeBeforeDelete = personNetworkShoppingRepository.findAll().size();

        // Get the personNetworkShopping
        restPersonNetworkShoppingMockMvc.perform(delete("/api/person-network-shoppings/{id}", personNetworkShopping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonNetworkShopping> personNetworkShoppingList = personNetworkShoppingRepository.findAll();
        assertThat(personNetworkShoppingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
