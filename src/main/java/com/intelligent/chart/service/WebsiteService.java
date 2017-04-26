package com.intelligent.chart.service;

import com.intelligent.chart.domain.Website;
import java.util.List;

/**
 * Service Interface for managing Website.
 */
public interface WebsiteService {

    /**
     * Save a website.
     *
     * @param website the entity to save
     * @return the persisted entity
     */
    Website save(Website website);

    /**
     *  Get all the websites.
     *  
     *  @return the list of entities
     */
    List<Website> findAll();

    /**
     *  Get the "id" website.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Website findOne(Long id);

    /**
     *  Delete the "id" website.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
