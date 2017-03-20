package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.InnovationTypeService;
import com.intelligent.chart.domain.InnovationType;
import com.intelligent.chart.repository.InnovationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing InnovationType.
 */
@Service
@Transactional
public class InnovationTypeServiceImpl implements InnovationTypeService{

    private final Logger log = LoggerFactory.getLogger(InnovationTypeServiceImpl.class);
    
    @Inject
    private InnovationTypeRepository innovationTypeRepository;

    /**
     * Save a innovationType.
     *
     * @param innovationType the entity to save
     * @return the persisted entity
     */
    public InnovationType save(InnovationType innovationType) {
        log.debug("Request to save InnovationType : {}", innovationType);
        InnovationType result = innovationTypeRepository.save(innovationType);
        return result;
    }

    /**
     *  Get all the innovationTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<InnovationType> findAll(Pageable pageable) {
        log.debug("Request to get all InnovationTypes");
        Page<InnovationType> result = innovationTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one innovationType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InnovationType findOne(Long id) {
        log.debug("Request to get InnovationType : {}", id);
        InnovationType innovationType = innovationTypeRepository.findOne(id);
        return innovationType;
    }

    /**
     *  Delete the  innovationType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InnovationType : {}", id);
        innovationTypeRepository.delete(id);
    }
}
