package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.SexService;
import com.intelligent.chart.domain.Sex;
import com.intelligent.chart.repository.SexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Sex.
 */
@Service
@Transactional
public class SexServiceImpl implements SexService{

    private final Logger log = LoggerFactory.getLogger(SexServiceImpl.class);
    
    @Inject
    private SexRepository sexRepository;

    /**
     * Save a sex.
     *
     * @param sex the entity to save
     * @return the persisted entity
     */
    public Sex save(Sex sex) {
        log.debug("Request to save Sex : {}", sex);
        Sex result = sexRepository.save(sex);
        return result;
    }

    /**
     *  Get all the sexes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Sex> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        Page<Sex> result = sexRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sex by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Sex findOne(Long id) {
        log.debug("Request to get Sex : {}", id);
        Sex sex = sexRepository.findOne(id);
        return sex;
    }

    /**
     *  Delete the  sex by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sex : {}", id);
        sexRepository.delete(id);
    }
}
