package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.RobotMovieSubjectSuccessPageService;
import com.intelligent.chart.domain.RobotMovieSubjectSuccessPage;
import com.intelligent.chart.repository.RobotMovieSubjectSuccessPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing RobotMovieSubjectSuccessPage.
 */
@Service
@Transactional
public class RobotMovieSubjectSuccessPageServiceImpl implements RobotMovieSubjectSuccessPageService{

    private final Logger log = LoggerFactory.getLogger(RobotMovieSubjectSuccessPageServiceImpl.class);
    
    @Inject
    private RobotMovieSubjectSuccessPageRepository robotMovieSubjectSuccessPageRepository;

    /**
     * Save a robotMovieSubjectSuccessPage.
     *
     * @param robotMovieSubjectSuccessPage the entity to save
     * @return the persisted entity
     */
    public RobotMovieSubjectSuccessPage save(RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage) {
        log.debug("Request to save RobotMovieSubjectSuccessPage : {}", robotMovieSubjectSuccessPage);
        RobotMovieSubjectSuccessPage result = robotMovieSubjectSuccessPageRepository.save(robotMovieSubjectSuccessPage);
        return result;
    }

    /**
     *  Get all the robotMovieSubjectSuccessPages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RobotMovieSubjectSuccessPage> findAll(Pageable pageable) {
        log.debug("Request to get all RobotMovieSubjectSuccessPages");
        Page<RobotMovieSubjectSuccessPage> result = robotMovieSubjectSuccessPageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one robotMovieSubjectSuccessPage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RobotMovieSubjectSuccessPage findOne(Long id) {
        log.debug("Request to get RobotMovieSubjectSuccessPage : {}", id);
        RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage = robotMovieSubjectSuccessPageRepository.findOne(id);
        return robotMovieSubjectSuccessPage;
    }

    /**
     *  Delete the  robotMovieSubjectSuccessPage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RobotMovieSubjectSuccessPage : {}", id);
        robotMovieSubjectSuccessPageRepository.delete(id);
    }
}
