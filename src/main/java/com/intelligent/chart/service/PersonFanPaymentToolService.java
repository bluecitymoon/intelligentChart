package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonFanPaymentTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonFanPaymentTool.
 */
public interface PersonFanPaymentToolService {

    /**
     * Save a personFanPaymentTool.
     *
     * @param personFanPaymentTool the entity to save
     * @return the persisted entity
     */
    PersonFanPaymentTool save(PersonFanPaymentTool personFanPaymentTool);

    /**
     *  Get all the personFanPaymentTools.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonFanPaymentTool> findAll(Pageable pageable);

    /**
     *  Get the "id" personFanPaymentTool.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonFanPaymentTool findOne(Long id);

    /**
     *  Delete the "id" personFanPaymentTool.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
