package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFansPucharsingPowerService;
import com.intelligent.chart.domain.PersonFansPucharsingPower;
import com.intelligent.chart.repository.PersonFansPucharsingPowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFansPucharsingPower.
 */
@Service
@Transactional
public class PersonFansPucharsingPowerServiceImpl implements PersonFansPucharsingPowerService{

    private final Logger log = LoggerFactory.getLogger(PersonFansPucharsingPowerServiceImpl.class);
    
    @Inject
    private PersonFansPucharsingPowerRepository personFansPucharsingPowerRepository;

    /**
     * Save a personFansPucharsingPower.
     *
     * @param personFansPucharsingPower the entity to save
     * @return the persisted entity
     */
    public PersonFansPucharsingPower save(PersonFansPucharsingPower personFansPucharsingPower) {
        log.debug("Request to save PersonFansPucharsingPower : {}", personFansPucharsingPower);
        PersonFansPucharsingPower result = personFansPucharsingPowerRepository.save(personFansPucharsingPower);
        return result;
    }

    /**
     *  Get all the personFansPucharsingPowers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFansPucharsingPower> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFansPucharsingPowers");
        Page<PersonFansPucharsingPower> result = personFansPucharsingPowerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFansPucharsingPower by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFansPucharsingPower findOne(Long id) {
        log.debug("Request to get PersonFansPucharsingPower : {}", id);
        PersonFansPucharsingPower personFansPucharsingPower = personFansPucharsingPowerRepository.findOne(id);
        return personFansPucharsingPower;
    }

    /**
     *  Delete the  personFansPucharsingPower by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFansPucharsingPower : {}", id);
        personFansPucharsingPowerRepository.delete(id);
    }
}
