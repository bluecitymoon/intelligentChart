package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonSocialMedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonSocialMedia.
 */
public interface PersonSocialMediaService {

    /**
     * Save a personSocialMedia.
     *
     * @param personSocialMedia the entity to save
     * @return the persisted entity
     */
    PersonSocialMedia save(PersonSocialMedia personSocialMedia);

    /**
     *  Get all the personSocialMedias.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonSocialMedia> findAll(Pageable pageable);

    /**
     *  Get the "id" personSocialMedia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonSocialMedia findOne(Long id);

    /**
     *  Delete the "id" personSocialMedia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
