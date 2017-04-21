package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MovieSuccessLogService;
import com.intelligent.chart.domain.MovieSuccessLog;
import com.intelligent.chart.repository.MovieSuccessLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MovieSuccessLog.
 */
@Service
@Transactional
public class MovieSuccessLogServiceImpl implements MovieSuccessLogService{

    private final Logger log = LoggerFactory.getLogger(MovieSuccessLogServiceImpl.class);

    @Inject
    private MovieSuccessLogRepository movieSuccessLogRepository;

    /**
     * Save a movieSuccessLog.
     *
     * @param movieSuccessLog the entity to save
     * @return the persisted entity
     */
    public MovieSuccessLog save(MovieSuccessLog movieSuccessLog) {
        log.debug("Request to save MovieSuccessLog : {}", movieSuccessLog);
        MovieSuccessLog result = movieSuccessLogRepository.save(movieSuccessLog);
        return result;
    }

    /**
     *  Get all the movieSuccessLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MovieSuccessLog> findAll(Pageable pageable) {
        log.debug("Request to get all MovieSuccessLogs");
        Page<MovieSuccessLog> result = movieSuccessLogRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one movieSuccessLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MovieSuccessLog findOne(Long id) {
        log.debug("Request to get MovieSuccessLog : {}", id);
        MovieSuccessLog movieSuccessLog = movieSuccessLogRepository.findOne(id);
        return movieSuccessLog;
    }

    /**
     *  Delete the  movieSuccessLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieSuccessLog : {}", id);
        movieSuccessLogRepository.delete(id);
    }

    @Override
    public boolean isMovieGrabed(String doubanId) {

        try {

            MovieSuccessLog movieSuccessLog = movieSuccessLogRepository.findByDoubanId(doubanId);

            return movieSuccessLog != null;

        } catch (Exception e) {

            return false;
        }
    }
}
