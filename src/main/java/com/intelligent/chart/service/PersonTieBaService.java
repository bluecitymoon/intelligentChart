package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonTieBa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonTieBa.
 */
public interface PersonTieBaService {

    /**
     * Save a personTieBa.
     *
     * @param personTieBa the entity to save
     * @return the persisted entity
     */
    PersonTieBa save(PersonTieBa personTieBa);

    /**
     *  Get all the personTieBas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonTieBa> findAll(Pageable pageable);

    /**
     *  Get the "id" personTieBa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonTieBa findOne(Long id);

    /**
     *  Delete the "id" personTieBa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
