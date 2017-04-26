package com.intelligent.chart.service.impl;

import com.intelligent.chart.domain.RobotLog;
import com.intelligent.chart.repository.RobotLogRepository;
import com.intelligent.chart.service.RobotLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing RobotLog.
 */
@Service
public class RobotLogServiceImpl implements RobotLogService{

    private final Logger log = LoggerFactory.getLogger(RobotLogServiceImpl.class);

    @Inject
    private RobotLogRepository robotLogRepository;

    /**
     * Save a robotLog.
     *
     * @param robotLog the entity to save
     * @return the persisted entity
     */
    public RobotLog save(RobotLog robotLog) {
        log.debug("Request to save RobotLog : {}", robotLog);
        RobotLog result = robotLogRepository.save(robotLog);
        return result;
    }

    /**
     *  Get all the robotLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RobotLog> findAll(Pageable pageable) {
        log.debug("Request to get all RobotLogs");
        Page<RobotLog> result = robotLogRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one robotLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RobotLog findOne(Long id) {
        log.debug("Request to get RobotLog : {}", id);
        RobotLog robotLog = robotLogRepository.findOne(id);
        return robotLog;
    }

    /**
     *  Delete the  robotLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RobotLog : {}", id);
        robotLogRepository.delete(id);
    }
}
