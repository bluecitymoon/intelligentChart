package com.intelligent.chart.service;

import com.intelligent.chart.domain.PaymentTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PaymentTool.
 */
public interface PaymentToolService {

    /**
     * Save a paymentTool.
     *
     * @param paymentTool the entity to save
     * @return the persisted entity
     */
    PaymentTool save(PaymentTool paymentTool);

    /**
     *  Get all the paymentTools.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentTool> findAll(Pageable pageable);

    /**
     *  Get the "id" paymentTool.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentTool findOne(Long id);

    /**
     *  Delete the "id" paymentTool.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
