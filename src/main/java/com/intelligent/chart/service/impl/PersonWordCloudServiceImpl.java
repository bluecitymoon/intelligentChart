package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonWordCloudService;
import com.intelligent.chart.domain.PersonWordCloud;
import com.intelligent.chart.repository.PersonWordCloudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonWordCloud.
 */
@Service
@Transactional
public class PersonWordCloudServiceImpl implements PersonWordCloudService{

    private final Logger log = LoggerFactory.getLogger(PersonWordCloudServiceImpl.class);
    
    @Inject
    private PersonWordCloudRepository personWordCloudRepository;

    /**
     * Save a personWordCloud.
     *
     * @param personWordCloud the entity to save
     * @return the persisted entity
     */
    public PersonWordCloud save(PersonWordCloud personWordCloud) {
        log.debug("Request to save PersonWordCloud : {}", personWordCloud);
        PersonWordCloud result = personWordCloudRepository.save(personWordCloud);
        return result;
    }

    /**
     *  Get all the personWordClouds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonWordCloud> findAll(Pageable pageable) {
        log.debug("Request to get all PersonWordClouds");
        Page<PersonWordCloud> result = personWordCloudRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personWordCloud by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonWordCloud findOne(Long id) {
        log.debug("Request to get PersonWordCloud : {}", id);
        PersonWordCloud personWordCloud = personWordCloudRepository.findOne(id);
        return personWordCloud;
    }

    /**
     *  Delete the  personWordCloud by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonWordCloud : {}", id);
        personWordCloudRepository.delete(id);
    }
}
