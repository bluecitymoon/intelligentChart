package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PrizeGroupService;
import com.intelligent.chart.domain.PrizeGroup;
import com.intelligent.chart.repository.PrizeGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PrizeGroup.
 */
@Service
@Transactional
public class PrizeGroupServiceImpl implements PrizeGroupService{

    private final Logger log = LoggerFactory.getLogger(PrizeGroupServiceImpl.class);
    
    @Inject
    private PrizeGroupRepository prizeGroupRepository;

    /**
     * Save a prizeGroup.
     *
     * @param prizeGroup the entity to save
     * @return the persisted entity
     */
    public PrizeGroup save(PrizeGroup prizeGroup) {
        log.debug("Request to save PrizeGroup : {}", prizeGroup);
        PrizeGroup result = prizeGroupRepository.save(prizeGroup);
        return result;
    }

    /**
     *  Get all the prizeGroups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PrizeGroup> findAll(Pageable pageable) {
        log.debug("Request to get all PrizeGroups");
        Page<PrizeGroup> result = prizeGroupRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one prizeGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PrizeGroup findOne(Long id) {
        log.debug("Request to get PrizeGroup : {}", id);
        PrizeGroup prizeGroup = prizeGroupRepository.findOne(id);
        return prizeGroup;
    }

    /**
     *  Delete the  prizeGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrizeGroup : {}", id);
        prizeGroupRepository.delete(id);
    }
}
