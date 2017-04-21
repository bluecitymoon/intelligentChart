package com.intelligent.chart.service;

import com.intelligent.chart.domain.MovieSuccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MovieSuccessLog.
 */
public interface MovieSuccessLogService {

    /**
     * Save a movieSuccessLog.
     *
     * @param movieSuccessLog the entity to save
     * @return the persisted entity
     */
    MovieSuccessLog save(MovieSuccessLog movieSuccessLog);

    /**
     *  Get all the movieSuccessLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MovieSuccessLog> findAll(Pageable pageable);

    /**
     *  Get the "id" movieSuccessLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MovieSuccessLog findOne(Long id);

    /**
     *  Delete the "id" movieSuccessLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    boolean isMovieGrabed(String doubanId);
}
