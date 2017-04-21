package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.MovieSuccessLog;
import com.intelligent.chart.service.MovieSuccessLogService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MovieSuccessLog.
 */
@RestController
@RequestMapping("/api")
public class MovieSuccessLogResource {

    private final Logger log = LoggerFactory.getLogger(MovieSuccessLogResource.class);
        
    @Inject
    private MovieSuccessLogService movieSuccessLogService;

    /**
     * POST  /movie-success-logs : Create a new movieSuccessLog.
     *
     * @param movieSuccessLog the movieSuccessLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieSuccessLog, or with status 400 (Bad Request) if the movieSuccessLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-success-logs")
    @Timed
    public ResponseEntity<MovieSuccessLog> createMovieSuccessLog(@Valid @RequestBody MovieSuccessLog movieSuccessLog) throws URISyntaxException {
        log.debug("REST request to save MovieSuccessLog : {}", movieSuccessLog);
        if (movieSuccessLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("movieSuccessLog", "idexists", "A new movieSuccessLog cannot already have an ID")).body(null);
        }
        MovieSuccessLog result = movieSuccessLogService.save(movieSuccessLog);
        return ResponseEntity.created(new URI("/api/movie-success-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("movieSuccessLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-success-logs : Updates an existing movieSuccessLog.
     *
     * @param movieSuccessLog the movieSuccessLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieSuccessLog,
     * or with status 400 (Bad Request) if the movieSuccessLog is not valid,
     * or with status 500 (Internal Server Error) if the movieSuccessLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-success-logs")
    @Timed
    public ResponseEntity<MovieSuccessLog> updateMovieSuccessLog(@Valid @RequestBody MovieSuccessLog movieSuccessLog) throws URISyntaxException {
        log.debug("REST request to update MovieSuccessLog : {}", movieSuccessLog);
        if (movieSuccessLog.getId() == null) {
            return createMovieSuccessLog(movieSuccessLog);
        }
        MovieSuccessLog result = movieSuccessLogService.save(movieSuccessLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("movieSuccessLog", movieSuccessLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-success-logs : get all the movieSuccessLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieSuccessLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/movie-success-logs")
    @Timed
    public ResponseEntity<List<MovieSuccessLog>> getAllMovieSuccessLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MovieSuccessLogs");
        Page<MovieSuccessLog> page = movieSuccessLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-success-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /movie-success-logs/:id : get the "id" movieSuccessLog.
     *
     * @param id the id of the movieSuccessLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieSuccessLog, or with status 404 (Not Found)
     */
    @GetMapping("/movie-success-logs/{id}")
    @Timed
    public ResponseEntity<MovieSuccessLog> getMovieSuccessLog(@PathVariable Long id) {
        log.debug("REST request to get MovieSuccessLog : {}", id);
        MovieSuccessLog movieSuccessLog = movieSuccessLogService.findOne(id);
        return Optional.ofNullable(movieSuccessLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /movie-success-logs/:id : delete the "id" movieSuccessLog.
     *
     * @param id the id of the movieSuccessLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-success-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieSuccessLog(@PathVariable Long id) {
        log.debug("REST request to delete MovieSuccessLog : {}", id);
        movieSuccessLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("movieSuccessLog", id.toString())).build();
    }

}
