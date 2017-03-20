package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MediaTypeService;
import com.intelligent.chart.domain.MediaType;
import com.intelligent.chart.repository.MediaTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MediaType.
 */
@Service
@Transactional
public class MediaTypeServiceImpl implements MediaTypeService{

    private final Logger log = LoggerFactory.getLogger(MediaTypeServiceImpl.class);
    
    @Inject
    private MediaTypeRepository mediaTypeRepository;

    /**
     * Save a mediaType.
     *
     * @param mediaType the entity to save
     * @return the persisted entity
     */
    public MediaType save(MediaType mediaType) {
        log.debug("Request to save MediaType : {}", mediaType);
        MediaType result = mediaTypeRepository.save(mediaType);
        return result;
    }

    /**
     *  Get all the mediaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MediaType> findAll(Pageable pageable) {
        log.debug("Request to get all MediaTypes");
        Page<MediaType> result = mediaTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one mediaType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MediaType findOne(Long id) {
        log.debug("Request to get MediaType : {}", id);
        MediaType mediaType = mediaTypeRepository.findOne(id);
        return mediaType;
    }

    /**
     *  Delete the  mediaType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MediaType : {}", id);
        mediaTypeRepository.delete(id);
    }
}
