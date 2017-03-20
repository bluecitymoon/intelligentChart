package com.intelligent.chart.service;

import com.intelligent.chart.domain.WordCloud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WordCloud.
 */
public interface WordCloudService {

    /**
     * Save a wordCloud.
     *
     * @param wordCloud the entity to save
     * @return the persisted entity
     */
    WordCloud save(WordCloud wordCloud);

    /**
     *  Get all the wordClouds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WordCloud> findAll(Pageable pageable);

    /**
     *  Get the "id" wordCloud.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WordCloud findOne(Long id);

    /**
     *  Delete the "id" wordCloud.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
