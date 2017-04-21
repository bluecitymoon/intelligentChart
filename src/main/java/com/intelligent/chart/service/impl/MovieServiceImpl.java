package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MovieService;
import com.intelligent.chart.domain.Movie;
import com.intelligent.chart.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Movie.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
    
    @Inject
    private MovieRepository movieRepository;

    /**
     * Save a movie.
     *
     * @param movie the entity to save
     * @return the persisted entity
     */
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        Movie result = movieRepository.save(movie);
        return result;
    }

    /**
     *  Get all the movies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Movie> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        Page<Movie> result = movieRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one movie by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Movie findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        Movie movie = movieRepository.findOne(id);
        return movie;
    }

    /**
     *  Delete the  movie by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.delete(id);
    }
}
