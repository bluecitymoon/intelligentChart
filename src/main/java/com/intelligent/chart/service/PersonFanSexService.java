package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFanSex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFanSex.
 */
public interface PersonFanSexService {

    /**
     * Save a personFanSex.
     *
     * @param personFanSex the entity to save
     * @return the persisted entity
     */
    PersonFanSex save(PersonFanSex personFanSex);

    /**
     *  Get all the personFanSexes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFanSex> findAll(Pageable pageable);

    /**
     *  Get the "id" personFanSex.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFanSex findOne(Long id);

    /**
     *  Delete the "id" personFanSex.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
