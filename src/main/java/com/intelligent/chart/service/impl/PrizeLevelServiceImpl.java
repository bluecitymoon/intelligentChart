package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PrizeLevelService;
import com.intelligent.chart.domain.PrizeLevel;
import com.intelligent.chart.repository.PrizeLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PrizeLevel.
 */
@Service
@Transactional
public class PrizeLevelServiceImpl implements PrizeLevelService{

    private final Logger log = LoggerFactory.getLogger(PrizeLevelServiceImpl.class);
    
    @Inject
    private PrizeLevelRepository prizeLevelRepository;

    /**
     * Save a prizeLevel.
     *
     * @param prizeLevel the entity to save
     * @return the persisted entity
     */
    public PrizeLevel save(PrizeLevel prizeLevel) {
        log.debug("Request to save PrizeLevel : {}", prizeLevel);
        PrizeLevel result = prizeLevelRepository.save(prizeLevel);
        return result;
    }

    /**
     *  Get all the prizeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PrizeLevel> findAll(Pageable pageable) {
        log.debug("Request to get all PrizeLevels");
        Page<PrizeLevel> result = prizeLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one prizeLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PrizeLevel findOne(Long id) {
        log.debug("Request to get PrizeLevel : {}", id);
        PrizeLevel prizeLevel = prizeLevelRepository.findOne(id);
        return prizeLevel;
    }

    /**
     *  Delete the  prizeLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrizeLevel : {}", id);
        prizeLevelRepository.delete(id);
    }
}
