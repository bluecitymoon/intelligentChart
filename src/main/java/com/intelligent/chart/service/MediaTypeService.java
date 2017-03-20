package com.intelligent.chart.service;

import com.intelligent.chart.domain.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MediaType.
 */
public interface MediaTypeService {

    /**
     * Save a mediaType.
     *
     * @param mediaType the entity to save
     * @return the persisted entity
     */
    MediaType save(MediaType mediaType);

    /**
     *  Get all the mediaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MediaType> findAll(Pageable pageable);

    /**
     *  Get the "id" mediaType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MediaType findOne(Long id);

    /**
     *  Delete the "id" mediaType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
