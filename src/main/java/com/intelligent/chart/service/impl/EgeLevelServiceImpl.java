package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.EgeLevelService;
import com.intelligent.chart.domain.EgeLevel;
import com.intelligent.chart.repository.EgeLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing EgeLevel.
 */
@Service
@Transactional
public class EgeLevelServiceImpl implements EgeLevelService{

    private final Logger log = LoggerFactory.getLogger(EgeLevelServiceImpl.class);
    
    @Inject
    private EgeLevelRepository egeLevelRepository;

    /**
     * Save a egeLevel.
     *
     * @param egeLevel the entity to save
     * @return the persisted entity
     */
    public EgeLevel save(EgeLevel egeLevel) {
        log.debug("Request to save EgeLevel : {}", egeLevel);
        EgeLevel result = egeLevelRepository.save(egeLevel);
        return result;
    }

    /**
     *  Get all the egeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EgeLevel> findAll(Pageable pageable) {
        log.debug("Request to get all EgeLevels");
        Page<EgeLevel> result = egeLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one egeLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EgeLevel findOne(Long id) {
        log.debug("Request to get EgeLevel : {}", id);
        EgeLevel egeLevel = egeLevelRepository.findOne(id);
        return egeLevel;
    }

    /**
     *  Delete the  egeLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EgeLevel : {}", id);
        egeLevelRepository.delete(id);
    }
}
