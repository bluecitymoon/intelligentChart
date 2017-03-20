package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonPaidNetworkDebit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonPaidNetworkDebit.
 */
public interface PersonPaidNetworkDebitService {

    /**
     * Save a personPaidNetworkDebit.
     *
     * @param personPaidNetworkDebit the entity to save
     * @return the persisted entity
     */
    PersonPaidNetworkDebit save(PersonPaidNetworkDebit personPaidNetworkDebit);

    /**
     *  Get all the personPaidNetworkDebits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonPaidNetworkDebit> findAll(Pageable pageable);

    /**
     *  Get the "id" personPaidNetworkDebit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonPaidNetworkDebit findOne(Long id);

    /**
     *  Delete the "id" personPaidNetworkDebit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
