package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFansEgeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFansEgeLevel.
 */
public interface PersonFansEgeLevelService {

    /**
     * Save a personFansEgeLevel.
     *
     * @param personFansEgeLevel the entity to save
     * @return the persisted entity
     */
    PersonFansEgeLevel save(PersonFansEgeLevel personFansEgeLevel);

    /**
     *  Get all the personFansEgeLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFansEgeLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" personFansEgeLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFansEgeLevel findOne(Long id);

    /**
     *  Delete the "id" personFansEgeLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
