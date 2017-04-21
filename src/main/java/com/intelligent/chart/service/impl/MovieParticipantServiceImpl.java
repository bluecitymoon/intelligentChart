package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MovieParticipantService;
import com.intelligent.chart.domain.MovieParticipant;
import com.intelligent.chart.repository.MovieParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MovieParticipant.
 */
@Service
@Transactional
public class MovieParticipantServiceImpl implements MovieParticipantService{

    private final Logger log = LoggerFactory.getLogger(MovieParticipantServiceImpl.class);
    
    @Inject
    private MovieParticipantRepository movieParticipantRepository;

    /**
     * Save a movieParticipant.
     *
     * @param movieParticipant the entity to save
     * @return the persisted entity
     */
    public MovieParticipant save(MovieParticipant movieParticipant) {
        log.debug("Request to save MovieParticipant : {}", movieParticipant);
        MovieParticipant result = movieParticipantRepository.save(movieParticipant);
        return result;
    }

    /**
     *  Get all the movieParticipants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MovieParticipant> findAll(Pageable pageable) {
        log.debug("Request to get all MovieParticipants");
        Page<MovieParticipant> result = movieParticipantRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one movieParticipant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MovieParticipant findOne(Long id) {
        log.debug("Request to get MovieParticipant : {}", id);
        MovieParticipant movieParticipant = movieParticipantRepository.findOne(id);
        return movieParticipant;
    }

    /**
     *  Delete the  movieParticipant by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieParticipant : {}", id);
        movieParticipantRepository.delete(id);
    }
}
