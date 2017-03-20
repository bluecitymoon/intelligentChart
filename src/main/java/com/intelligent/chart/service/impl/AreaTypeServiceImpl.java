package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.AreaTypeService;
import com.intelligent.chart.domain.AreaType;
import com.intelligent.chart.repository.AreaTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing AreaType.
 */
@Service
@Transactional
public class AreaTypeServiceImpl implements AreaTypeService{

    private final Logger log = LoggerFactory.getLogger(AreaTypeServiceImpl.class);
    
    @Inject
    private AreaTypeRepository areaTypeRepository;

    /**
     * Save a areaType.
     *
     * @param areaType the entity to save
     * @return the persisted entity
     */
    public AreaType save(AreaType areaType) {
        log.debug("Request to save AreaType : {}", areaType);
        AreaType result = areaTypeRepository.save(areaType);
        return result;
    }

    /**
     *  Get all the areaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AreaType> findAll(Pageable pageable) {
        log.debug("Request to get all AreaTypes");
        Page<AreaType> result = areaTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one areaType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AreaType findOne(Long id) {
        log.debug("Request to get AreaType : {}", id);
        AreaType areaType = areaTypeRepository.findOne(id);
        return areaType;
    }

    /**
     *  Delete the  areaType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaType : {}", id);
        areaTypeRepository.delete(id);
    }
}
