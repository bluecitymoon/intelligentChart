package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonRelation.
 */
public interface PersonRelationService {

    /**
     * Save a personRelation.
     *
     * @param personRelation the entity to save
     * @return the persisted entity
     */
    PersonRelation save(PersonRelation personRelation);

    /**
     *  Get all the personRelations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonRelation> findAll(Pageable pageable);

    /**
     *  Get the "id" personRelation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonRelation findOne(Long id);

    /**
     *  Delete the "id" personRelation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
