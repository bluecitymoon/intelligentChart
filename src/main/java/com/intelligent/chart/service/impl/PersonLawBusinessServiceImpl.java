package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonLawBusinessService;
import com.intelligent.chart.domain.PersonLawBusiness;
import com.intelligent.chart.repository.PersonLawBusinessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonLawBusiness.
 */
@Service
@Transactional
public class PersonLawBusinessServiceImpl implements PersonLawBusinessService{

    private final Logger log = LoggerFactory.getLogger(PersonLawBusinessServiceImpl.class);
    
    @Inject
    private PersonLawBusinessRepository personLawBusinessRepository;

    /**
     * Save a personLawBusiness.
     *
     * @param personLawBusiness the entity to save
     * @return the persisted entity
     */
    public PersonLawBusiness save(PersonLawBusiness personLawBusiness) {
        log.debug("Request to save PersonLawBusiness : {}", personLawBusiness);
        PersonLawBusiness result = personLawBusinessRepository.save(personLawBusiness);
        return result;
    }

    /**
     *  Get all the personLawBusinesses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonLawBusiness> findAll(Pageable pageable) {
        log.debug("Request to get all PersonLawBusinesses");
        Page<PersonLawBusiness> result = personLawBusinessRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personLawBusiness by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonLawBusiness findOne(Long id) {
        log.debug("Request to get PersonLawBusiness : {}", id);
        PersonLawBusiness personLawBusiness = personLawBusinessRepository.findOne(id);
        return personLawBusiness;
    }

    /**
     *  Delete the  personLawBusiness by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonLawBusiness : {}", id);
        personLawBusinessRepository.delete(id);
    }
}
