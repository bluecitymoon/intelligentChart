package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFanDisbribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFanDisbribution.
 */
public interface PersonFanDisbributionService {

    /**
     * Save a personFanDisbribution.
     *
     * @param personFanDisbribution the entity to save
     * @return the persisted entity
     */
    PersonFanDisbribution save(PersonFanDisbribution personFanDisbribution);

    /**
     *  Get all the personFanDisbributions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFanDisbribution> findAll(Pageable pageable);

    /**
     *  Get the "id" personFanDisbribution.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFanDisbribution findOne(Long id);

    /**
     *  Delete the "id" personFanDisbribution.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
