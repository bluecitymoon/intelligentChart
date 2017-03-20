package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonNetworkTexiActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonNetworkTexiActivity.
 */
public interface PersonNetworkTexiActivityService {

    /**
     * Save a personNetworkTexiActivity.
     *
     * @param personNetworkTexiActivity the entity to save
     * @return the persisted entity
     */
    PersonNetworkTexiActivity save(PersonNetworkTexiActivity personNetworkTexiActivity);

    /**
     *  Get all the personNetworkTexiActivities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonNetworkTexiActivity> findAll(Pageable pageable);

    /**
     *  Get the "id" personNetworkTexiActivity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonNetworkTexiActivity findOne(Long id);

    /**
     *  Delete the "id" personNetworkTexiActivity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
