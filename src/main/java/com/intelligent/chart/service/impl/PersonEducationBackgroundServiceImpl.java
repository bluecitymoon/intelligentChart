package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonEducationBackgroundService;
import com.intelligent.chart.domain.PersonEducationBackground;
import com.intelligent.chart.repository.PersonEducationBackgroundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonEducationBackground.
 */
@Service
@Transactional
public class PersonEducationBackgroundServiceImpl implements PersonEducationBackgroundService{

    private final Logger log = LoggerFactory.getLogger(PersonEducationBackgroundServiceImpl.class);
    
    @Inject
    private PersonEducationBackgroundRepository personEducationBackgroundRepository;

    /**
     * Save a personEducationBackground.
     *
     * @param personEducationBackground the entity to save
     * @return the persisted entity
     */
    public PersonEducationBackground save(PersonEducationBackground personEducationBackground) {
        log.debug("Request to save PersonEducationBackground : {}", personEducationBackground);
        PersonEducationBackground result = personEducationBackgroundRepository.save(personEducationBackground);
        return result;
    }

    /**
     *  Get all the personEducationBackgrounds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonEducationBackground> findAll(Pageable pageable) {
        log.debug("Request to get all PersonEducationBackgrounds");
        Page<PersonEducationBackground> result = personEducationBackgroundRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personEducationBackground by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonEducationBackground findOne(Long id) {
        log.debug("Request to get PersonEducationBackground : {}", id);
        PersonEducationBackground personEducationBackground = personEducationBackgroundRepository.findOne(id);
        return personEducationBackground;
    }

    /**
     *  Delete the  personEducationBackground by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonEducationBackground : {}", id);
        personEducationBackgroundRepository.delete(id);
    }
}
