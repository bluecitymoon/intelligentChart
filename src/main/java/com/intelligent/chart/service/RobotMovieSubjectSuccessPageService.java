package com.intelligent.chart.service;

import com.intelligent.chart.domain.RobotMovieSubjectSuccessPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing RobotMovieSubjectSuccessPage.
 */
public interface RobotMovieSubjectSuccessPageService {

    /**
     * Save a robotMovieSubjectSuccessPage.
     *
     * @param robotMovieSubjectSuccessPage the entity to save
     * @return the persisted entity
     */
    RobotMovieSubjectSuccessPage save(RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage);

    /**
     *  Get all the robotMovieSubjectSuccessPages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RobotMovieSubjectSuccessPage> findAll(Pageable pageable);

    /**
     *  Get the "id" robotMovieSubjectSuccessPage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RobotMovieSubjectSuccessPage findOne(Long id);

    /**
     *  Delete the "id" robotMovieSubjectSuccessPage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
