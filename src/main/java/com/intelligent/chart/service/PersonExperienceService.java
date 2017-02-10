package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonExperience.
 */
public interface PersonExperienceService {

    /**
     * Save a personExperience.
     *
     * @param personExperience the entity to save
     * @return the persisted entity
     */
    PersonExperience save(PersonExperience personExperience);

    /**
     *  Get all the personExperiences.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonExperience> findAll(Pageable pageable);

    /**
     *  Get the "id" personExperience.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonExperience findOne(Long id);

    /**
     *  Delete the "id" personExperience.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
