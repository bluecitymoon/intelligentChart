package com.intelligent.chart.service;

import com.intelligent.chart.domain.ConnectionLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ConnectionLevel.
 */
public interface ConnectionLevelService {

    /**
     * Save a connectionLevel.
     *
     * @param connectionLevel the entity to save
     * @return the persisted entity
     */
    ConnectionLevel save(ConnectionLevel connectionLevel);

    /**
     *  Get all the connectionLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConnectionLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" connectionLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConnectionLevel findOne(Long id);

    /**
     *  Delete the "id" connectionLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
