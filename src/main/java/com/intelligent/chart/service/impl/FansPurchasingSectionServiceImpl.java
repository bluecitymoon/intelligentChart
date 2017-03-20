package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.FansPurchasingSectionService;
import com.intelligent.chart.domain.FansPurchasingSection;
import com.intelligent.chart.repository.FansPurchasingSectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing FansPurchasingSection.
 */
@Service
@Transactional
public class FansPurchasingSectionServiceImpl implements FansPurchasingSectionService{

    private final Logger log = LoggerFactory.getLogger(FansPurchasingSectionServiceImpl.class);
    
    @Inject
    private FansPurchasingSectionRepository fansPurchasingSectionRepository;

    /**
     * Save a fansPurchasingSection.
     *
     * @param fansPurchasingSection the entity to save
     * @return the persisted entity
     */
    public FansPurchasingSection save(FansPurchasingSection fansPurchasingSection) {
        log.debug("Request to save FansPurchasingSection : {}", fansPurchasingSection);
        FansPurchasingSection result = fansPurchasingSectionRepository.save(fansPurchasingSection);
        return result;
    }

    /**
     *  Get all the fansPurchasingSections.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FansPurchasingSection> findAll() {
        log.debug("Request to get all FansPurchasingSections");
        List<FansPurchasingSection> result = fansPurchasingSectionRepository.findAll();

        return result;
    }

    /**
     *  Get one fansPurchasingSection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FansPurchasingSection findOne(Long id) {
        log.debug("Request to get FansPurchasingSection : {}", id);
        FansPurchasingSection fansPurchasingSection = fansPurchasingSectionRepository.findOne(id);
        return fansPurchasingSection;
    }

    /**
     *  Delete the  fansPurchasingSection by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FansPurchasingSection : {}", id);
        fansPurchasingSectionRepository.delete(id);
    }
}
