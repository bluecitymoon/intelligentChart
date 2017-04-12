package com.intelligent.chart.service;

import com.intelligent.chart.domain.Robot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Robot.
 */
public interface RobotService {

    /**
     * Save a robot.
     *
     * @param robot the entity to save
     * @return the persisted entity
     */
    Robot save(Robot robot);

    /**
     *  Get all the robots.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Robot> findAll(Pageable pageable);

    /**
     *  Get the "id" robot.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Robot findOne(Long id);

    /**
     *  Delete the "id" robot.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Robot start(Long id);
}
