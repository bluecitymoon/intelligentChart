package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonCreditCardActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonCreditCardActivity.
 */
public interface PersonCreditCardActivityService {

    /**
     * Save a personCreditCardActivity.
     *
     * @param personCreditCardActivity the entity to save
     * @return the persisted entity
     */
    PersonCreditCardActivity save(PersonCreditCardActivity personCreditCardActivity);

    /**
     *  Get all the personCreditCardActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonCreditCardActivity> findAll(Pageable pageable);

    /**
     *  Get the "id" personCreditCardActivity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonCreditCardActivity findOne(Long id);

    /**
     *  Delete the "id" personCreditCardActivity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
