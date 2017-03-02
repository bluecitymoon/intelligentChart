package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonWordCloud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonWordCloud.
 */
public interface PersonWordCloudService {

    /**
     * Save a personWordCloud.
     *
     * @param personWordCloud the entity to save
     * @return the persisted entity
     */
    PersonWordCloud save(PersonWordCloud personWordCloud);

    /**
     *  Get all the personWordClouds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonWordCloud> findAll(Pageable pageable);

    /**
     *  Get the "id" personWordCloud.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonWordCloud findOne(Long id);

    /**
     *  Delete the "id" personWordCloud.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
