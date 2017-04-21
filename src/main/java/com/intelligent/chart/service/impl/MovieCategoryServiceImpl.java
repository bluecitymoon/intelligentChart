package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MovieCategoryService;
import com.intelligent.chart.domain.MovieCategory;
import com.intelligent.chart.repository.MovieCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MovieCategory.
 */
@Service
@Transactional
public class MovieCategoryServiceImpl implements MovieCategoryService{

    private final Logger log = LoggerFactory.getLogger(MovieCategoryServiceImpl.class);
    
    @Inject
    private MovieCategoryRepository movieCategoryRepository;

    /**
     * Save a movieCategory.
     *
     * @param movieCategory the entity to save
     * @return the persisted entity
     */
    public MovieCategory save(MovieCategory movieCategory) {
        log.debug("Request to save MovieCategory : {}", movieCategory);
        MovieCategory result = movieCategoryRepository.save(movieCategory);
        return result;
    }

    /**
     *  Get all the movieCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MovieCategory> findAll(Pageable pageable) {
        log.debug("Request to get all MovieCategories");
        Page<MovieCategory> result = movieCategoryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one movieCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MovieCategory findOne(Long id) {
        log.debug("Request to get MovieCategory : {}", id);
        MovieCategory movieCategory = movieCategoryRepository.findOne(id);
        return movieCategory;
    }

    /**
     *  Delete the  movieCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieCategory : {}", id);
        movieCategoryRepository.delete(id);
    }
}
