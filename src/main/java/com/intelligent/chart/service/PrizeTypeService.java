package com.intelligent.chart.service;

import com.intelligent.chart.domain.PrizeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PrizeType.
 */
public interface PrizeTypeService {

    /**
     * Save a prizeType.
     *
     * @param prizeType the entity to save
     * @return the persisted entity
     */
    PrizeType save(PrizeType prizeType);

    /**
     *  Get all the prizeTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PrizeType> findAll(Pageable pageable);

    /**
     *  Get the "id" prizeType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PrizeType findOne(Long id);

    /**
     *  Delete the "id" prizeType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
