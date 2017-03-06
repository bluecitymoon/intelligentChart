package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonNetworkDebit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonNetworkDebit.
 */
public interface PersonNetworkDebitService {

    /**
     * Save a personNetworkDebit.
     *
     * @param personNetworkDebit the entity to save
     * @return the persisted entity
     */
    PersonNetworkDebit save(PersonNetworkDebit personNetworkDebit);

    /**
     *  Get all the personNetworkDebits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonNetworkDebit> findAll(Pageable pageable);

    /**
     *  Get the "id" personNetworkDebit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonNetworkDebit findOne(Long id);

    /**
     *  Delete the "id" personNetworkDebit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
