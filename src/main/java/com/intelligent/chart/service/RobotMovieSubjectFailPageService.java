package com.intelligent.chart.service;

import com.intelligent.chart.domain.RobotMovieSubjectFailPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing RobotMovieSubjectFailPage.
 */
public interface RobotMovieSubjectFailPageService {

    /**
     * Save a robotMovieSubjectFailPage.
     *
     * @param robotMovieSubjectFailPage the entity to save
     * @return the persisted entity
     */
    RobotMovieSubjectFailPage save(RobotMovieSubjectFailPage robotMovieSubjectFailPage);

    /**
     *  Get all the robotMovieSubjectFailPages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RobotMovieSubjectFailPage> findAll(Pageable pageable);

    /**
     *  Get the "id" robotMovieSubjectFailPage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RobotMovieSubjectFailPage findOne(Long id);

    /**
     *  Delete the "id" robotMovieSubjectFailPage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
