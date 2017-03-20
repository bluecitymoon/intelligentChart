package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonAreaPercentage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonAreaPercentage.
 */
public interface PersonAreaPercentageService {

    /**
     * Save a personAreaPercentage.
     *
     * @param personAreaPercentage the entity to save
     * @return the persisted entity
     */
    PersonAreaPercentage save(PersonAreaPercentage personAreaPercentage);

    /**
     *  Get all the personAreaPercentages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonAreaPercentage> findAll(Pageable pageable);

    /**
     *  Get the "id" personAreaPercentage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonAreaPercentage findOne(Long id);

    /**
     *  Delete the "id" personAreaPercentage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
