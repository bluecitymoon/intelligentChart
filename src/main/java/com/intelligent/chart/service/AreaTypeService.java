package com.intelligent.chart.service;

import com.intelligent.chart.domain.AreaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing AreaType.
 */
public interface AreaTypeService {

    /**
     * Save a areaType.
     *
     * @param areaType the entity to save
     * @return the persisted entity
     */
    AreaType save(AreaType areaType);

    /**
     *  Get all the areaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AreaType> findAll(Pageable pageable);

    /**
     *  Get the "id" areaType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AreaType findOne(Long id);

    /**
     *  Delete the "id" areaType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
