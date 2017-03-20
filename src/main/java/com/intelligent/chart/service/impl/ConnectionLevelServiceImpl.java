package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.ConnectionLevelService;
import com.intelligent.chart.domain.ConnectionLevel;
import com.intelligent.chart.repository.ConnectionLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ConnectionLevel.
 */
@Service
@Transactional
public class ConnectionLevelServiceImpl implements ConnectionLevelService{

    private final Logger log = LoggerFactory.getLogger(ConnectionLevelServiceImpl.class);
    
    @Inject
    private ConnectionLevelRepository connectionLevelRepository;

    /**
     * Save a connectionLevel.
     *
     * @param connectionLevel the entity to save
     * @return the persisted entity
     */
    public ConnectionLevel save(ConnectionLevel connectionLevel) {
        log.debug("Request to save ConnectionLevel : {}", connectionLevel);
        ConnectionLevel result = connectionLevelRepository.save(connectionLevel);
        return result;
    }

    /**
     *  Get all the connectionLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ConnectionLevel> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectionLevels");
        Page<ConnectionLevel> result = connectionLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one connectionLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ConnectionLevel findOne(Long id) {
        log.debug("Request to get ConnectionLevel : {}", id);
        ConnectionLevel connectionLevel = connectionLevelRepository.findOne(id);
        return connectionLevel;
    }

    /**
     *  Delete the  connectionLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConnectionLevel : {}", id);
        connectionLevelRepository.delete(id);
    }
}
