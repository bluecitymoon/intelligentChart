package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.DoubleMovieSubjectService;
import com.intelligent.chart.domain.DoubleMovieSubject;
import com.intelligent.chart.repository.DoubleMovieSubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DoubleMovieSubject.
 */
@Service
@Transactional
public class DoubleMovieSubjectServiceImpl implements DoubleMovieSubjectService{

    private final Logger log = LoggerFactory.getLogger(DoubleMovieSubjectServiceImpl.class);
    
    @Inject
    private DoubleMovieSubjectRepository doubleMovieSubjectRepository;

    /**
     * Save a doubleMovieSubject.
     *
     * @param doubleMovieSubject the entity to save
     * @return the persisted entity
     */
    public DoubleMovieSubject save(DoubleMovieSubject doubleMovieSubject) {
        log.debug("Request to save DoubleMovieSubject : {}", doubleMovieSubject);
        DoubleMovieSubject result = doubleMovieSubjectRepository.save(doubleMovieSubject);
        return result;
    }

    /**
     *  Get all the doubleMovieSubjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DoubleMovieSubject> findAll(Pageable pageable) {
        log.debug("Request to get all DoubleMovieSubjects");
        Page<DoubleMovieSubject> result = doubleMovieSubjectRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one doubleMovieSubject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DoubleMovieSubject findOne(Long id) {
        log.debug("Request to get DoubleMovieSubject : {}", id);
        DoubleMovieSubject doubleMovieSubject = doubleMovieSubjectRepository.findOne(id);
        return doubleMovieSubject;
    }

    /**
     *  Delete the  doubleMovieSubject by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DoubleMovieSubject : {}", id);
        doubleMovieSubjectRepository.delete(id);
    }
}
