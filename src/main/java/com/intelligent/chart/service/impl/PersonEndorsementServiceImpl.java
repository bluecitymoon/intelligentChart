package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonEndorsementService;
import com.intelligent.chart.domain.PersonEndorsement;
import com.intelligent.chart.repository.PersonEndorsementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonEndorsement.
 */
@Service
@Transactional
public class PersonEndorsementServiceImpl implements PersonEndorsementService{

    private final Logger log = LoggerFactory.getLogger(PersonEndorsementServiceImpl.class);
    
    @Inject
    private PersonEndorsementRepository personEndorsementRepository;

    /**
     * Save a personEndorsement.
     *
     * @param personEndorsement the entity to save
     * @return the persisted entity
     */
    public PersonEndorsement save(PersonEndorsement personEndorsement) {
        log.debug("Request to save PersonEndorsement : {}", personEndorsement);
        PersonEndorsement result = personEndorsementRepository.save(personEndorsement);
        return result;
    }

    /**
     *  Get all the personEndorsements.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonEndorsement> findAll(Pageable pageable) {
        log.debug("Request to get all PersonEndorsements");
        Page<PersonEndorsement> result = personEndorsementRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personEndorsement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonEndorsement findOne(Long id) {
        log.debug("Request to get PersonEndorsement : {}", id);
        PersonEndorsement personEndorsement = personEndorsementRepository.findOne(id);
        return personEndorsement;
    }

    /**
     *  Delete the  personEndorsement by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonEndorsement : {}", id);
        personEndorsementRepository.delete(id);
    }
}
