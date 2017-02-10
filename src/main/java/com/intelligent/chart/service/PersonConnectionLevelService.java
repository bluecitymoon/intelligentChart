package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonConnectionLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonConnectionLevel.
 */
public interface PersonConnectionLevelService {

    /**
     * Save a personConnectionLevel.
     *
     * @param personConnectionLevel the entity to save
     * @return the persisted entity
     */
    PersonConnectionLevel save(PersonConnectionLevel personConnectionLevel);

    /**
     *  Get all the personConnectionLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonConnectionLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" personConnectionLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonConnectionLevel findOne(Long id);

    /**
     *  Delete the "id" personConnectionLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
