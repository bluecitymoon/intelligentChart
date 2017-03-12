package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFansEgeLevel;
import com.intelligent.chart.service.PersonFansEgeLevelService;import com.intelligent.chart.repository.PersonFansEgeLevelRepository;
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
 * REST controller for managing PersonFansEgeLevel.
 */
@RestController
@RequestMapping("/api")
public class PersonFansEgeLevelResource {

    private final Logger log = LoggerFactory.getLogger(PersonFansEgeLevelResource.class);
        
    @Inject
    private PersonFansEgeLevelService personFansEgeLevelService;

    @Inject
    private PersonFansEgeLevelRepository personFansEgeLevelRepository;


    @GetMapping("/person-fans-ege-levels/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFansEgeLevel>> getAllPersonPersonFansEgeLevelByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFansEgeLevel> page = personFansEgeLevelRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-ege-levels");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fans-ege-levels : Create a new personFansEgeLevel.
     *
     * @param personFansEgeLevel the personFansEgeLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFansEgeLevel, or with status 400 (Bad Request) if the personFansEgeLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fans-ege-levels")
    @Timed
    public ResponseEntity<PersonFansEgeLevel> createPersonFansEgeLevel(@RequestBody PersonFansEgeLevel personFansEgeLevel) throws URISyntaxException {
        log.debug("REST request to save PersonFansEgeLevel : {}", personFansEgeLevel);
        if (personFansEgeLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFansEgeLevel", "idexists", "A new personFansEgeLevel cannot already have an ID")).body(null);
        }
        PersonFansEgeLevel result = personFansEgeLevelService.save(personFansEgeLevel);
        return ResponseEntity.created(new URI("/api/person-fans-ege-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFansEgeLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fans-ege-levels : Updates an existing personFansEgeLevel.
     *
     * @param personFansEgeLevel the personFansEgeLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFansEgeLevel,
     * or with status 400 (Bad Request) if the personFansEgeLevel is not valid,
     * or with status 500 (Internal Server Error) if the personFansEgeLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fans-ege-levels")
    @Timed
    public ResponseEntity<PersonFansEgeLevel> updatePersonFansEgeLevel(@RequestBody PersonFansEgeLevel personFansEgeLevel) throws URISyntaxException {
        log.debug("REST request to update PersonFansEgeLevel : {}", personFansEgeLevel);
        if (personFansEgeLevel.getId() == null) {
            return createPersonFansEgeLevel(personFansEgeLevel);
        }
        PersonFansEgeLevel result = personFansEgeLevelService.save(personFansEgeLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFansEgeLevel", personFansEgeLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fans-ege-levels : get all the personFansEgeLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFansEgeLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fans-ege-levels")
    @Timed
    public ResponseEntity<List<PersonFansEgeLevel>> getAllPersonFansEgeLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFansEgeLevels");
        Page<PersonFansEgeLevel> page = personFansEgeLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-ege-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fans-ege-levels/:id : get the "id" personFansEgeLevel.
     *
     * @param id the id of the personFansEgeLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFansEgeLevel, or with status 404 (Not Found)
     */
    @GetMapping("/person-fans-ege-levels/{id}")
    @Timed
    public ResponseEntity<PersonFansEgeLevel> getPersonFansEgeLevel(@PathVariable Long id) {
        log.debug("REST request to get PersonFansEgeLevel : {}", id);
        PersonFansEgeLevel personFansEgeLevel = personFansEgeLevelService.findOne(id);
        return Optional.ofNullable(personFansEgeLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fans-ege-levels/:id : delete the "id" personFansEgeLevel.
     *
     * @param id the id of the personFansEgeLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fans-ege-levels/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFansEgeLevel(@PathVariable Long id) {
        log.debug("REST request to delete PersonFansEgeLevel : {}", id);
        personFansEgeLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFansEgeLevel", id.toString())).build();
    }

}
