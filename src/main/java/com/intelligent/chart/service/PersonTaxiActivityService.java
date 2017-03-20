package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonTaxiActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonTaxiActivity.
 */
public interface PersonTaxiActivityService {

    /**
     * Save a personTaxiActivity.
     *
     * @param personTaxiActivity the entity to save
     * @return the persisted entity
     */
    PersonTaxiActivity save(PersonTaxiActivity personTaxiActivity);

    /**
     *  Get all the personTaxiActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonTaxiActivity> findAll(Pageable pageable);

    /**
     *  Get the "id" personTaxiActivity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonTaxiActivity findOne(Long id);

    /**
     *  Delete the "id" personTaxiActivity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
