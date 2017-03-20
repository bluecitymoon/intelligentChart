package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonSearchCountService;
import com.intelligent.chart.domain.PersonSearchCount;
import com.intelligent.chart.repository.PersonSearchCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonSearchCount.
 */
@Service
@Transactional
public class PersonSearchCountServiceImpl implements PersonSearchCountService{

    private final Logger log = LoggerFactory.getLogger(PersonSearchCountServiceImpl.class);
    
    @Inject
    private PersonSearchCountRepository personSearchCountRepository;

    /**
     * Save a personSearchCount.
     *
     * @param personSearchCount the entity to save
     * @return the persisted entity
     */
    public PersonSearchCount save(PersonSearchCount personSearchCount) {
        log.debug("Request to save PersonSearchCount : {}", personSearchCount);
        PersonSearchCount result = personSearchCountRepository.save(personSearchCount);
        return result;
    }

    /**
     *  Get all the personSearchCounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonSearchCount> findAll(Pageable pageable) {
        log.debug("Request to get all PersonSearchCounts");
        Page<PersonSearchCount> result = personSearchCountRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personSearchCount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonSearchCount findOne(Long id) {
        log.debug("Request to get PersonSearchCount : {}", id);
        PersonSearchCount personSearchCount = personSearchCountRepository.findOne(id);
        return personSearchCount;
    }

    /**
     *  Delete the  personSearchCount by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonSearchCount : {}", id);
        personSearchCountRepository.delete(id);
    }
}
