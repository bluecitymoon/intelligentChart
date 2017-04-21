package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.MovieCategory;
import com.intelligent.chart.service.MovieCategoryService;
import com.intelligent.chart.web.rest.util.HeaderUtil;
import com.intelligent.chart.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MovieCategory.
 */
@RestController
@RequestMapping("/api")
public class MovieCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MovieCategoryResource.class);
        
    @Inject
    private MovieCategoryService movieCategoryService;

    /**
     * POST  /movie-categories : Create a new movieCategory.
     *
     * @param movieCategory the movieCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieCategory, or with status 400 (Bad Request) if the movieCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-categories")
    @Timed
    public ResponseEntity<MovieCategory> createMovieCategory(@RequestBody MovieCategory movieCategory) throws URISyntaxException {
        log.debug("REST request to save MovieCategory : {}", movieCategory);
        if (movieCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("movieCategory", "idexists", "A new movieCategory cannot already have an ID")).body(null);
        }
        MovieCategory result = movieCategoryService.save(movieCategory);
        return ResponseEntity.created(new URI("/api/movie-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("movieCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-categories : Updates an existing movieCategory.
     *
     * @param movieCategory the movieCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieCategory,
     * or with status 400 (Bad Request) if the movieCategory is not valid,
     * or with status 500 (Internal Server Error) if the movieCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-categories")
    @Timed
    public ResponseEntity<MovieCategory> updateMovieCategory(@RequestBody MovieCategory movieCategory) throws URISyntaxException {
        log.debug("REST request to update MovieCategory : {}", movieCategory);
        if (movieCategory.getId() == null) {
            return createMovieCategory(movieCategory);
        }
        MovieCategory result = movieCategoryService.save(movieCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("movieCategory", movieCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-categories : get all the movieCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieCategories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/movie-categories")
    @Timed
    public ResponseEntity<List<MovieCategory>> getAllMovieCategories(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MovieCategories");
        Page<MovieCategory> page = movieCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /movie-categories/:id : get the "id" movieCategory.
     *
     * @param id the id of the movieCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieCategory, or with status 404 (Not Found)
     */
    @GetMapping("/movie-categories/{id}")
    @Timed
    public ResponseEntity<MovieCategory> getMovieCategory(@PathVariable Long id) {
        log.debug("REST request to get MovieCategory : {}", id);
        MovieCategory movieCategory = movieCategoryService.findOne(id);
        return Optional.ofNullable(movieCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /movie-categories/:id : delete the "id" movieCategory.
     *
     * @param id the id of the movieCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieCategory(@PathVariable Long id) {
        log.debug("REST request to delete MovieCategory : {}", id);
        movieCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("movieCategory", id.toString())).build();
    }

}
