package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonInnovation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonInnovation.
 */
public interface PersonInnovationService {

    /**
     * Save a personInnovation.
     *
     * @param personInnovation the entity to save
     * @return the persisted entity
     */
    PersonInnovation save(PersonInnovation personInnovation);

    /**
     *  Get all the personInnovations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonInnovation> findAll(Pageable pageable);

    /**
     *  Get the "id" personInnovation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonInnovation findOne(Long id);

    /**
     *  Delete the "id" personInnovation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
