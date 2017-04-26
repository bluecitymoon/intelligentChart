package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.WebsiteService;
import com.intelligent.chart.domain.Website;
import com.intelligent.chart.repository.WebsiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Website.
 */
@Service
@Transactional
public class WebsiteServiceImpl implements WebsiteService{

    private final Logger log = LoggerFactory.getLogger(WebsiteServiceImpl.class);
    
    @Inject
    private WebsiteRepository websiteRepository;

    /**
     * Save a website.
     *
     * @param website the entity to save
     * @return the persisted entity
     */
    public Website save(Website website) {
        log.debug("Request to save Website : {}", website);
        Website result = websiteRepository.save(website);
        return result;
    }

    /**
     *  Get all the websites.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Website> findAll() {
        log.debug("Request to get all Websites");
        List<Website> result = websiteRepository.findAll();

        return result;
    }

    /**
     *  Get one website by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Website findOne(Long id) {
        log.debug("Request to get Website : {}", id);
        Website website = websiteRepository.findOne(id);
        return website;
    }

    /**
     *  Delete the  website by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Website : {}", id);
        websiteRepository.delete(id);
    }
}
