package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.EducationLevelService;
import com.intelligent.chart.domain.EducationLevel;
import com.intelligent.chart.repository.EducationLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing EducationLevel.
 */
@Service
@Transactional
public class EducationLevelServiceImpl implements EducationLevelService{

    private final Logger log = LoggerFactory.getLogger(EducationLevelServiceImpl.class);
    
    @Inject
    private EducationLevelRepository educationLevelRepository;

    /**
     * Save a educationLevel.
     *
     * @param educationLevel the entity to save
     * @return the persisted entity
     */
    public EducationLevel save(EducationLevel educationLevel) {
        log.debug("Request to save EducationLevel : {}", educationLevel);
        EducationLevel result = educationLevelRepository.save(educationLevel);
        return result;
    }

    /**
     *  Get all the educationLevels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EducationLevel> findAll(Pageable pageable) {
        log.debug("Request to get all EducationLevels");
        Page<EducationLevel> result = educationLevelRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one educationLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EducationLevel findOne(Long id) {
        log.debug("Request to get EducationLevel : {}", id);
        EducationLevel educationLevel = educationLevelRepository.findOne(id);
        return educationLevel;
    }

    /**
     *  Delete the  educationLevel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EducationLevel : {}", id);
        educationLevelRepository.delete(id);
    }
}
