package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.MovieParticipant;
import com.intelligent.chart.service.MovieParticipantService;
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
 * REST controller for managing MovieParticipant.
 */
@RestController
@RequestMapping("/api")
public class MovieParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MovieParticipantResource.class);
        
    @Inject
    private MovieParticipantService movieParticipantService;

    /**
     * POST  /movie-participants : Create a new movieParticipant.
     *
     * @param movieParticipant the movieParticipant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieParticipant, or with status 400 (Bad Request) if the movieParticipant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-participants")
    @Timed
    public ResponseEntity<MovieParticipant> createMovieParticipant(@RequestBody MovieParticipant movieParticipant) throws URISyntaxException {
        log.debug("REST request to save MovieParticipant : {}", movieParticipant);
        if (movieParticipant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("movieParticipant", "idexists", "A new movieParticipant cannot already have an ID")).body(null);
        }
        MovieParticipant result = movieParticipantService.save(movieParticipant);
        return ResponseEntity.created(new URI("/api/movie-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("movieParticipant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-participants : Updates an existing movieParticipant.
     *
     * @param movieParticipant the movieParticipant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieParticipant,
     * or with status 400 (Bad Request) if the movieParticipant is not valid,
     * or with status 500 (Internal Server Error) if the movieParticipant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-participants")
    @Timed
    public ResponseEntity<MovieParticipant> updateMovieParticipant(@RequestBody MovieParticipant movieParticipant) throws URISyntaxException {
        log.debug("REST request to update MovieParticipant : {}", movieParticipant);
        if (movieParticipant.getId() == null) {
            return createMovieParticipant(movieParticipant);
        }
        MovieParticipant result = movieParticipantService.save(movieParticipant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("movieParticipant", movieParticipant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-participants : get all the movieParticipants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieParticipants in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/movie-participants")
    @Timed
    public ResponseEntity<List<MovieParticipant>> getAllMovieParticipants(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MovieParticipants");
        Page<MovieParticipant> page = movieParticipantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-participants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /movie-participants/:id : get the "id" movieParticipant.
     *
     * @param id the id of the movieParticipant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieParticipant, or with status 404 (Not Found)
     */
    @GetMapping("/movie-participants/{id}")
    @Timed
    public ResponseEntity<MovieParticipant> getMovieParticipant(@PathVariable Long id) {
        log.debug("REST request to get MovieParticipant : {}", id);
        MovieParticipant movieParticipant = movieParticipantService.findOne(id);
        return Optional.ofNullable(movieParticipant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /movie-participants/:id : delete the "id" movieParticipant.
     *
     * @param id the id of the movieParticipant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-participants/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MovieParticipant : {}", id);
        movieParticipantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("movieParticipant", id.toString())).build();
    }

}
