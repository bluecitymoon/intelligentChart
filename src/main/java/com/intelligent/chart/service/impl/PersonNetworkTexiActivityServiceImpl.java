package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonNetworkTexiActivityService;
import com.intelligent.chart.domain.PersonNetworkTexiActivity;
import com.intelligent.chart.repository.PersonNetworkTexiActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonNetworkTexiActivity.
 */
@Service
@Transactional
public class PersonNetworkTexiActivityServiceImpl implements PersonNetworkTexiActivityService{

    private final Logger log = LoggerFactory.getLogger(PersonNetworkTexiActivityServiceImpl.class);
    
    @Inject
    private PersonNetworkTexiActivityRepository personNetworkTexiActivityRepository;

    /**
     * Save a personNetworkTexiActivity.
     *
     * @param personNetworkTexiActivity the entity to save
     * @return the persisted entity
     */
    public PersonNetworkTexiActivity save(PersonNetworkTexiActivity personNetworkTexiActivity) {
        log.debug("Request to save PersonNetworkTexiActivity : {}", personNetworkTexiActivity);
        PersonNetworkTexiActivity result = personNetworkTexiActivityRepository.save(personNetworkTexiActivity);
        return result;
    }

    /**
     *  Get all the personNetworkTexiActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonNetworkTexiActivity> findAll(Pageable pageable) {
        log.debug("Request to get all PersonNetworkTexiActivities");
        Page<PersonNetworkTexiActivity> result = personNetworkTexiActivityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personNetworkTexiActivity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonNetworkTexiActivity findOne(Long id) {
        log.debug("Request to get PersonNetworkTexiActivity : {}", id);
        PersonNetworkTexiActivity personNetworkTexiActivity = personNetworkTexiActivityRepository.findOne(id);
        return personNetworkTexiActivity;
    }

    /**
     *  Delete the  personNetworkTexiActivity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonNetworkTexiActivity : {}", id);
        personNetworkTexiActivityRepository.delete(id);
    }
}
