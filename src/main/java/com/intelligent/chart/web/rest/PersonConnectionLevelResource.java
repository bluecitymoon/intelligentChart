package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonConnectionLevel;
import com.intelligent.chart.domain.PersonEducationBackground;
import com.intelligent.chart.repository.PersonConnectionLevelRepository;
import com.intelligent.chart.repository.PersonEducationBackgroundRepository;
import com.intelligent.chart.service.PersonConnectionLevelService;
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
 * REST controller for managing PersonConnectionLevel.
 */
@RestController
@RequestMapping("/api")
public class PersonConnectionLevelResource {

    private final Logger log = LoggerFactory.getLogger(PersonConnectionLevelResource.class);

    @Inject
    private PersonConnectionLevelService personConnectionLevelService;

    @Inject
    private PersonConnectionLevelRepository personConnectionLevelRepository;

    @GetMapping("/person-connection-levels/person/{id}")
    @Timed
    public ResponseEntity<List<PersonConnectionLevel>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonConnectionLevel> page = personConnectionLevelRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-connection-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-connection-levels : Create a new personConnectionLevel.
     *
     * @param personConnectionLevel the personConnectionLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personConnectionLevel, or with status 400 (Bad Request) if the personConnectionLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-connection-levels")
    @Timed
    public ResponseEntity<PersonConnectionLevel> createPersonConnectionLevel(@RequestBody PersonConnectionLevel personConnectionLevel) throws URISyntaxException {
        log.debug("REST request to save PersonConnectionLevel : {}", personConnectionLevel);
        if (personConnectionLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personConnectionLevel", "idexists", "A new personConnectionLevel cannot already have an ID")).body(null);
        }
        PersonConnectionLevel result = personConnectionLevelService.save(personConnectionLevel);
        return ResponseEntity.created(new URI("/api/person-connection-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personConnectionLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-connection-levels : Updates an existing personConnectionLevel.
     *
     * @param personConnectionLevel the personConnectionLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personConnectionLevel,
     * or with status 400 (Bad Request) if the personConnectionLevel is not valid,
     * or with status 500 (Internal Server Error) if the personConnectionLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-connection-levels")
    @Timed
    public ResponseEntity<PersonConnectionLevel> updatePersonConnectionLevel(@RequestBody PersonConnectionLevel personConnectionLevel) throws URISyntaxException {
        log.debug("REST request to update PersonConnectionLevel : {}", personConnectionLevel);
        if (personConnectionLevel.getId() == null) {
            return createPersonConnectionLevel(personConnectionLevel);
        }
        PersonConnectionLevel result = personConnectionLevelService.save(personConnectionLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personConnectionLevel", personConnectionLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-connection-levels : get all the personConnectionLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personConnectionLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-connection-levels")
    @Timed
    public ResponseEntity<List<PersonConnectionLevel>> getAllPersonConnectionLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonConnectionLevels");
        Page<PersonConnectionLevel> page = personConnectionLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-connection-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-connection-levels/:id : get the "id" personConnectionLevel.
     *
     * @param id the id of the personConnectionLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personConnectionLevel, or with status 404 (Not Found)
     */
    @GetMapping("/person-connection-levels/{id}")
    @Timed
    public ResponseEntity<PersonConnectionLevel> getPersonConnectionLevel(@PathVariable Long id) {
        log.debug("REST request to get PersonConnectionLevel : {}", id);
        PersonConnectionLevel personConnectionLevel = personConnectionLevelService.findOne(id);
        return Optional.ofNullable(personConnectionLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-connection-levels/:id : delete the "id" personConnectionLevel.
     *
     * @param id the id of the personConnectionLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-connection-levels/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonConnectionLevel(@PathVariable Long id) {
        log.debug("REST request to delete PersonConnectionLevel : {}", id);
        personConnectionLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personConnectionLevel", id.toString())).build();
    }

}
