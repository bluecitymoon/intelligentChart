package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PrizeTypeService;
import com.intelligent.chart.domain.PrizeType;
import com.intelligent.chart.repository.PrizeTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PrizeType.
 */
@Service
@Transactional
public class PrizeTypeServiceImpl implements PrizeTypeService{

    private final Logger log = LoggerFactory.getLogger(PrizeTypeServiceImpl.class);
    
    @Inject
    private PrizeTypeRepository prizeTypeRepository;

    /**
     * Save a prizeType.
     *
     * @param prizeType the entity to save
     * @return the persisted entity
     */
    public PrizeType save(PrizeType prizeType) {
        log.debug("Request to save PrizeType : {}", prizeType);
        PrizeType result = prizeTypeRepository.save(prizeType);
        return result;
    }

    /**
     *  Get all the prizeTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PrizeType> findAll(Pageable pageable) {
        log.debug("Request to get all PrizeTypes");
        Page<PrizeType> result = prizeTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one prizeType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PrizeType findOne(Long id) {
        log.debug("Request to get PrizeType : {}", id);
        PrizeType prizeType = prizeTypeRepository.findOne(id);
        return prizeType;
    }

    /**
     *  Delete the  prizeType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrizeType : {}", id);
        prizeTypeRepository.delete(id);
    }
}
