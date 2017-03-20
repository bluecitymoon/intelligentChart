package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonPrize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonPrize.
 */
public interface PersonPrizeService {

    /**
     * Save a personPrize.
     *
     * @param personPrize the entity to save
     * @return the persisted entity
     */
    PersonPrize save(PersonPrize personPrize);

    /**
     *  Get all the personPrizes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonPrize> findAll(Pageable pageable);

    /**
     *  Get the "id" personPrize.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonPrize findOne(Long id);

    /**
     *  Delete the "id" personPrize.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
