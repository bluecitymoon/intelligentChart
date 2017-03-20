package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonTaxiActivityService;
import com.intelligent.chart.domain.PersonTaxiActivity;
import com.intelligent.chart.repository.PersonTaxiActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonTaxiActivity.
 */
@Service
@Transactional
public class PersonTaxiActivityServiceImpl implements PersonTaxiActivityService{

    private final Logger log = LoggerFactory.getLogger(PersonTaxiActivityServiceImpl.class);
    
    @Inject
    private PersonTaxiActivityRepository personTaxiActivityRepository;

    /**
     * Save a personTaxiActivity.
     *
     * @param personTaxiActivity the entity to save
     * @return the persisted entity
     */
    public PersonTaxiActivity save(PersonTaxiActivity personTaxiActivity) {
        log.debug("Request to save PersonTaxiActivity : {}", personTaxiActivity);
        PersonTaxiActivity result = personTaxiActivityRepository.save(personTaxiActivity);
        return result;
    }

    /**
     *  Get all the personTaxiActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonTaxiActivity> findAll(Pageable pageable) {
        log.debug("Request to get all PersonTaxiActivities");
        Page<PersonTaxiActivity> result = personTaxiActivityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personTaxiActivity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonTaxiActivity findOne(Long id) {
        log.debug("Request to get PersonTaxiActivity : {}", id);
        PersonTaxiActivity personTaxiActivity = personTaxiActivityRepository.findOne(id);
        return personTaxiActivity;
    }

    /**
     *  Delete the  personTaxiActivity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonTaxiActivity : {}", id);
        personTaxiActivityRepository.delete(id);
    }
}
