package com.intelligent.chart.service;

import com.intelligent.chart.domain.PrizeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PrizeLevel.
 */
public interface PrizeLevelService {

    /**
     * Save a prizeLevel.
     *
     * @param prizeLevel the entity to save
     * @return the persisted entity
     */
    PrizeLevel save(PrizeLevel prizeLevel);

    /**
     *  Get all the prizeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PrizeLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" prizeLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PrizeLevel findOne(Long id);

    /**
     *  Delete the "id" prizeLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
