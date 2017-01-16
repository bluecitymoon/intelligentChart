package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.DirectorService;
import com.intelligent.chart.domain.Director;
import com.intelligent.chart.repository.DirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Director.
 */
@Service
@Transactional
public class DirectorServiceImpl implements DirectorService{

    private final Logger log = LoggerFactory.getLogger(DirectorServiceImpl.class);
    
    @Inject
    private DirectorRepository directorRepository;

    /**
     * Save a director.
     *
     * @param director the entity to save
     * @return the persisted entity
     */
    public Director save(Director director) {
        log.debug("Request to save Director : {}", director);
        Director result = directorRepository.save(director);
        return result;
    }

    /**
     *  Get all the directors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Director> findAll(Pageable pageable) {
        log.debug("Request to get all Directors");
        Page<Director> result = directorRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one director by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Director findOne(Long id) {
        log.debug("Request to get Director : {}", id);
        Director director = directorRepository.findOne(id);
        return director;
    }

    /**
     *  Delete the  director by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Director : {}", id);
        directorRepository.delete(id);
    }
}
