package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonSocialHotDiscussService;
import com.intelligent.chart.domain.PersonSocialHotDiscuss;
import com.intelligent.chart.repository.PersonSocialHotDiscussRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonSocialHotDiscuss.
 */
@Service
@Transactional
public class PersonSocialHotDiscussServiceImpl implements PersonSocialHotDiscussService{

    private final Logger log = LoggerFactory.getLogger(PersonSocialHotDiscussServiceImpl.class);
    
    @Inject
    private PersonSocialHotDiscussRepository personSocialHotDiscussRepository;

    /**
     * Save a personSocialHotDiscuss.
     *
     * @param personSocialHotDiscuss the entity to save
     * @return the persisted entity
     */
    public PersonSocialHotDiscuss save(PersonSocialHotDiscuss personSocialHotDiscuss) {
        log.debug("Request to save PersonSocialHotDiscuss : {}", personSocialHotDiscuss);
        PersonSocialHotDiscuss result = personSocialHotDiscussRepository.save(personSocialHotDiscuss);
        return result;
    }

    /**
     *  Get all the personSocialHotDiscusses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonSocialHotDiscuss> findAll(Pageable pageable) {
        log.debug("Request to get all PersonSocialHotDiscusses");
        Page<PersonSocialHotDiscuss> result = personSocialHotDiscussRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personSocialHotDiscuss by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonSocialHotDiscuss findOne(Long id) {
        log.debug("Request to get PersonSocialHotDiscuss : {}", id);
        PersonSocialHotDiscuss personSocialHotDiscuss = personSocialHotDiscussRepository.findOne(id);
        return personSocialHotDiscuss;
    }

    /**
     *  Delete the  personSocialHotDiscuss by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonSocialHotDiscuss : {}", id);
        personSocialHotDiscussRepository.delete(id);
    }
}
