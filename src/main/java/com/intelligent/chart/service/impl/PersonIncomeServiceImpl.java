package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonIncomeService;
import com.intelligent.chart.domain.PersonIncome;
import com.intelligent.chart.repository.PersonIncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonIncome.
 */
@Service
@Transactional
public class PersonIncomeServiceImpl implements PersonIncomeService{

    private final Logger log = LoggerFactory.getLogger(PersonIncomeServiceImpl.class);
    
    @Inject
    private PersonIncomeRepository personIncomeRepository;

    /**
     * Save a personIncome.
     *
     * @param personIncome the entity to save
     * @return the persisted entity
     */
    public PersonIncome save(PersonIncome personIncome) {
        log.debug("Request to save PersonIncome : {}", personIncome);
        PersonIncome result = personIncomeRepository.save(personIncome);
        return result;
    }

    /**
     *  Get all the personIncomes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonIncome> findAll(Pageable pageable) {
        log.debug("Request to get all PersonIncomes");
        Page<PersonIncome> result = personIncomeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personIncome by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonIncome findOne(Long id) {
        log.debug("Request to get PersonIncome : {}", id);
        PersonIncome personIncome = personIncomeRepository.findOne(id);
        return personIncome;
    }

    /**
     *  Delete the  personIncome by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonIncome : {}", id);
        personIncomeRepository.delete(id);
    }
}
