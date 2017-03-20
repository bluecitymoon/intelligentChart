package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFansEducationLevelService;
import com.intelligent.chart.domain.PersonFansEducationLevel;
import com.intelligent.chart.repository.PersonFansEducationLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFansEducationLevel.
 */
@Service
@Transactional
public class PersonFansEducationLevelServiceImpl implements PersonFansEducationLevelService{

    private final Logger log = LoggerFactory.getLogger(PersonFansEducationLevelServiceImpl.class);
    
    @Inject
    private PersonFansEducationLevelRepository personFansEducationLevelRepository;

    /**
     * Save a personFansEducationLevel.
     *
     * @param personFansEducationLevel the entity to save
     * @return the persisted entity
     */
    public PersonFansEducationLevel save(PersonFansEducationLevel personFansEducationLevel) {
        log.debug("Request to save PersonFansEducationLevel : {}", personFansEducationLevel);
        PersonFansEducationLevel result = personFansEducationLevelRepository.save(personFansEducationLevel);
        return result;
    }

    /**
     *  Get all the personFansEducationLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFansEducationLevel> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFansEducationLevels");
        Page<PersonFansEducationLevel> result = personFansEducationLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFansEducationLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFansEducationLevel findOne(Long id) {
        log.debug("Request to get PersonFansEducationLevel : {}", id);
        PersonFansEducationLevel personFansEducationLevel = personFansEducationLevelRepository.findOne(id);
        return personFansEducationLevel;
    }

    /**
     *  Delete the  personFansEducationLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFansEducationLevel : {}", id);
        personFansEducationLevelRepository.delete(id);
    }
}
