package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonSearchCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonSearchCount.
 */
public interface PersonSearchCountService {

    /**
     * Save a personSearchCount.
     *
     * @param personSearchCount the entity to save
     * @return the persisted entity
     */
    PersonSearchCount save(PersonSearchCount personSearchCount);

    /**
     *  Get all the personSearchCounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonSearchCount> findAll(Pageable pageable);

    /**
     *  Get the "id" personSearchCount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonSearchCount findOne(Long id);

    /**
     *  Delete the "id" personSearchCount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
