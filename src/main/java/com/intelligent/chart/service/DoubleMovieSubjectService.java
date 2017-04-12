package com.intelligent.chart.service;

import com.intelligent.chart.domain.DoubleMovieSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DoubleMovieSubject.
 */
public interface DoubleMovieSubjectService {

    /**
     * Save a doubleMovieSubject.
     *
     * @param doubleMovieSubject the entity to save
     * @return the persisted entity
     */
    DoubleMovieSubject save(DoubleMovieSubject doubleMovieSubject);

    /**
     *  Get all the doubleMovieSubjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DoubleMovieSubject> findAll(Pageable pageable);

    /**
     *  Get the "id" doubleMovieSubject.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DoubleMovieSubject findOne(Long id);

    /**
     *  Delete the "id" doubleMovieSubject.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
