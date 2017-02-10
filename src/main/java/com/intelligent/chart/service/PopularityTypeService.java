package com.intelligent.chart.service;

import com.intelligent.chart.domain.PopularityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PopularityType.
 */
public interface PopularityTypeService {

    /**
     * Save a popularityType.
     *
     * @param popularityType the entity to save
     * @return the persisted entity
     */
    PopularityType save(PopularityType popularityType);

    /**
     *  Get all the popularityTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PopularityType> findAll(Pageable pageable);

    /**
     *  Get the "id" popularityType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PopularityType findOne(Long id);

    /**
     *  Delete the "id" popularityType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
