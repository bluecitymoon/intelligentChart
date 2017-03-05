package com.intelligent.chart.service;

import com.intelligent.chart.domain.CreditCardActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CreditCardActivityType.
 */
public interface CreditCardActivityTypeService {

    /**
     * Save a creditCardActivityType.
     *
     * @param creditCardActivityType the entity to save
     * @return the persisted entity
     */
    CreditCardActivityType save(CreditCardActivityType creditCardActivityType);

    /**
     *  Get all the creditCardActivityTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CreditCardActivityType> findAll(Pageable pageable);

    /**
     *  Get the "id" creditCardActivityType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CreditCardActivityType findOne(Long id);

    /**
     *  Delete the "id" creditCardActivityType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
