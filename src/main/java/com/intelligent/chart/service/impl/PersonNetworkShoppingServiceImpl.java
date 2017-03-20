package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonNetworkShoppingService;
import com.intelligent.chart.domain.PersonNetworkShopping;
import com.intelligent.chart.repository.PersonNetworkShoppingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonNetworkShopping.
 */
@Service
@Transactional
public class PersonNetworkShoppingServiceImpl implements PersonNetworkShoppingService{

    private final Logger log = LoggerFactory.getLogger(PersonNetworkShoppingServiceImpl.class);
    
    @Inject
    private PersonNetworkShoppingRepository personNetworkShoppingRepository;

    /**
     * Save a personNetworkShopping.
     *
     * @param personNetworkShopping the entity to save
     * @return the persisted entity
     */
    public PersonNetworkShopping save(PersonNetworkShopping personNetworkShopping) {
        log.debug("Request to save PersonNetworkShopping : {}", personNetworkShopping);
        PersonNetworkShopping result = personNetworkShoppingRepository.save(personNetworkShopping);
        return result;
    }

    /**
     *  Get all the personNetworkShoppings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonNetworkShopping> findAll(Pageable pageable) {
        log.debug("Request to get all PersonNetworkShoppings");
        Page<PersonNetworkShopping> result = personNetworkShoppingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personNetworkShopping by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonNetworkShopping findOne(Long id) {
        log.debug("Request to get PersonNetworkShopping : {}", id);
        PersonNetworkShopping personNetworkShopping = personNetworkShoppingRepository.findOne(id);
        return personNetworkShopping;
    }

    /**
     *  Delete the  personNetworkShopping by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonNetworkShopping : {}", id);
        personNetworkShoppingRepository.delete(id);
    }
}
