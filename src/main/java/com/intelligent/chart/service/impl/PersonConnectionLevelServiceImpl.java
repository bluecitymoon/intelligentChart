package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonConnectionLevelService;
import com.intelligent.chart.domain.PersonConnectionLevel;
import com.intelligent.chart.repository.PersonConnectionLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonConnectionLevel.
 */
@Service
@Transactional
public class PersonConnectionLevelServiceImpl implements PersonConnectionLevelService{

    private final Logger log = LoggerFactory.getLogger(PersonConnectionLevelServiceImpl.class);
    
    @Inject
    private PersonConnectionLevelRepository personConnectionLevelRepository;

    /**
     * Save a personConnectionLevel.
     *
     * @param personConnectionLevel the entity to save
     * @return the persisted entity
     */
    public PersonConnectionLevel save(PersonConnectionLevel personConnectionLevel) {
        log.debug("Request to save PersonConnectionLevel : {}", personConnectionLevel);
        PersonConnectionLevel result = personConnectionLevelRepository.save(personConnectionLevel);
        return result;
    }

    /**
     *  Get all the personConnectionLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonConnectionLevel> findAll(Pageable pageable) {
        log.debug("Request to get all PersonConnectionLevels");
        Page<PersonConnectionLevel> result = personConnectionLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personConnectionLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonConnectionLevel findOne(Long id) {
        log.debug("Request to get PersonConnectionLevel : {}", id);
        PersonConnectionLevel personConnectionLevel = personConnectionLevelRepository.findOne(id);
        return personConnectionLevel;
    }

    /**
     *  Delete the  personConnectionLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonConnectionLevel : {}", id);
        personConnectionLevelRepository.delete(id);
    }
}
