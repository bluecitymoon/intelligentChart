package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonTieBaService;
import com.intelligent.chart.domain.PersonTieBa;
import com.intelligent.chart.repository.PersonTieBaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonTieBa.
 */
@Service
@Transactional
public class PersonTieBaServiceImpl implements PersonTieBaService{

    private final Logger log = LoggerFactory.getLogger(PersonTieBaServiceImpl.class);
    
    @Inject
    private PersonTieBaRepository personTieBaRepository;

    /**
     * Save a personTieBa.
     *
     * @param personTieBa the entity to save
     * @return the persisted entity
     */
    public PersonTieBa save(PersonTieBa personTieBa) {
        log.debug("Request to save PersonTieBa : {}", personTieBa);
        PersonTieBa result = personTieBaRepository.save(personTieBa);
        return result;
    }

    /**
     *  Get all the personTieBas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonTieBa> findAll(Pageable pageable) {
        log.debug("Request to get all PersonTieBas");
        Page<PersonTieBa> result = personTieBaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personTieBa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonTieBa findOne(Long id) {
        log.debug("Request to get PersonTieBa : {}", id);
        PersonTieBa personTieBa = personTieBaRepository.findOne(id);
        return personTieBa;
    }

    /**
     *  Delete the  personTieBa by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonTieBa : {}", id);
        personTieBaRepository.delete(id);
    }
}
