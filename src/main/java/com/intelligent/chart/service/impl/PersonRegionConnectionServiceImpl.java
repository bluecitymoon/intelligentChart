package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonRegionConnectionService;
import com.intelligent.chart.domain.PersonRegionConnection;
import com.intelligent.chart.repository.PersonRegionConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonRegionConnection.
 */
@Service
@Transactional
public class PersonRegionConnectionServiceImpl implements PersonRegionConnectionService{

    private final Logger log = LoggerFactory.getLogger(PersonRegionConnectionServiceImpl.class);
    
    @Inject
    private PersonRegionConnectionRepository personRegionConnectionRepository;

    /**
     * Save a personRegionConnection.
     *
     * @param personRegionConnection the entity to save
     * @return the persisted entity
     */
    public PersonRegionConnection save(PersonRegionConnection personRegionConnection) {
        log.debug("Request to save PersonRegionConnection : {}", personRegionConnection);
        PersonRegionConnection result = personRegionConnectionRepository.save(personRegionConnection);
        return result;
    }

    /**
     *  Get all the personRegionConnections.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonRegionConnection> findAll(Pageable pageable) {
        log.debug("Request to get all PersonRegionConnections");
        Page<PersonRegionConnection> result = personRegionConnectionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personRegionConnection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonRegionConnection findOne(Long id) {
        log.debug("Request to get PersonRegionConnection : {}", id);
        PersonRegionConnection personRegionConnection = personRegionConnectionRepository.findOne(id);
        return personRegionConnection;
    }

    /**
     *  Delete the  personRegionConnection by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonRegionConnection : {}", id);
        personRegionConnectionRepository.delete(id);
    }
}
