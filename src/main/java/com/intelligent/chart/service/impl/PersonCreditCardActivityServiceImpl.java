package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonCreditCardActivityService;
import com.intelligent.chart.domain.PersonCreditCardActivity;
import com.intelligent.chart.repository.PersonCreditCardActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonCreditCardActivity.
 */
@Service
@Transactional
public class PersonCreditCardActivityServiceImpl implements PersonCreditCardActivityService{

    private final Logger log = LoggerFactory.getLogger(PersonCreditCardActivityServiceImpl.class);
    
    @Inject
    private PersonCreditCardActivityRepository personCreditCardActivityRepository;

    /**
     * Save a personCreditCardActivity.
     *
     * @param personCreditCardActivity the entity to save
     * @return the persisted entity
     */
    public PersonCreditCardActivity save(PersonCreditCardActivity personCreditCardActivity) {
        log.debug("Request to save PersonCreditCardActivity : {}", personCreditCardActivity);
        PersonCreditCardActivity result = personCreditCardActivityRepository.save(personCreditCardActivity);
        return result;
    }

    /**
     *  Get all the personCreditCardActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonCreditCardActivity> findAll(Pageable pageable) {
        log.debug("Request to get all PersonCreditCardActivities");
        Page<PersonCreditCardActivity> result = personCreditCardActivityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personCreditCardActivity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonCreditCardActivity findOne(Long id) {
        log.debug("Request to get PersonCreditCardActivity : {}", id);
        PersonCreditCardActivity personCreditCardActivity = personCreditCardActivityRepository.findOne(id);
        return personCreditCardActivity;
    }

    /**
     *  Delete the  personCreditCardActivity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonCreditCardActivity : {}", id);
        personCreditCardActivityRepository.delete(id);
    }
}
