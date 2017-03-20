package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonEndorsement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonEndorsement.
 */
public interface PersonEndorsementService {

    /**
     * Save a personEndorsement.
     *
     * @param personEndorsement the entity to save
     * @return the persisted entity
     */
    PersonEndorsement save(PersonEndorsement personEndorsement);

    /**
     *  Get all the personEndorsements.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonEndorsement> findAll(Pageable pageable);

    /**
     *  Get the "id" personEndorsement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonEndorsement findOne(Long id);

    /**
     *  Delete the "id" personEndorsement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
