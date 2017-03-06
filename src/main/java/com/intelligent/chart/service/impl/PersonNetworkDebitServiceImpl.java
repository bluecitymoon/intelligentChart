package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonNetworkDebitService;
import com.intelligent.chart.domain.PersonNetworkDebit;
import com.intelligent.chart.repository.PersonNetworkDebitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonNetworkDebit.
 */
@Service
@Transactional
public class PersonNetworkDebitServiceImpl implements PersonNetworkDebitService{

    private final Logger log = LoggerFactory.getLogger(PersonNetworkDebitServiceImpl.class);
    
    @Inject
    private PersonNetworkDebitRepository personNetworkDebitRepository;

    /**
     * Save a personNetworkDebit.
     *
     * @param personNetworkDebit the entity to save
     * @return the persisted entity
     */
    public PersonNetworkDebit save(PersonNetworkDebit personNetworkDebit) {
        log.debug("Request to save PersonNetworkDebit : {}", personNetworkDebit);
        PersonNetworkDebit result = personNetworkDebitRepository.save(personNetworkDebit);
        return result;
    }

    /**
     *  Get all the personNetworkDebits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonNetworkDebit> findAll(Pageable pageable) {
        log.debug("Request to get all PersonNetworkDebits");
        Page<PersonNetworkDebit> result = personNetworkDebitRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personNetworkDebit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonNetworkDebit findOne(Long id) {
        log.debug("Request to get PersonNetworkDebit : {}", id);
        PersonNetworkDebit personNetworkDebit = personNetworkDebitRepository.findOne(id);
        return personNetworkDebit;
    }

    /**
     *  Delete the  personNetworkDebit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonNetworkDebit : {}", id);
        personNetworkDebitRepository.delete(id);
    }
}
