package com.intelligent.chart.service;

import com.intelligent.chart.domain.NetworkTexiCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing NetworkTexiCompany.
 */
public interface NetworkTexiCompanyService {

    /**
     * Save a networkTexiCompany.
     *
     * @param networkTexiCompany the entity to save
     * @return the persisted entity
     */
    NetworkTexiCompany save(NetworkTexiCompany networkTexiCompany);

    /**
     *  Get all the networkTexiCompanies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NetworkTexiCompany> findAll(Pageable pageable);

    /**
     *  Get the "id" networkTexiCompany.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NetworkTexiCompany findOne(Long id);

    /**
     *  Delete the "id" networkTexiCompany.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
