package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonEducationBackground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonEducationBackground.
 */
public interface PersonEducationBackgroundService {

    /**
     * Save a personEducationBackground.
     *
     * @param personEducationBackground the entity to save
     * @return the persisted entity
     */
    PersonEducationBackground save(PersonEducationBackground personEducationBackground);

    /**
     *  Get all the personEducationBackgrounds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonEducationBackground> findAll(Pageable pageable);

    /**
     *  Get the "id" personEducationBackground.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonEducationBackground findOne(Long id);

    /**
     *  Delete the "id" personEducationBackground.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
