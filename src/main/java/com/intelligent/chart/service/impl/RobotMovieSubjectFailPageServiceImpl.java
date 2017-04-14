package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.RobotMovieSubjectFailPageService;
import com.intelligent.chart.domain.RobotMovieSubjectFailPage;
import com.intelligent.chart.repository.RobotMovieSubjectFailPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing RobotMovieSubjectFailPage.
 */
@Service
@Transactional
public class RobotMovieSubjectFailPageServiceImpl implements RobotMovieSubjectFailPageService{

    private final Logger log = LoggerFactory.getLogger(RobotMovieSubjectFailPageServiceImpl.class);
    
    @Inject
    private RobotMovieSubjectFailPageRepository robotMovieSubjectFailPageRepository;

    /**
     * Save a robotMovieSubjectFailPage.
     *
     * @param robotMovieSubjectFailPage the entity to save
     * @return the persisted entity
     */
    public RobotMovieSubjectFailPage save(RobotMovieSubjectFailPage robotMovieSubjectFailPage) {
        log.debug("Request to save RobotMovieSubjectFailPage : {}", robotMovieSubjectFailPage);
        RobotMovieSubjectFailPage result = robotMovieSubjectFailPageRepository.save(robotMovieSubjectFailPage);
        return result;
    }

    /**
     *  Get all the robotMovieSubjectFailPages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RobotMovieSubjectFailPage> findAll(Pageable pageable) {
        log.debug("Request to get all RobotMovieSubjectFailPages");
        Page<RobotMovieSubjectFailPage> result = robotMovieSubjectFailPageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one robotMovieSubjectFailPage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RobotMovieSubjectFailPage findOne(Long id) {
        log.debug("Request to get RobotMovieSubjectFailPage : {}", id);
        RobotMovieSubjectFailPage robotMovieSubjectFailPage = robotMovieSubjectFailPageRepository.findOne(id);
        return robotMovieSubjectFailPage;
    }

    /**
     *  Delete the  robotMovieSubjectFailPage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RobotMovieSubjectFailPage : {}", id);
        robotMovieSubjectFailPageRepository.delete(id);
    }
}
