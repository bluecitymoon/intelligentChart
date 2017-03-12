package com.intelligent.chart.service;

import com.intelligent.chart.domain.Hobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Hobby.
 */
public interface HobbyService {

    /**
     * Save a hobby.
     *
     * @param hobby the entity to save
     * @return the persisted entity
     */
    Hobby save(Hobby hobby);

    /**
     *  Get all the hobbies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Hobby> findAll(Pageable pageable);

    /**
     *  Get the "id" hobby.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Hobby findOne(Long id);

    /**
     *  Delete the "id" hobby.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
