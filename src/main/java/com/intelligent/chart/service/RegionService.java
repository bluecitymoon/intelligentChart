package com.intelligent.chart.service;

import com.intelligent.chart.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Region.
 */
public interface RegionService {

    /**
     * Save a region.
     *
     * @param region the entity to save
     * @return the persisted entity
     */
    Region save(Region region);

    /**
     *  Get all the regions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Region> findAll(Pageable pageable);

    /**
     *  Get the "id" region.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Region findOne(Long id);

    /**
     *  Delete the "id" region.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
