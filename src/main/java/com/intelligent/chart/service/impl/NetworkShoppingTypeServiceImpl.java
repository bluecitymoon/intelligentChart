package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.NetworkShoppingTypeService;
import com.intelligent.chart.domain.NetworkShoppingType;
import com.intelligent.chart.repository.NetworkShoppingTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing NetworkShoppingType.
 */
@Service
@Transactional
public class NetworkShoppingTypeServiceImpl implements NetworkShoppingTypeService{

    private final Logger log = LoggerFactory.getLogger(NetworkShoppingTypeServiceImpl.class);
    
    @Inject
    private NetworkShoppingTypeRepository networkShoppingTypeRepository;

    /**
     * Save a networkShoppingType.
     *
     * @param networkShoppingType the entity to save
     * @return the persisted entity
     */
    public NetworkShoppingType save(NetworkShoppingType networkShoppingType) {
        log.debug("Request to save NetworkShoppingType : {}", networkShoppingType);
        NetworkShoppingType result = networkShoppingTypeRepository.save(networkShoppingType);
        return result;
    }

    /**
     *  Get all the networkShoppingTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<NetworkShoppingType> findAll(Pageable pageable) {
        log.debug("Request to get all NetworkShoppingTypes");
        Page<NetworkShoppingType> result = networkShoppingTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one networkShoppingType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public NetworkShoppingType findOne(Long id) {
        log.debug("Request to get NetworkShoppingType : {}", id);
        NetworkShoppingType networkShoppingType = networkShoppingTypeRepository.findOne(id);
        return networkShoppingType;
    }

    /**
     *  Delete the  networkShoppingType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NetworkShoppingType : {}", id);
        networkShoppingTypeRepository.delete(id);
    }
}
