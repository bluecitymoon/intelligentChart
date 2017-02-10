package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonExperienceService;
import com.intelligent.chart.domain.PersonExperience;
import com.intelligent.chart.repository.PersonExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonExperience.
 */
@Service
@Transactional
public class PersonExperienceServiceImpl implements PersonExperienceService{

    private final Logger log = LoggerFactory.getLogger(PersonExperienceServiceImpl.class);
    
    @Inject
    private PersonExperienceRepository personExperienceRepository;

    /**
     * Save a personExperience.
     *
     * @param personExperience the entity to save
     * @return the persisted entity
     */
    public PersonExperience save(PersonExperience personExperience) {
        log.debug("Request to save PersonExperience : {}", personExperience);
        PersonExperience result = personExperienceRepository.save(personExperience);
        return result;
    }

    /**
     *  Get all the personExperiences.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonExperience> findAll(Pageable pageable) {
        log.debug("Request to get all PersonExperiences");
        Page<PersonExperience> result = personExperienceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personExperience by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonExperience findOne(Long id) {
        log.debug("Request to get PersonExperience : {}", id);
        PersonExperience personExperience = personExperienceRepository.findOne(id);
        return personExperience;
    }

    /**
     *  Delete the  personExperience by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonExperience : {}", id);
        personExperienceRepository.delete(id);
    }
}
