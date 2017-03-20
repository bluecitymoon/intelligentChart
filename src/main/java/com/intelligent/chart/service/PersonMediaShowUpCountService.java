package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonMediaShowUpCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonMediaShowUpCount.
 */
public interface PersonMediaShowUpCountService {

    /**
     * Save a personMediaShowUpCount.
     *
     * @param personMediaShowUpCount the entity to save
     * @return the persisted entity
     */
    PersonMediaShowUpCount save(PersonMediaShowUpCount personMediaShowUpCount);

    /**
     *  Get all the personMediaShowUpCounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonMediaShowUpCount> findAll(Pageable pageable);

    /**
     *  Get the "id" personMediaShowUpCount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonMediaShowUpCount findOne(Long id);

    /**
     *  Delete the "id" personMediaShowUpCount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
