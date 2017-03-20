package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonPopularity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonPopularity.
 */
public interface PersonPopularityService {

    /**
     * Save a personPopularity.
     *
     * @param personPopularity the entity to save
     * @return the persisted entity
     */
    PersonPopularity save(PersonPopularity personPopularity);

    /**
     *  Get all the personPopularities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonPopularity> findAll(Pageable pageable);

    /**
     *  Get the "id" personPopularity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonPopularity findOne(Long id);

    /**
     *  Delete the "id" personPopularity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
