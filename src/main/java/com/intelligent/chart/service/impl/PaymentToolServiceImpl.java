package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PaymentToolService;
import com.intelligent.chart.domain.PaymentTool;
import com.intelligent.chart.repository.PaymentToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PaymentTool.
 */
@Service
@Transactional
public class PaymentToolServiceImpl implements PaymentToolService{

    private final Logger log = LoggerFactory.getLogger(PaymentToolServiceImpl.class);
    
    @Inject
    private PaymentToolRepository paymentToolRepository;

    /**
     * Save a paymentTool.
     *
     * @param paymentTool the entity to save
     * @return the persisted entity
     */
    public PaymentTool save(PaymentTool paymentTool) {
        log.debug("Request to save PaymentTool : {}", paymentTool);
        PaymentTool result = paymentToolRepository.save(paymentTool);
        return result;
    }

    /**
     *  Get all the paymentTools.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PaymentTool> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTools");
        Page<PaymentTool> result = paymentToolRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one paymentTool by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PaymentTool findOne(Long id) {
        log.debug("Request to get PaymentTool : {}", id);
        PaymentTool paymentTool = paymentToolRepository.findOne(id);
        return paymentTool;
    }

    /**
     *  Delete the  paymentTool by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentTool : {}", id);
        paymentToolRepository.delete(id);
    }
}
