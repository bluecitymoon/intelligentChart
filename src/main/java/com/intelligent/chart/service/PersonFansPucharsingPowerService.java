package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFansPucharsingPower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFansPucharsingPower.
 */
public interface PersonFansPucharsingPowerService {

    /**
     * Save a personFansPucharsingPower.
     *
     * @param personFansPucharsingPower the entity to save
     * @return the persisted entity
     */
    PersonFansPucharsingPower save(PersonFansPucharsingPower personFansPucharsingPower);

    /**
     *  Get all the personFansPucharsingPowers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFansPucharsingPower> findAll(Pageable pageable);

    /**
     *  Get the "id" personFansPucharsingPower.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFansPucharsingPower findOne(Long id);

    /**
     *  Delete the "id" personFansPucharsingPower.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
