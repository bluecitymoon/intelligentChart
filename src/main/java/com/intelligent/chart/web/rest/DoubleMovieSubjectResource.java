package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.DoubleMovieSubject;
import com.intelligent.chart.service.DoubleMovieSubjectService;
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
 * REST controller for managing DoubleMovieSubject.
 */
@RestController
@RequestMapping("/api")
public class DoubleMovieSubjectResource {

    private final Logger log = LoggerFactory.getLogger(DoubleMovieSubjectResource.class);
        
    @Inject
    private DoubleMovieSubjectService doubleMovieSubjectService;

    /**
     * POST  /double-movie-subjects : Create a new doubleMovieSubject.
     *
     * @param doubleMovieSubject the doubleMovieSubject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doubleMovieSubject, or with status 400 (Bad Request) if the doubleMovieSubject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/double-movie-subjects")
    @Timed
    public ResponseEntity<DoubleMovieSubject> createDoubleMovieSubject(@Valid @RequestBody DoubleMovieSubject doubleMovieSubject) throws URISyntaxException {
        log.debug("REST request to save DoubleMovieSubject : {}", doubleMovieSubject);
        if (doubleMovieSubject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("doubleMovieSubject", "idexists", "A new doubleMovieSubject cannot already have an ID")).body(null);
        }
        DoubleMovieSubject result = doubleMovieSubjectService.save(doubleMovieSubject);
        return ResponseEntity.created(new URI("/api/double-movie-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("doubleMovieSubject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /double-movie-subjects : Updates an existing doubleMovieSubject.
     *
     * @param doubleMovieSubject the doubleMovieSubject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doubleMovieSubject,
     * or with status 400 (Bad Request) if the doubleMovieSubject is not valid,
     * or with status 500 (Internal Server Error) if the doubleMovieSubject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/double-movie-subjects")
    @Timed
    public ResponseEntity<DoubleMovieSubject> updateDoubleMovieSubject(@Valid @RequestBody DoubleMovieSubject doubleMovieSubject) throws URISyntaxException {
        log.debug("REST request to update DoubleMovieSubject : {}", doubleMovieSubject);
        if (doubleMovieSubject.getId() == null) {
            return createDoubleMovieSubject(doubleMovieSubject);
        }
        DoubleMovieSubject result = doubleMovieSubjectService.save(doubleMovieSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("doubleMovieSubject", doubleMovieSubject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /double-movie-subjects : get all the doubleMovieSubjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doubleMovieSubjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/double-movie-subjects")
    @Timed
    public ResponseEntity<List<DoubleMovieSubject>> getAllDoubleMovieSubjects(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DoubleMovieSubjects");
        Page<DoubleMovieSubject> page = doubleMovieSubjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/double-movie-subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /double-movie-subjects/:id : get the "id" doubleMovieSubject.
     *
     * @param id the id of the doubleMovieSubject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doubleMovieSubject, or with status 404 (Not Found)
     */
    @GetMapping("/double-movie-subjects/{id}")
    @Timed
    public ResponseEntity<DoubleMovieSubject> getDoubleMovieSubject(@PathVariable Long id) {
        log.debug("REST request to get DoubleMovieSubject : {}", id);
        DoubleMovieSubject doubleMovieSubject = doubleMovieSubjectService.findOne(id);
        return Optional.ofNullable(doubleMovieSubject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /double-movie-subjects/:id : delete the "id" doubleMovieSubject.
     *
     * @param id the id of the doubleMovieSubject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/double-movie-subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoubleMovieSubject(@PathVariable Long id) {
        log.debug("REST request to delete DoubleMovieSubject : {}", id);
        doubleMovieSubjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("doubleMovieSubject", id.toString())).build();
    }

}
