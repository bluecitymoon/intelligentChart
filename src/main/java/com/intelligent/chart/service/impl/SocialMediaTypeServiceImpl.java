package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.SocialMediaTypeService;
import com.intelligent.chart.domain.SocialMediaType;
import com.intelligent.chart.repository.SocialMediaTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SocialMediaType.
 */
@Service
@Transactional
public class SocialMediaTypeServiceImpl implements SocialMediaTypeService{

    private final Logger log = LoggerFactory.getLogger(SocialMediaTypeServiceImpl.class);
    
    @Inject
    private SocialMediaTypeRepository socialMediaTypeRepository;

    /**
     * Save a socialMediaType.
     *
     * @param socialMediaType the entity to save
     * @return the persisted entity
     */
    public SocialMediaType save(SocialMediaType socialMediaType) {
        log.debug("Request to save SocialMediaType : {}", socialMediaType);
        SocialMediaType result = socialMediaTypeRepository.save(socialMediaType);
        return result;
    }

    /**
     *  Get all the socialMediaTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SocialMediaType> findAll(Pageable pageable) {
        log.debug("Request to get all SocialMediaTypes");
        Page<SocialMediaType> result = socialMediaTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one socialMediaType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SocialMediaType findOne(Long id) {
        log.debug("Request to get SocialMediaType : {}", id);
        SocialMediaType socialMediaType = socialMediaTypeRepository.findOne(id);
        return socialMediaType;
    }

    /**
     *  Delete the  socialMediaType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SocialMediaType : {}", id);
        socialMediaTypeRepository.delete(id);
    }
}
