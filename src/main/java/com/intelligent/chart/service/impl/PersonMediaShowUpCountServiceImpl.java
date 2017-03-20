package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonMediaShowUpCountService;
import com.intelligent.chart.domain.PersonMediaShowUpCount;
import com.intelligent.chart.repository.PersonMediaShowUpCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonMediaShowUpCount.
 */
@Service
@Transactional
public class PersonMediaShowUpCountServiceImpl implements PersonMediaShowUpCountService{

    private final Logger log = LoggerFactory.getLogger(PersonMediaShowUpCountServiceImpl.class);
    
    @Inject
    private PersonMediaShowUpCountRepository personMediaShowUpCountRepository;

    /**
     * Save a personMediaShowUpCount.
     *
     * @param personMediaShowUpCount the entity to save
     * @return the persisted entity
     */
    public PersonMediaShowUpCount save(PersonMediaShowUpCount personMediaShowUpCount) {
        log.debug("Request to save PersonMediaShowUpCount : {}", personMediaShowUpCount);
        PersonMediaShowUpCount result = personMediaShowUpCountRepository.save(personMediaShowUpCount);
        return result;
    }

    /**
     *  Get all the personMediaShowUpCounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonMediaShowUpCount> findAll(Pageable pageable) {
        log.debug("Request to get all PersonMediaShowUpCounts");
        Page<PersonMediaShowUpCount> result = personMediaShowUpCountRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personMediaShowUpCount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonMediaShowUpCount findOne(Long id) {
        log.debug("Request to get PersonMediaShowUpCount : {}", id);
        PersonMediaShowUpCount personMediaShowUpCount = personMediaShowUpCountRepository.findOne(id);
        return personMediaShowUpCount;
    }

    /**
     *  Delete the  personMediaShowUpCount by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonMediaShowUpCount : {}", id);
        personMediaShowUpCountRepository.delete(id);
    }
}
