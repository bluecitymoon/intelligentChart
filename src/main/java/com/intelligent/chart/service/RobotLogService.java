package com.intelligent.chart.service;

import com.intelligent.chart.domain.RobotLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing RobotLog.
 */
public interface RobotLogService {

    /**
     * Save a robotLog.
     *
     * @param robotLog the entity to save
     * @return the persisted entity
     */
    RobotLog save(RobotLog robotLog);

    /**
     *  Get all the robotLogs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RobotLog> findAll(Pageable pageable);

    /**
     *  Get the "id" robotLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RobotLog findOne(Long id);

    /**
     *  Delete the "id" robotLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
