package com.intelligent.chart.service;

import com.intelligent.chart.domain.MovieCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MovieCategory.
 */
public interface MovieCategoryService {

    /**
     * Save a movieCategory.
     *
     * @param movieCategory the entity to save
     * @return the persisted entity
     */
    MovieCategory save(MovieCategory movieCategory);

    /**
     *  Get all the movieCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MovieCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" movieCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MovieCategory findOne(Long id);

    /**
     *  Delete the "id" movieCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
