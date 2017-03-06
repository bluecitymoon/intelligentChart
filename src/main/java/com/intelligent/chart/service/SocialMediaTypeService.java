package com.intelligent.chart.service;

import com.intelligent.chart.domain.SocialMediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing SocialMediaType.
 */
public interface SocialMediaTypeService {

    /**
     * Save a socialMediaType.
     *
     * @param socialMediaType the entity to save
     * @return the persisted entity
     */
    SocialMediaType save(SocialMediaType socialMediaType);

    /**
     *  Get all the socialMediaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SocialMediaType> findAll(Pageable pageable);

    /**
     *  Get the "id" socialMediaType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SocialMediaType findOne(Long id);

    /**
     *  Delete the "id" socialMediaType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
