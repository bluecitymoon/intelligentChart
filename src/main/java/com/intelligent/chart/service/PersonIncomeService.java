package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonIncome.
 */
public interface PersonIncomeService {

    /**
     * Save a personIncome.
     *
     * @param personIncome the entity to save
     * @return the persisted entity
     */
    PersonIncome save(PersonIncome personIncome);

    /**
     *  Get all the personIncomes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonIncome> findAll(Pageable pageable);

    /**
     *  Get the "id" personIncome.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonIncome findOne(Long id);

    /**
     *  Delete the "id" personIncome.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
