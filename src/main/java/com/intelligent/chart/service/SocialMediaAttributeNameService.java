package com.intelligent.chart.service;

import com.intelligent.chart.domain.SocialMediaAttributeName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing SocialMediaAttributeName.
 */
public interface SocialMediaAttributeNameService {

    /**
     * Save a socialMediaAttributeName.
     *
     * @param socialMediaAttributeName the entity to save
     * @return the persisted entity
     */
    SocialMediaAttributeName save(SocialMediaAttributeName socialMediaAttributeName);

    /**
     *  Get all the socialMediaAttributeNames.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SocialMediaAttributeName> findAll(Pageable pageable);

    /**
     *  Get the "id" socialMediaAttributeName.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SocialMediaAttributeName findOne(Long id);

    /**
     *  Delete the "id" socialMediaAttributeName.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
