package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFansEgeLevelService;
import com.intelligent.chart.domain.PersonFansEgeLevel;
import com.intelligent.chart.repository.PersonFansEgeLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFansEgeLevel.
 */
@Service
@Transactional
public class PersonFansEgeLevelServiceImpl implements PersonFansEgeLevelService{

    private final Logger log = LoggerFactory.getLogger(PersonFansEgeLevelServiceImpl.class);
    
    @Inject
    private PersonFansEgeLevelRepository personFansEgeLevelRepository;

    /**
     * Save a personFansEgeLevel.
     *
     * @param personFansEgeLevel the entity to save
     * @return the persisted entity
     */
    public PersonFansEgeLevel save(PersonFansEgeLevel personFansEgeLevel) {
        log.debug("Request to save PersonFansEgeLevel : {}", personFansEgeLevel);
        PersonFansEgeLevel result = personFansEgeLevelRepository.save(personFansEgeLevel);
        return result;
    }

    /**
     *  Get all the personFansEgeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFansEgeLevel> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFansEgeLevels");
        Page<PersonFansEgeLevel> result = personFansEgeLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFansEgeLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFansEgeLevel findOne(Long id) {
        log.debug("Request to get PersonFansEgeLevel : {}", id);
        PersonFansEgeLevel personFansEgeLevel = personFansEgeLevelRepository.findOne(id);
        return personFansEgeLevel;
    }

    /**
     *  Delete the  personFansEgeLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFansEgeLevel : {}", id);
        personFansEgeLevelRepository.delete(id);
    }
}
