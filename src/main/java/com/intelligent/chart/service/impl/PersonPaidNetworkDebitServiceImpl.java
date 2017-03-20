package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonPaidNetworkDebitService;
import com.intelligent.chart.domain.PersonPaidNetworkDebit;
import com.intelligent.chart.repository.PersonPaidNetworkDebitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonPaidNetworkDebit.
 */
@Service
@Transactional
public class PersonPaidNetworkDebitServiceImpl implements PersonPaidNetworkDebitService{

    private final Logger log = LoggerFactory.getLogger(PersonPaidNetworkDebitServiceImpl.class);
    
    @Inject
    private PersonPaidNetworkDebitRepository personPaidNetworkDebitRepository;

    /**
     * Save a personPaidNetworkDebit.
     *
     * @param personPaidNetworkDebit the entity to save
     * @return the persisted entity
     */
    public PersonPaidNetworkDebit save(PersonPaidNetworkDebit personPaidNetworkDebit) {
        log.debug("Request to save PersonPaidNetworkDebit : {}", personPaidNetworkDebit);
        PersonPaidNetworkDebit result = personPaidNetworkDebitRepository.save(personPaidNetworkDebit);
        return result;
    }

    /**
     *  Get all the personPaidNetworkDebits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonPaidNetworkDebit> findAll(Pageable pageable) {
        log.debug("Request to get all PersonPaidNetworkDebits");
        Page<PersonPaidNetworkDebit> result = personPaidNetworkDebitRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personPaidNetworkDebit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonPaidNetworkDebit findOne(Long id) {
        log.debug("Request to get PersonPaidNetworkDebit : {}", id);
        PersonPaidNetworkDebit personPaidNetworkDebit = personPaidNetworkDebitRepository.findOne(id);
        return personPaidNetworkDebit;
    }

    /**
     *  Delete the  personPaidNetworkDebit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonPaidNetworkDebit : {}", id);
        personPaidNetworkDebitRepository.delete(id);
    }
}
