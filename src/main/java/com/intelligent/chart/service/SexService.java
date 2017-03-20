package com.intelligent.chart.service;

import com.intelligent.chart.domain.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Sex.
 */
public interface SexService {

    /**
     * Save a sex.
     *
     * @param sex the entity to save
     * @return the persisted entity
     */
    Sex save(Sex sex);

    /**
     *  Get all the sexes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Sex> findAll(Pageable pageable);

    /**
     *  Get the "id" sex.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Sex findOne(Long id);

    /**
     *  Delete the "id" sex.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
