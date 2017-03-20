package com.intelligent.chart.service;

import com.intelligent.chart.domain.LawBusinessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LawBusinessType.
 */
public interface LawBusinessTypeService {

    /**
     * Save a lawBusinessType.
     *
     * @param lawBusinessType the entity to save
     * @return the persisted entity
     */
    LawBusinessType save(LawBusinessType lawBusinessType);

    /**
     *  Get all the lawBusinessTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LawBusinessType> findAll(Pageable pageable);

    /**
     *  Get the "id" lawBusinessType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LawBusinessType findOne(Long id);

    /**
     *  Delete the "id" lawBusinessType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
