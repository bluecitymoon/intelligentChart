package com.intelligent.chart.service;

import com.intelligent.chart.domain.ChartMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ChartMenu.
 */
public interface ChartMenuService {

    /**
     * Save a chartMenu.
     *
     * @param chartMenu the entity to save
     * @return the persisted entity
     */
    ChartMenu save(ChartMenu chartMenu);

    /**
     *  Get all the chartMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChartMenu> findAll(Pageable pageable);

    /**
     *  Get the "id" chartMenu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartMenu findOne(Long id);

    /**
     *  Delete the "id" chartMenu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
