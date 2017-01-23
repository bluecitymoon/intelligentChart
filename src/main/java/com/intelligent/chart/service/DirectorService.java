package com.intelligent.chart.service;

import com.intelligent.chart.domain.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Director.
 */
public interface DirectorService {

    /**
     * Save a director.
     *
     * @param director the entity to save
     * @return the persisted entity
     */
    Director save(Director director);

    /**
     *  Get all the directors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Director> findAll(Pageable pageable);

    /**
     *  Get the "id" director.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Director findOne(Long id);

    /**
     *  Delete the "id" director.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
