package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonPopularityService;
import com.intelligent.chart.domain.PersonPopularity;
import com.intelligent.chart.repository.PersonPopularityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonPopularity.
 */
@Service
@Transactional
public class PersonPopularityServiceImpl implements PersonPopularityService{

    private final Logger log = LoggerFactory.getLogger(PersonPopularityServiceImpl.class);
    
    @Inject
    private PersonPopularityRepository personPopularityRepository;

    /**
     * Save a personPopularity.
     *
     * @param personPopularity the entity to save
     * @return the persisted entity
     */
    public PersonPopularity save(PersonPopularity personPopularity) {
        log.debug("Request to save PersonPopularity : {}", personPopularity);
        PersonPopularity result = personPopularityRepository.save(personPopularity);
        return result;
    }

    /**
     *  Get all the personPopularities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonPopularity> findAll(Pageable pageable) {
        log.debug("Request to get all PersonPopularities");
        Page<PersonPopularity> result = personPopularityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personPopularity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonPopularity findOne(Long id) {
        log.debug("Request to get PersonPopularity : {}", id);
        PersonPopularity personPopularity = personPopularityRepository.findOne(id);
        return personPopularity;
    }

    /**
     *  Delete the  personPopularity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonPopularity : {}", id);
        personPopularityRepository.delete(id);
    }
}
