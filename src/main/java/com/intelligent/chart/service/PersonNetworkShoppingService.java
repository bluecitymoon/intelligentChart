package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonNetworkShopping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonNetworkShopping.
 */
public interface PersonNetworkShoppingService {

    /**
     * Save a personNetworkShopping.
     *
     * @param personNetworkShopping the entity to save
     * @return the persisted entity
     */
    PersonNetworkShopping save(PersonNetworkShopping personNetworkShopping);

    /**
     *  Get all the personNetworkShoppings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonNetworkShopping> findAll(Pageable pageable);

    /**
     *  Get the "id" personNetworkShopping.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonNetworkShopping findOne(Long id);

    /**
     *  Delete the "id" personNetworkShopping.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
