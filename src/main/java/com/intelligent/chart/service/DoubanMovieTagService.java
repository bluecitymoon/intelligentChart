package com.intelligent.chart.service;

import com.intelligent.chart.domain.DoubanMovieTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DoubanMovieTag.
 */
public interface DoubanMovieTagService {

    /**
     * Save a doubanMovieTag.
     *
     * @param doubanMovieTag the entity to save
     * @return the persisted entity
     */
    DoubanMovieTag save(DoubanMovieTag doubanMovieTag);

    /**
     *  Get all the doubanMovieTags.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DoubanMovieTag> findAll(Pageable pageable);

    /**
     *  Get the "id" doubanMovieTag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DoubanMovieTag findOne(Long id);

    /**
     *  Delete the "id" doubanMovieTag.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
