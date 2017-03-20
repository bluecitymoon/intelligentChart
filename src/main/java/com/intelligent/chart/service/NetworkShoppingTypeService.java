package com.intelligent.chart.service;

import com.intelligent.chart.domain.NetworkShoppingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing NetworkShoppingType.
 */
public interface NetworkShoppingTypeService {

    /**
     * Save a networkShoppingType.
     *
     * @param networkShoppingType the entity to save
     * @return the persisted entity
     */
    NetworkShoppingType save(NetworkShoppingType networkShoppingType);

    /**
     *  Get all the networkShoppingTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NetworkShoppingType> findAll(Pageable pageable);

    /**
     *  Get the "id" networkShoppingType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NetworkShoppingType findOne(Long id);

    /**
     *  Delete the "id" networkShoppingType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
