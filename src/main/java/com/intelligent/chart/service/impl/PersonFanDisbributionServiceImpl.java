package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFanDisbributionService;
import com.intelligent.chart.domain.PersonFanDisbribution;
import com.intelligent.chart.repository.PersonFanDisbributionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFanDisbribution.
 */
@Service
@Transactional
public class PersonFanDisbributionServiceImpl implements PersonFanDisbributionService{

    private final Logger log = LoggerFactory.getLogger(PersonFanDisbributionServiceImpl.class);
    
    @Inject
    private PersonFanDisbributionRepository personFanDisbributionRepository;

    /**
     * Save a personFanDisbribution.
     *
     * @param personFanDisbribution the entity to save
     * @return the persisted entity
     */
    public PersonFanDisbribution save(PersonFanDisbribution personFanDisbribution) {
        log.debug("Request to save PersonFanDisbribution : {}", personFanDisbribution);
        PersonFanDisbribution result = personFanDisbributionRepository.save(personFanDisbribution);
        return result;
    }

    /**
     *  Get all the personFanDisbributions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFanDisbribution> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFanDisbributions");
        Page<PersonFanDisbribution> result = personFanDisbributionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFanDisbribution by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFanDisbribution findOne(Long id) {
        log.debug("Request to get PersonFanDisbribution : {}", id);
        PersonFanDisbribution personFanDisbribution = personFanDisbributionRepository.findOne(id);
        return personFanDisbribution;
    }

    /**
     *  Delete the  personFanDisbribution by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFanDisbribution : {}", id);
        personFanDisbributionRepository.delete(id);
    }
}
