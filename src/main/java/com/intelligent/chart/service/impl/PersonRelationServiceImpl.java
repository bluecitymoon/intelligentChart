package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonRelationService;
import com.intelligent.chart.domain.PersonRelation;
import com.intelligent.chart.repository.PersonRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonRelation.
 */
@Service
@Transactional
public class PersonRelationServiceImpl implements PersonRelationService{

    private final Logger log = LoggerFactory.getLogger(PersonRelationServiceImpl.class);
    
    @Inject
    private PersonRelationRepository personRelationRepository;

    /**
     * Save a personRelation.
     *
     * @param personRelation the entity to save
     * @return the persisted entity
     */
    public PersonRelation save(PersonRelation personRelation) {
        log.debug("Request to save PersonRelation : {}", personRelation);
        PersonRelation result = personRelationRepository.save(personRelation);
        return result;
    }

    /**
     *  Get all the personRelations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonRelation> findAll(Pageable pageable) {
        log.debug("Request to get all PersonRelations");
        Page<PersonRelation> result = personRelationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personRelation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonRelation findOne(Long id) {
        log.debug("Request to get PersonRelation : {}", id);
        PersonRelation personRelation = personRelationRepository.findOne(id);
        return personRelation;
    }

    /**
     *  Delete the  personRelation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonRelation : {}", id);
        personRelationRepository.delete(id);
    }
}
