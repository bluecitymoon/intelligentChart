package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFanSupportGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFanSupportGroup.
 */
public interface PersonFanSupportGroupService {

    /**
     * Save a personFanSupportGroup.
     *
     * @param personFanSupportGroup the entity to save
     * @return the persisted entity
     */
    PersonFanSupportGroup save(PersonFanSupportGroup personFanSupportGroup);

    /**
     *  Get all the personFanSupportGroups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFanSupportGroup> findAll(Pageable pageable);

    /**
     *  Get the "id" personFanSupportGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFanSupportGroup findOne(Long id);

    /**
     *  Delete the "id" personFanSupportGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
