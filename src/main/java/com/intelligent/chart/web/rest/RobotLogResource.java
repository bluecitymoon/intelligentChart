package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.RobotLog;
import com.intelligent.chart.service.RobotLogService;
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
 * REST controller for managing RobotLog.
 */
@RestController
@RequestMapping("/api")
public class RobotLogResource {

    private final Logger log = LoggerFactory.getLogger(RobotLogResource.class);
        
    @Inject
    private RobotLogService robotLogService;

    /**
     * POST  /robot-logs : Create a new robotLog.
     *
     * @param robotLog the robotLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new robotLog, or with status 400 (Bad Request) if the robotLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/robot-logs")
    @Timed
    public ResponseEntity<RobotLog> createRobotLog(@Valid @RequestBody RobotLog robotLog) throws URISyntaxException {
        log.debug("REST request to save RobotLog : {}", robotLog);
        if (robotLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("robotLog", "idexists", "A new robotLog cannot already have an ID")).body(null);
        }
        RobotLog result = robotLogService.save(robotLog);
        return ResponseEntity.created(new URI("/api/robot-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("robotLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /robot-logs : Updates an existing robotLog.
     *
     * @param robotLog the robotLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated robotLog,
     * or with status 400 (Bad Request) if the robotLog is not valid,
     * or with status 500 (Internal Server Error) if the robotLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/robot-logs")
    @Timed
    public ResponseEntity<RobotLog> updateRobotLog(@Valid @RequestBody RobotLog robotLog) throws URISyntaxException {
        log.debug("REST request to update RobotLog : {}", robotLog);
        if (robotLog.getId() == null) {
            return createRobotLog(robotLog);
        }
        RobotLog result = robotLogService.save(robotLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("robotLog", robotLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /robot-logs : get all the robotLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of robotLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/robot-logs")
    @Timed
    public ResponseEntity<List<RobotLog>> getAllRobotLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RobotLogs");
        Page<RobotLog> page = robotLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/robot-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /robot-logs/:id : get the "id" robotLog.
     *
     * @param id the id of the robotLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the robotLog, or with status 404 (Not Found)
     */
    @GetMapping("/robot-logs/{id}")
    @Timed
    public ResponseEntity<RobotLog> getRobotLog(@PathVariable Long id) {
        log.debug("REST request to get RobotLog : {}", id);
        RobotLog robotLog = robotLogService.findOne(id);
        return Optional.ofNullable(robotLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /robot-logs/:id : delete the "id" robotLog.
     *
     * @param id the id of the robotLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/robot-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRobotLog(@PathVariable Long id) {
        log.debug("REST request to delete RobotLog : {}", id);
        robotLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("robotLog", id.toString())).build();
    }

}
