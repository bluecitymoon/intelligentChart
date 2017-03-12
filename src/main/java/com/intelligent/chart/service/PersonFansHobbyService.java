package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFansHobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFansHobby.
 */
public interface PersonFansHobbyService {

    /**
     * Save a personFansHobby.
     *
     * @param personFansHobby the entity to save
     * @return the persisted entity
     */
    PersonFansHobby save(PersonFansHobby personFansHobby);

    /**
     *  Get all the personFansHobbies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFansHobby> findAll(Pageable pageable);

    /**
     *  Get the "id" personFansHobby.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFansHobby findOne(Long id);

    /**
     *  Delete the "id" personFansHobby.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
