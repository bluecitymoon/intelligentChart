package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.ConnectionLevel;
import com.intelligent.chart.service.ConnectionLevelService;
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
 * REST controller for managing ConnectionLevel.
 */
@RestController
@RequestMapping("/api")
public class ConnectionLevelResource {

    private final Logger log = LoggerFactory.getLogger(ConnectionLevelResource.class);
        
    @Inject
    private ConnectionLevelService connectionLevelService;

    /**
     * POST  /connection-levels : Create a new connectionLevel.
     *
     * @param connectionLevel the connectionLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connectionLevel, or with status 400 (Bad Request) if the connectionLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/connection-levels")
    @Timed
    public ResponseEntity<ConnectionLevel> createConnectionLevel(@RequestBody ConnectionLevel connectionLevel) throws URISyntaxException {
        log.debug("REST request to save ConnectionLevel : {}", connectionLevel);
        if (connectionLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("connectionLevel", "idexists", "A new connectionLevel cannot already have an ID")).body(null);
        }
        ConnectionLevel result = connectionLevelService.save(connectionLevel);
        return ResponseEntity.created(new URI("/api/connection-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("connectionLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /connection-levels : Updates an existing connectionLevel.
     *
     * @param connectionLevel the connectionLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated connectionLevel,
     * or with status 400 (Bad Request) if the connectionLevel is not valid,
     * or with status 500 (Internal Server Error) if the connectionLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/connection-levels")
    @Timed
    public ResponseEntity<ConnectionLevel> updateConnectionLevel(@RequestBody ConnectionLevel connectionLevel) throws URISyntaxException {
        log.debug("REST request to update ConnectionLevel : {}", connectionLevel);
        if (connectionLevel.getId() == null) {
            return createConnectionLevel(connectionLevel);
        }
        ConnectionLevel result = connectionLevelService.save(connectionLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("connectionLevel", connectionLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /connection-levels : get all the connectionLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of connectionLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/connection-levels")
    @Timed
    public ResponseEntity<List<ConnectionLevel>> getAllConnectionLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ConnectionLevels");
        Page<ConnectionLevel> page = connectionLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/connection-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /connection-levels/:id : get the "id" connectionLevel.
     *
     * @param id the id of the connectionLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the connectionLevel, or with status 404 (Not Found)
     */
    @GetMapping("/connection-levels/{id}")
    @Timed
    public ResponseEntity<ConnectionLevel> getConnectionLevel(@PathVariable Long id) {
        log.debug("REST request to get ConnectionLevel : {}", id);
        ConnectionLevel connectionLevel = connectionLevelService.findOne(id);
        return Optional.ofNullable(connectionLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /connection-levels/:id : delete the "id" connectionLevel.
     *
     * @param id the id of the connectionLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/connection-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteConnectionLevel(@PathVariable Long id) {
        log.debug("REST request to delete ConnectionLevel : {}", id);
        connectionLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("connectionLevel", id.toString())).build();
    }

}
