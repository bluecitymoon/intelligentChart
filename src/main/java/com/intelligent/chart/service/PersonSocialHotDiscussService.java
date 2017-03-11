package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonSocialHotDiscuss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonSocialHotDiscuss.
 */
public interface PersonSocialHotDiscussService {

    /**
     * Save a personSocialHotDiscuss.
     *
     * @param personSocialHotDiscuss the entity to save
     * @return the persisted entity
     */
    PersonSocialHotDiscuss save(PersonSocialHotDiscuss personSocialHotDiscuss);

    /**
     *  Get all the personSocialHotDiscusses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonSocialHotDiscuss> findAll(Pageable pageable);

    /**
     *  Get the "id" personSocialHotDiscuss.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonSocialHotDiscuss findOne(Long id);

    /**
     *  Delete the "id" personSocialHotDiscuss.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
