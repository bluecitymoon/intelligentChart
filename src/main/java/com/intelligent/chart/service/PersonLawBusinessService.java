package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonLawBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonLawBusiness.
 */
public interface PersonLawBusinessService {

    /**
     * Save a personLawBusiness.
     *
     * @param personLawBusiness the entity to save
     * @return the persisted entity
     */
    PersonLawBusiness save(PersonLawBusiness personLawBusiness);

    /**
     *  Get all the personLawBusinesses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonLawBusiness> findAll(Pageable pageable);

    /**
     *  Get the "id" personLawBusiness.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonLawBusiness findOne(Long id);

    /**
     *  Delete the "id" personLawBusiness.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
