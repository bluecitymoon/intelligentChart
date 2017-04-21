package com.intelligent.chart.service;

import com.intelligent.chart.domain.MovieParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MovieParticipant.
 */
public interface MovieParticipantService {

    /**
     * Save a movieParticipant.
     *
     * @param movieParticipant the entity to save
     * @return the persisted entity
     */
    MovieParticipant save(MovieParticipant movieParticipant);

    /**
     *  Get all the movieParticipants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MovieParticipant> findAll(Pageable pageable);

    /**
     *  Get the "id" movieParticipant.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MovieParticipant findOne(Long id);

    /**
     *  Delete the "id" movieParticipant.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
