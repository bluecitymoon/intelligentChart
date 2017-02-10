package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonInnovationService;
import com.intelligent.chart.domain.PersonInnovation;
import com.intelligent.chart.repository.PersonInnovationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonInnovation.
 */
@Service
@Transactional
public class PersonInnovationServiceImpl implements PersonInnovationService{

    private final Logger log = LoggerFactory.getLogger(PersonInnovationServiceImpl.class);
    
    @Inject
    private PersonInnovationRepository personInnovationRepository;

    /**
     * Save a personInnovation.
     *
     * @param personInnovation the entity to save
     * @return the persisted entity
     */
    public PersonInnovation save(PersonInnovation personInnovation) {
        log.debug("Request to save PersonInnovation : {}", personInnovation);
        PersonInnovation result = personInnovationRepository.save(personInnovation);
        return result;
    }

    /**
     *  Get all the personInnovations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonInnovation> findAll(Pageable pageable) {
        log.debug("Request to get all PersonInnovations");
        Page<PersonInnovation> result = personInnovationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personInnovation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonInnovation findOne(Long id) {
        log.debug("Request to get PersonInnovation : {}", id);
        PersonInnovation personInnovation = personInnovationRepository.findOne(id);
        return personInnovation;
    }

    /**
     *  Delete the  personInnovation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonInnovation : {}", id);
        personInnovationRepository.delete(id);
    }
}
