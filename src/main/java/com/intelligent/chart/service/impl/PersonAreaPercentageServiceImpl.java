package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonAreaPercentageService;
import com.intelligent.chart.domain.PersonAreaPercentage;
import com.intelligent.chart.repository.PersonAreaPercentageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonAreaPercentage.
 */
@Service
@Transactional
public class PersonAreaPercentageServiceImpl implements PersonAreaPercentageService{

    private final Logger log = LoggerFactory.getLogger(PersonAreaPercentageServiceImpl.class);
    
    @Inject
    private PersonAreaPercentageRepository personAreaPercentageRepository;

    /**
     * Save a personAreaPercentage.
     *
     * @param personAreaPercentage the entity to save
     * @return the persisted entity
     */
    public PersonAreaPercentage save(PersonAreaPercentage personAreaPercentage) {
        log.debug("Request to save PersonAreaPercentage : {}", personAreaPercentage);
        PersonAreaPercentage result = personAreaPercentageRepository.save(personAreaPercentage);
        return result;
    }

    /**
     *  Get all the personAreaPercentages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonAreaPercentage> findAll(Pageable pageable) {
        log.debug("Request to get all PersonAreaPercentages");
        Page<PersonAreaPercentage> result = personAreaPercentageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personAreaPercentage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonAreaPercentage findOne(Long id) {
        log.debug("Request to get PersonAreaPercentage : {}", id);
        PersonAreaPercentage personAreaPercentage = personAreaPercentageRepository.findOne(id);
        return personAreaPercentage;
    }

    /**
     *  Delete the  personAreaPercentage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonAreaPercentage : {}", id);
        personAreaPercentageRepository.delete(id);
    }
}
