package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.RobotMovieSubjectFailPage;
import com.intelligent.chart.service.RobotMovieSubjectFailPageService;
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
 * REST controller for managing RobotMovieSubjectFailPage.
 */
@RestController
@RequestMapping("/api")
public class RobotMovieSubjectFailPageResource {

    private final Logger log = LoggerFactory.getLogger(RobotMovieSubjectFailPageResource.class);
        
    @Inject
    private RobotMovieSubjectFailPageService robotMovieSubjectFailPageService;

    /**
     * POST  /robot-movie-subject-fail-pages : Create a new robotMovieSubjectFailPage.
     *
     * @param robotMovieSubjectFailPage the robotMovieSubjectFailPage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new robotMovieSubjectFailPage, or with status 400 (Bad Request) if the robotMovieSubjectFailPage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/robot-movie-subject-fail-pages")
    @Timed
    public ResponseEntity<RobotMovieSubjectFailPage> createRobotMovieSubjectFailPage(@Valid @RequestBody RobotMovieSubjectFailPage robotMovieSubjectFailPage) throws URISyntaxException {
        log.debug("REST request to save RobotMovieSubjectFailPage : {}", robotMovieSubjectFailPage);
        if (robotMovieSubjectFailPage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("robotMovieSubjectFailPage", "idexists", "A new robotMovieSubjectFailPage cannot already have an ID")).body(null);
        }
        RobotMovieSubjectFailPage result = robotMovieSubjectFailPageService.save(robotMovieSubjectFailPage);
        return ResponseEntity.created(new URI("/api/robot-movie-subject-fail-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("robotMovieSubjectFailPage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /robot-movie-subject-fail-pages : Updates an existing robotMovieSubjectFailPage.
     *
     * @param robotMovieSubjectFailPage the robotMovieSubjectFailPage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated robotMovieSubjectFailPage,
     * or with status 400 (Bad Request) if the robotMovieSubjectFailPage is not valid,
     * or with status 500 (Internal Server Error) if the robotMovieSubjectFailPage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/robot-movie-subject-fail-pages")
    @Timed
    public ResponseEntity<RobotMovieSubjectFailPage> updateRobotMovieSubjectFailPage(@Valid @RequestBody RobotMovieSubjectFailPage robotMovieSubjectFailPage) throws URISyntaxException {
        log.debug("REST request to update RobotMovieSubjectFailPage : {}", robotMovieSubjectFailPage);
        if (robotMovieSubjectFailPage.getId() == null) {
            return createRobotMovieSubjectFailPage(robotMovieSubjectFailPage);
        }
        RobotMovieSubjectFailPage result = robotMovieSubjectFailPageService.save(robotMovieSubjectFailPage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("robotMovieSubjectFailPage", robotMovieSubjectFailPage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /robot-movie-subject-fail-pages : get all the robotMovieSubjectFailPages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of robotMovieSubjectFailPages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/robot-movie-subject-fail-pages")
    @Timed
    public ResponseEntity<List<RobotMovieSubjectFailPage>> getAllRobotMovieSubjectFailPages(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RobotMovieSubjectFailPages");
        Page<RobotMovieSubjectFailPage> page = robotMovieSubjectFailPageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/robot-movie-subject-fail-pages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /robot-movie-subject-fail-pages/:id : get the "id" robotMovieSubjectFailPage.
     *
     * @param id the id of the robotMovieSubjectFailPage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the robotMovieSubjectFailPage, or with status 404 (Not Found)
     */
    @GetMapping("/robot-movie-subject-fail-pages/{id}")
    @Timed
    public ResponseEntity<RobotMovieSubjectFailPage> getRobotMovieSubjectFailPage(@PathVariable Long id) {
        log.debug("REST request to get RobotMovieSubjectFailPage : {}", id);
        RobotMovieSubjectFailPage robotMovieSubjectFailPage = robotMovieSubjectFailPageService.findOne(id);
        return Optional.ofNullable(robotMovieSubjectFailPage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /robot-movie-subject-fail-pages/:id : delete the "id" robotMovieSubjectFailPage.
     *
     * @param id the id of the robotMovieSubjectFailPage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/robot-movie-subject-fail-pages/{id}")
    @Timed
    public ResponseEntity<Void> deleteRobotMovieSubjectFailPage(@PathVariable Long id) {
        log.debug("REST request to delete RobotMovieSubjectFailPage : {}", id);
        robotMovieSubjectFailPageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("robotMovieSubjectFailPage", id.toString())).build();
    }

}
