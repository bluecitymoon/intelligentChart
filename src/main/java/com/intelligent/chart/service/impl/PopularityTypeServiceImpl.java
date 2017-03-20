package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PopularityTypeService;
import com.intelligent.chart.domain.PopularityType;
import com.intelligent.chart.repository.PopularityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PopularityType.
 */
@Service
@Transactional
public class PopularityTypeServiceImpl implements PopularityTypeService{

    private final Logger log = LoggerFactory.getLogger(PopularityTypeServiceImpl.class);
    
    @Inject
    private PopularityTypeRepository popularityTypeRepository;

    /**
     * Save a popularityType.
     *
     * @param popularityType the entity to save
     * @return the persisted entity
     */
    public PopularityType save(PopularityType popularityType) {
        log.debug("Request to save PopularityType : {}", popularityType);
        PopularityType result = popularityTypeRepository.save(popularityType);
        return result;
    }

    /**
     *  Get all the popularityTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PopularityType> findAll(Pageable pageable) {
        log.debug("Request to get all PopularityTypes");
        Page<PopularityType> result = popularityTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one popularityType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PopularityType findOne(Long id) {
        log.debug("Request to get PopularityType : {}", id);
        PopularityType popularityType = popularityTypeRepository.findOne(id);
        return popularityType;
    }

    /**
     *  Delete the  popularityType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopularityType : {}", id);
        popularityTypeRepository.delete(id);
    }
}
