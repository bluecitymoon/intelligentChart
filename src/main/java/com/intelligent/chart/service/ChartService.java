package com.intelligent.chart.service;

import com.intelligent.chart.domain.Chart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Chart.
 */
public interface ChartService {

    /**
     * Save a chart.
     *
     * @param chart the entity to save
     * @return the persisted entity
     */
    Chart save(Chart chart);

    /**
     *  Get all the charts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Chart> findAll(Pageable pageable);

    /**
     *  Get the "id" chart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Chart findOne(Long id);

    /**
     *  Delete the "id" chart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
