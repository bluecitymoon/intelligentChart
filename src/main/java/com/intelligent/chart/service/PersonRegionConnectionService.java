package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonRegionConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonRegionConnection.
 */
public interface PersonRegionConnectionService {

    /**
     * Save a personRegionConnection.
     *
     * @param personRegionConnection the entity to save
     * @return the persisted entity
     */
    PersonRegionConnection save(PersonRegionConnection personRegionConnection);

    /**
     *  Get all the personRegionConnections.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonRegionConnection> findAll(Pageable pageable);

    /**
     *  Get the "id" personRegionConnection.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonRegionConnection findOne(Long id);

    /**
     *  Delete the "id" personRegionConnection.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
