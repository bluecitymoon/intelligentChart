package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.HobbyService;
import com.intelligent.chart.domain.Hobby;
import com.intelligent.chart.repository.HobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Hobby.
 */
@Service
@Transactional
public class HobbyServiceImpl implements HobbyService{

    private final Logger log = LoggerFactory.getLogger(HobbyServiceImpl.class);
    
    @Inject
    private HobbyRepository hobbyRepository;

    /**
     * Save a hobby.
     *
     * @param hobby the entity to save
     * @return the persisted entity
     */
    public Hobby save(Hobby hobby) {
        log.debug("Request to save Hobby : {}", hobby);
        Hobby result = hobbyRepository.save(hobby);
        return result;
    }

    /**
     *  Get all the hobbies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Hobby> findAll(Pageable pageable) {
        log.debug("Request to get all Hobbies");
        Page<Hobby> result = hobbyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one hobby by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Hobby findOne(Long id) {
        log.debug("Request to get Hobby : {}", id);
        Hobby hobby = hobbyRepository.findOne(id);
        return hobby;
    }

    /**
     *  Delete the  hobby by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Hobby : {}", id);
        hobbyRepository.delete(id);
    }
}
