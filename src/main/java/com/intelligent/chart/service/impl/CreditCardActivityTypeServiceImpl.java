package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.CreditCardActivityTypeService;
import com.intelligent.chart.domain.CreditCardActivityType;
import com.intelligent.chart.repository.CreditCardActivityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing CreditCardActivityType.
 */
@Service
@Transactional
public class CreditCardActivityTypeServiceImpl implements CreditCardActivityTypeService{

    private final Logger log = LoggerFactory.getLogger(CreditCardActivityTypeServiceImpl.class);
    
    @Inject
    private CreditCardActivityTypeRepository creditCardActivityTypeRepository;

    /**
     * Save a creditCardActivityType.
     *
     * @param creditCardActivityType the entity to save
     * @return the persisted entity
     */
    public CreditCardActivityType save(CreditCardActivityType creditCardActivityType) {
        log.debug("Request to save CreditCardActivityType : {}", creditCardActivityType);
        CreditCardActivityType result = creditCardActivityTypeRepository.save(creditCardActivityType);
        return result;
    }

    /**
     *  Get all the creditCardActivityTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CreditCardActivityType> findAll(Pageable pageable) {
        log.debug("Request to get all CreditCardActivityTypes");
        Page<CreditCardActivityType> result = creditCardActivityTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one creditCardActivityType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CreditCardActivityType findOne(Long id) {
        log.debug("Request to get CreditCardActivityType : {}", id);
        CreditCardActivityType creditCardActivityType = creditCardActivityTypeRepository.findOne(id);
        return creditCardActivityType;
    }

    /**
     *  Delete the  creditCardActivityType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditCardActivityType : {}", id);
        creditCardActivityTypeRepository.delete(id);
    }
}
