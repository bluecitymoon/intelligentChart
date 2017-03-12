package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFansHobbyService;
import com.intelligent.chart.domain.PersonFansHobby;
import com.intelligent.chart.repository.PersonFansHobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFansHobby.
 */
@Service
@Transactional
public class PersonFansHobbyServiceImpl implements PersonFansHobbyService{

    private final Logger log = LoggerFactory.getLogger(PersonFansHobbyServiceImpl.class);
    
    @Inject
    private PersonFansHobbyRepository personFansHobbyRepository;

    /**
     * Save a personFansHobby.
     *
     * @param personFansHobby the entity to save
     * @return the persisted entity
     */
    public PersonFansHobby save(PersonFansHobby personFansHobby) {
        log.debug("Request to save PersonFansHobby : {}", personFansHobby);
        PersonFansHobby result = personFansHobbyRepository.save(personFansHobby);
        return result;
    }

    /**
     *  Get all the personFansHobbies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFansHobby> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFansHobbies");
        Page<PersonFansHobby> result = personFansHobbyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFansHobby by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFansHobby findOne(Long id) {
        log.debug("Request to get PersonFansHobby : {}", id);
        PersonFansHobby personFansHobby = personFansHobbyRepository.findOne(id);
        return personFansHobby;
    }

    /**
     *  Delete the  personFansHobby by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFansHobby : {}", id);
        personFansHobbyRepository.delete(id);
    }
}
