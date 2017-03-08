package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.NetworkTexiCompanyService;
import com.intelligent.chart.domain.NetworkTexiCompany;
import com.intelligent.chart.repository.NetworkTexiCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing NetworkTexiCompany.
 */
@Service
@Transactional
public class NetworkTexiCompanyServiceImpl implements NetworkTexiCompanyService{

    private final Logger log = LoggerFactory.getLogger(NetworkTexiCompanyServiceImpl.class);
    
    @Inject
    private NetworkTexiCompanyRepository networkTexiCompanyRepository;

    /**
     * Save a networkTexiCompany.
     *
     * @param networkTexiCompany the entity to save
     * @return the persisted entity
     */
    public NetworkTexiCompany save(NetworkTexiCompany networkTexiCompany) {
        log.debug("Request to save NetworkTexiCompany : {}", networkTexiCompany);
        NetworkTexiCompany result = networkTexiCompanyRepository.save(networkTexiCompany);
        return result;
    }

    /**
     *  Get all the networkTexiCompanies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<NetworkTexiCompany> findAll(Pageable pageable) {
        log.debug("Request to get all NetworkTexiCompanies");
        Page<NetworkTexiCompany> result = networkTexiCompanyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one networkTexiCompany by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public NetworkTexiCompany findOne(Long id) {
        log.debug("Request to get NetworkTexiCompany : {}", id);
        NetworkTexiCompany networkTexiCompany = networkTexiCompanyRepository.findOne(id);
        return networkTexiCompany;
    }

    /**
     *  Delete the  networkTexiCompany by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NetworkTexiCompany : {}", id);
        networkTexiCompanyRepository.delete(id);
    }
}
