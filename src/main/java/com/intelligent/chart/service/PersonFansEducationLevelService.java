package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFansEducationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFansEducationLevel.
 */
public interface PersonFansEducationLevelService {

    /**
     * Save a personFansEducationLevel.
     *
     * @param personFansEducationLevel the entity to save
     * @return the persisted entity
     */
    PersonFansEducationLevel save(PersonFansEducationLevel personFansEducationLevel);

    /**
     *  Get all the personFansEducationLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFansEducationLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" personFansEducationLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFansEducationLevel findOne(Long id);

    /**
     *  Delete the "id" personFansEducationLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
