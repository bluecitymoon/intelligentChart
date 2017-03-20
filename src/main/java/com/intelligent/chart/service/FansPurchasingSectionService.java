package com.intelligent.chart.service;

import com.intelligent.chart.domain.FansPurchasingSection;
import java.util.List;

/**
 * Service Interface for managing FansPurchasingSection.
 */
public interface FansPurchasingSectionService {

    /**
     * Save a fansPurchasingSection.
     *
     * @param fansPurchasingSection the entity to save
     * @return the persisted entity
     */
    FansPurchasingSection save(FansPurchasingSection fansPurchasingSection);

    /**
     *  Get all the fansPurchasingSections.
     *  
     *  @return the list of entities
     */
    List<FansPurchasingSection> findAll();

    /**
     *  Get the "id" fansPurchasingSection.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FansPurchasingSection findOne(Long id);

    /**
     *  Delete the "id" fansPurchasingSection.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
