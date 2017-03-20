package com.intelligent.chart.service;

import com.intelligent.chart.domain.InnovationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing InnovationType.
 */
public interface InnovationTypeService {

    /**
     * Save a innovationType.
     *
     * @param innovationType the entity to save
     * @return the persisted entity
     */
    InnovationType save(InnovationType innovationType);

    /**
     *  Get all the innovationTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<InnovationType> findAll(Pageable pageable);

    /**
     *  Get the "id" innovationType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InnovationType findOne(Long id);

    /**
     *  Delete the "id" innovationType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
