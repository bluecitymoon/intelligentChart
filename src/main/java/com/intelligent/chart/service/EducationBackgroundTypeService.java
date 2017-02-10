package com.intelligent.chart.service;

import com.intelligent.chart.domain.EducationBackgroundType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing EducationBackgroundType.
 */
public interface EducationBackgroundTypeService {

    /**
     * Save a educationBackgroundType.
     *
     * @param educationBackgroundType the entity to save
     * @return the persisted entity
     */
    EducationBackgroundType save(EducationBackgroundType educationBackgroundType);

    /**
     *  Get all the educationBackgroundTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EducationBackgroundType> findAll(Pageable pageable);

    /**
     *  Get the "id" educationBackgroundType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EducationBackgroundType findOne(Long id);

    /**
     *  Delete the "id" educationBackgroundType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
