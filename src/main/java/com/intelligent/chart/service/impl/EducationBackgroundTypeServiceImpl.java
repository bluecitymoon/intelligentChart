package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.EducationBackgroundTypeService;
import com.intelligent.chart.domain.EducationBackgroundType;
import com.intelligent.chart.repository.EducationBackgroundTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing EducationBackgroundType.
 */
@Service
@Transactional
public class EducationBackgroundTypeServiceImpl implements EducationBackgroundTypeService{

    private final Logger log = LoggerFactory.getLogger(EducationBackgroundTypeServiceImpl.class);
    
    @Inject
    private EducationBackgroundTypeRepository educationBackgroundTypeRepository;

    /**
     * Save a educationBackgroundType.
     *
     * @param educationBackgroundType the entity to save
     * @return the persisted entity
     */
    public EducationBackgroundType save(EducationBackgroundType educationBackgroundType) {
        log.debug("Request to save EducationBackgroundType : {}", educationBackgroundType);
        EducationBackgroundType result = educationBackgroundTypeRepository.save(educationBackgroundType);
        return result;
    }

    /**
     *  Get all the educationBackgroundTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EducationBackgroundType> findAll(Pageable pageable) {
        log.debug("Request to get all EducationBackgroundTypes");
        Page<EducationBackgroundType> result = educationBackgroundTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one educationBackgroundType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EducationBackgroundType findOne(Long id) {
        log.debug("Request to get EducationBackgroundType : {}", id);
        EducationBackgroundType educationBackgroundType = educationBackgroundTypeRepository.findOne(id);
        return educationBackgroundType;
    }

    /**
     *  Delete the  educationBackgroundType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EducationBackgroundType : {}", id);
        educationBackgroundTypeRepository.delete(id);
    }
}
