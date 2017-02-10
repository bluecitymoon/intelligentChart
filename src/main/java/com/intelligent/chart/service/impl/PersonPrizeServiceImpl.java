package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonPrizeService;
import com.intelligent.chart.domain.PersonPrize;
import com.intelligent.chart.repository.PersonPrizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonPrize.
 */
@Service
@Transactional
public class PersonPrizeServiceImpl implements PersonPrizeService{

    private final Logger log = LoggerFactory.getLogger(PersonPrizeServiceImpl.class);
    
    @Inject
    private PersonPrizeRepository personPrizeRepository;

    /**
     * Save a personPrize.
     *
     * @param personPrize the entity to save
     * @return the persisted entity
     */
    public PersonPrize save(PersonPrize personPrize) {
        log.debug("Request to save PersonPrize : {}", personPrize);
        PersonPrize result = personPrizeRepository.save(personPrize);
        return result;
    }

    /**
     *  Get all the personPrizes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonPrize> findAll(Pageable pageable) {
        log.debug("Request to get all PersonPrizes");
        Page<PersonPrize> result = personPrizeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personPrize by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonPrize findOne(Long id) {
        log.debug("Request to get PersonPrize : {}", id);
        PersonPrize personPrize = personPrizeRepository.findOne(id);
        return personPrize;
    }

    /**
     *  Delete the  personPrize by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonPrize : {}", id);
        personPrizeRepository.delete(id);
    }
}
