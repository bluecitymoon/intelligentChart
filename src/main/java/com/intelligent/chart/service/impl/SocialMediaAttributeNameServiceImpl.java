package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.SocialMediaAttributeNameService;
import com.intelligent.chart.domain.SocialMediaAttributeName;
import com.intelligent.chart.repository.SocialMediaAttributeNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SocialMediaAttributeName.
 */
@Service
@Transactional
public class SocialMediaAttributeNameServiceImpl implements SocialMediaAttributeNameService{

    private final Logger log = LoggerFactory.getLogger(SocialMediaAttributeNameServiceImpl.class);
    
    @Inject
    private SocialMediaAttributeNameRepository socialMediaAttributeNameRepository;

    /**
     * Save a socialMediaAttributeName.
     *
     * @param socialMediaAttributeName the entity to save
     * @return the persisted entity
     */
    public SocialMediaAttributeName save(SocialMediaAttributeName socialMediaAttributeName) {
        log.debug("Request to save SocialMediaAttributeName : {}", socialMediaAttributeName);
        SocialMediaAttributeName result = socialMediaAttributeNameRepository.save(socialMediaAttributeName);
        return result;
    }

    /**
     *  Get all the socialMediaAttributeNames.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SocialMediaAttributeName> findAll(Pageable pageable) {
        log.debug("Request to get all SocialMediaAttributeNames");
        Page<SocialMediaAttributeName> result = socialMediaAttributeNameRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one socialMediaAttributeName by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SocialMediaAttributeName findOne(Long id) {
        log.debug("Request to get SocialMediaAttributeName : {}", id);
        SocialMediaAttributeName socialMediaAttributeName = socialMediaAttributeNameRepository.findOne(id);
        return socialMediaAttributeName;
    }

    /**
     *  Delete the  socialMediaAttributeName by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SocialMediaAttributeName : {}", id);
        socialMediaAttributeNameRepository.delete(id);
    }
}
