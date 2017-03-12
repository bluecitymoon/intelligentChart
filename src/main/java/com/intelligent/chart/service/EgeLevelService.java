package com.intelligent.chart.service;

import com.intelligent.chart.domain.EgeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing EgeLevel.
 */
public interface EgeLevelService {

    /**
     * Save a egeLevel.
     *
     * @param egeLevel the entity to save
     * @return the persisted entity
     */
    EgeLevel save(EgeLevel egeLevel);

    /**
     *  Get all the egeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EgeLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" egeLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EgeLevel findOne(Long id);

    /**
     *  Delete the "id" egeLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
