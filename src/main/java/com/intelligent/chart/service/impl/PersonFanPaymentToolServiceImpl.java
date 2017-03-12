package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFanPaymentToolService;
import com.intelligent.chart.domain.PersonFanPaymentTool;
import com.intelligent.chart.repository.PersonFanPaymentToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFanPaymentTool.
 */
@Service
@Transactional
public class PersonFanPaymentToolServiceImpl implements PersonFanPaymentToolService{

    private final Logger log = LoggerFactory.getLogger(PersonFanPaymentToolServiceImpl.class);
    
    @Inject
    private PersonFanPaymentToolRepository personFanPaymentToolRepository;

    /**
     * Save a personFanPaymentTool.
     *
     * @param personFanPaymentTool the entity to save
     * @return the persisted entity
     */
    public PersonFanPaymentTool save(PersonFanPaymentTool personFanPaymentTool) {
        log.debug("Request to save PersonFanPaymentTool : {}", personFanPaymentTool);
        PersonFanPaymentTool result = personFanPaymentToolRepository.save(personFanPaymentTool);
        return result;
    }

    /**
     *  Get all the personFanPaymentTools.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFanPaymentTool> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFanPaymentTools");
        Page<PersonFanPaymentTool> result = personFanPaymentToolRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFanPaymentTool by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFanPaymentTool findOne(Long id) {
        log.debug("Request to get PersonFanPaymentTool : {}", id);
        PersonFanPaymentTool personFanPaymentTool = personFanPaymentToolRepository.findOne(id);
        return personFanPaymentTool;
    }

    /**
     *  Delete the  personFanPaymentTool by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFanPaymentTool : {}", id);
        personFanPaymentToolRepository.delete(id);
    }
}
