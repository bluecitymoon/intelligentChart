package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonRegionConnection;
import com.intelligent.chart.service.PersonRegionConnectionService;
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
 * REST controller for managing PersonRegionConnection.
 */
@RestController
@RequestMapping("/api")
public class PersonRegionConnectionResource {

    private final Logger log = LoggerFactory.getLogger(PersonRegionConnectionResource.class);
        
    @Inject
    private PersonRegionConnectionService personRegionConnectionService;

    /**
     * POST  /person-region-connections : Create a new personRegionConnection.
     *
     * @param personRegionConnection the personRegionConnection to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personRegionConnection, or with status 400 (Bad Request) if the personRegionConnection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-region-connections")
    @Timed
    public ResponseEntity<PersonRegionConnection> createPersonRegionConnection(@RequestBody PersonRegionConnection personRegionConnection) throws URISyntaxException {
        log.debug("REST request to save PersonRegionConnection : {}", personRegionConnection);
        if (personRegionConnection.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personRegionConnection", "idexists", "A new personRegionConnection cannot already have an ID")).body(null);
        }
        PersonRegionConnection result = personRegionConnectionService.save(personRegionConnection);
        return ResponseEntity.created(new URI("/api/person-region-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personRegionConnection", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-region-connections : Updates an existing personRegionConnection.
     *
     * @param personRegionConnection the personRegionConnection to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personRegionConnection,
     * or with status 400 (Bad Request) if the personRegionConnection is not valid,
     * or with status 500 (Internal Server Error) if the personRegionConnection couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-region-connections")
    @Timed
    public ResponseEntity<PersonRegionConnection> updatePersonRegionConnection(@RequestBody PersonRegionConnection personRegionConnection) throws URISyntaxException {
        log.debug("REST request to update PersonRegionConnection : {}", personRegionConnection);
        if (personRegionConnection.getId() == null) {
            return createPersonRegionConnection(personRegionConnection);
        }
        PersonRegionConnection result = personRegionConnectionService.save(personRegionConnection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personRegionConnection", personRegionConnection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-region-connections : get all the personRegionConnections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personRegionConnections in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-region-connections")
    @Timed
    public ResponseEntity<List<PersonRegionConnection>> getAllPersonRegionConnections(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonRegionConnections");
        Page<PersonRegionConnection> page = personRegionConnectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-region-connections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-region-connections/:id : get the "id" personRegionConnection.
     *
     * @param id the id of the personRegionConnection to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personRegionConnection, or with status 404 (Not Found)
     */
    @GetMapping("/person-region-connections/{id}")
    @Timed
    public ResponseEntity<PersonRegionConnection> getPersonRegionConnection(@PathVariable Long id) {
        log.debug("REST request to get PersonRegionConnection : {}", id);
        PersonRegionConnection personRegionConnection = personRegionConnectionService.findOne(id);
        return Optional.ofNullable(personRegionConnection)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-region-connections/:id : delete the "id" personRegionConnection.
     *
     * @param id the id of the personRegionConnection to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-region-connections/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonRegionConnection(@PathVariable Long id) {
        log.debug("REST request to delete PersonRegionConnection : {}", id);
        personRegionConnectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personRegionConnection", id.toString())).build();
    }

}
