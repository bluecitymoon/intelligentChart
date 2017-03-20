package com.intelligent.chart.service;

import com.intelligent.chart.domain.EducationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing EducationLevel.
 */
public interface EducationLevelService {

    /**
     * Save a educationLevel.
     *
     * @param educationLevel the entity to save
     * @return the persisted entity
     */
    EducationLevel save(EducationLevel educationLevel);

    /**
     *  Get all the educationLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EducationLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" educationLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EducationLevel findOne(Long id);

    /**
     *  Delete the "id" educationLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
