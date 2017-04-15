package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.RobotMovieSubjectSuccessPage;
import com.intelligent.chart.service.RobotMovieSubjectSuccessPageService;
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
 * REST controller for managing RobotMovieSubjectSuccessPage.
 */
@RestController
@RequestMapping("/api")
public class RobotMovieSubjectSuccessPageResource {

    private final Logger log = LoggerFactory.getLogger(RobotMovieSubjectSuccessPageResource.class);
        
    @Inject
    private RobotMovieSubjectSuccessPageService robotMovieSubjectSuccessPageService;

    /**
     * POST  /robot-movie-subject-success-pages : Create a new robotMovieSubjectSuccessPage.
     *
     * @param robotMovieSubjectSuccessPage the robotMovieSubjectSuccessPage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new robotMovieSubjectSuccessPage, or with status 400 (Bad Request) if the robotMovieSubjectSuccessPage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/robot-movie-subject-success-pages")
    @Timed
    public ResponseEntity<RobotMovieSubjectSuccessPage> createRobotMovieSubjectSuccessPage(@RequestBody RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage) throws URISyntaxException {
        log.debug("REST request to save RobotMovieSubjectSuccessPage : {}", robotMovieSubjectSuccessPage);
        if (robotMovieSubjectSuccessPage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("robotMovieSubjectSuccessPage", "idexists", "A new robotMovieSubjectSuccessPage cannot already have an ID")).body(null);
        }
        RobotMovieSubjectSuccessPage result = robotMovieSubjectSuccessPageService.save(robotMovieSubjectSuccessPage);
        return ResponseEntity.created(new URI("/api/robot-movie-subject-success-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("robotMovieSubjectSuccessPage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /robot-movie-subject-success-pages : Updates an existing robotMovieSubjectSuccessPage.
     *
     * @param robotMovieSubjectSuccessPage the robotMovieSubjectSuccessPage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated robotMovieSubjectSuccessPage,
     * or with status 400 (Bad Request) if the robotMovieSubjectSuccessPage is not valid,
     * or with status 500 (Internal Server Error) if the robotMovieSubjectSuccessPage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/robot-movie-subject-success-pages")
    @Timed
    public ResponseEntity<RobotMovieSubjectSuccessPage> updateRobotMovieSubjectSuccessPage(@RequestBody RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage) throws URISyntaxException {
        log.debug("REST request to update RobotMovieSubjectSuccessPage : {}", robotMovieSubjectSuccessPage);
        if (robotMovieSubjectSuccessPage.getId() == null) {
            return createRobotMovieSubjectSuccessPage(robotMovieSubjectSuccessPage);
        }
        RobotMovieSubjectSuccessPage result = robotMovieSubjectSuccessPageService.save(robotMovieSubjectSuccessPage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("robotMovieSubjectSuccessPage", robotMovieSubjectSuccessPage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /robot-movie-subject-success-pages : get all the robotMovieSubjectSuccessPages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of robotMovieSubjectSuccessPages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/robot-movie-subject-success-pages")
    @Timed
    public ResponseEntity<List<RobotMovieSubjectSuccessPage>> getAllRobotMovieSubjectSuccessPages(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RobotMovieSubjectSuccessPages");
        Page<RobotMovieSubjectSuccessPage> page = robotMovieSubjectSuccessPageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/robot-movie-subject-success-pages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /robot-movie-subject-success-pages/:id : get the "id" robotMovieSubjectSuccessPage.
     *
     * @param id the id of the robotMovieSubjectSuccessPage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the robotMovieSubjectSuccessPage, or with status 404 (Not Found)
     */
    @GetMapping("/robot-movie-subject-success-pages/{id}")
    @Timed
    public ResponseEntity<RobotMovieSubjectSuccessPage> getRobotMovieSubjectSuccessPage(@PathVariable Long id) {
        log.debug("REST request to get RobotMovieSubjectSuccessPage : {}", id);
        RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage = robotMovieSubjectSuccessPageService.findOne(id);
        return Optional.ofNullable(robotMovieSubjectSuccessPage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /robot-movie-subject-success-pages/:id : delete the "id" robotMovieSubjectSuccessPage.
     *
     * @param id the id of the robotMovieSubjectSuccessPage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/robot-movie-subject-success-pages/{id}")
    @Timed
    public ResponseEntity<Void> deleteRobotMovieSubjectSuccessPage(@PathVariable Long id) {
        log.debug("REST request to delete RobotMovieSubjectSuccessPage : {}", id);
        robotMovieSubjectSuccessPageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("robotMovieSubjectSuccessPage", id.toString())).build();
    }

}
