package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonSocialMediaService;
import com.intelligent.chart.domain.PersonSocialMedia;
import com.intelligent.chart.repository.PersonSocialMediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonSocialMedia.
 */
@Service
@Transactional
public class PersonSocialMediaServiceImpl implements PersonSocialMediaService{

    private final Logger log = LoggerFactory.getLogger(PersonSocialMediaServiceImpl.class);
    
    @Inject
    private PersonSocialMediaRepository personSocialMediaRepository;

    /**
     * Save a personSocialMedia.
     *
     * @param personSocialMedia the entity to save
     * @return the persisted entity
     */
    public PersonSocialMedia save(PersonSocialMedia personSocialMedia) {
        log.debug("Request to save PersonSocialMedia : {}", personSocialMedia);
        PersonSocialMedia result = personSocialMediaRepository.save(personSocialMedia);
        return result;
    }

    /**
     *  Get all the personSocialMedias.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonSocialMedia> findAll(Pageable pageable) {
        log.debug("Request to get all PersonSocialMedias");
        Page<PersonSocialMedia> result = personSocialMediaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personSocialMedia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonSocialMedia findOne(Long id) {
        log.debug("Request to get PersonSocialMedia : {}", id);
        PersonSocialMedia personSocialMedia = personSocialMediaRepository.findOne(id);
        return personSocialMedia;
    }

    /**
     *  Delete the  personSocialMedia by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonSocialMedia : {}", id);
        personSocialMediaRepository.delete(id);
    }
}
