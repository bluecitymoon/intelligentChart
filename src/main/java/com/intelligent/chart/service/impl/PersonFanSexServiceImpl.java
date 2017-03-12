package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFanSexService;
import com.intelligent.chart.domain.PersonFanSex;
import com.intelligent.chart.repository.PersonFanSexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFanSex.
 */
@Service
@Transactional
public class PersonFanSexServiceImpl implements PersonFanSexService{

    private final Logger log = LoggerFactory.getLogger(PersonFanSexServiceImpl.class);
    
    @Inject
    private PersonFanSexRepository personFanSexRepository;

    /**
     * Save a personFanSex.
     *
     * @param personFanSex the entity to save
     * @return the persisted entity
     */
    public PersonFanSex save(PersonFanSex personFanSex) {
        log.debug("Request to save PersonFanSex : {}", personFanSex);
        PersonFanSex result = personFanSexRepository.save(personFanSex);
        return result;
    }

    /**
     *  Get all the personFanSexes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFanSex> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFanSexes");
        Page<PersonFanSex> result = personFanSexRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFanSex by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFanSex findOne(Long id) {
        log.debug("Request to get PersonFanSex : {}", id);
        PersonFanSex personFanSex = personFanSexRepository.findOne(id);
        return personFanSex;
    }

    /**
     *  Delete the  personFanSex by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFanSex : {}", id);
        personFanSexRepository.delete(id);
    }
}
