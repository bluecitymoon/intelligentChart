package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.LawBusinessTypeService;
import com.intelligent.chart.domain.LawBusinessType;
import com.intelligent.chart.repository.LawBusinessTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing LawBusinessType.
 */
@Service
@Transactional
public class LawBusinessTypeServiceImpl implements LawBusinessTypeService{

    private final Logger log = LoggerFactory.getLogger(LawBusinessTypeServiceImpl.class);
    
    @Inject
    private LawBusinessTypeRepository lawBusinessTypeRepository;

    /**
     * Save a lawBusinessType.
     *
     * @param lawBusinessType the entity to save
     * @return the persisted entity
     */
    public LawBusinessType save(LawBusinessType lawBusinessType) {
        log.debug("Request to save LawBusinessType : {}", lawBusinessType);
        LawBusinessType result = lawBusinessTypeRepository.save(lawBusinessType);
        return result;
    }

    /**
     *  Get all the lawBusinessTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LawBusinessType> findAll(Pageable pageable) {
        log.debug("Request to get all LawBusinessTypes");
        Page<LawBusinessType> result = lawBusinessTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one lawBusinessType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LawBusinessType findOne(Long id) {
        log.debug("Request to get LawBusinessType : {}", id);
        LawBusinessType lawBusinessType = lawBusinessTypeRepository.findOne(id);
        return lawBusinessType;
    }

    /**
     *  Delete the  lawBusinessType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LawBusinessType : {}", id);
        lawBusinessTypeRepository.delete(id);
    }
}
