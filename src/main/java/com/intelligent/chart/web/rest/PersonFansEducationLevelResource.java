package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFansEducationLevel;
import com.intelligent.chart.service.PersonFansEducationLevelService;import com.intelligent.chart.repository.PersonFansEducationLevelRepository;
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
 * REST controller for managing PersonFansEducationLevel.
 */
@RestController
@RequestMapping("/api")
public class PersonFansEducationLevelResource {

    private final Logger log = LoggerFactory.getLogger(PersonFansEducationLevelResource.class);
        
    @Inject
    private PersonFansEducationLevelService personFansEducationLevelService;

    @Inject
    private PersonFansEducationLevelRepository personFansEducationLevelRepository;


    @GetMapping("/person-fans-education-levels/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFansEducationLevel>> getAllPersonPersonFansEducationLevelByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFansEducationLevel> page = personFansEducationLevelRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-education-levels");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fans-education-levels : Create a new personFansEducationLevel.
     *
     * @param personFansEducationLevel the personFansEducationLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFansEducationLevel, or with status 400 (Bad Request) if the personFansEducationLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fans-education-levels")
    @Timed
    public ResponseEntity<PersonFansEducationLevel> createPersonFansEducationLevel(@RequestBody PersonFansEducationLevel personFansEducationLevel) throws URISyntaxException {
        log.debug("REST request to save PersonFansEducationLevel : {}", personFansEducationLevel);
        if (personFansEducationLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFansEducationLevel", "idexists", "A new personFansEducationLevel cannot already have an ID")).body(null);
        }
        PersonFansEducationLevel result = personFansEducationLevelService.save(personFansEducationLevel);
        return ResponseEntity.created(new URI("/api/person-fans-education-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFansEducationLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fans-education-levels : Updates an existing personFansEducationLevel.
     *
     * @param personFansEducationLevel the personFansEducationLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFansEducationLevel,
     * or with status 400 (Bad Request) if the personFansEducationLevel is not valid,
     * or with status 500 (Internal Server Error) if the personFansEducationLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fans-education-levels")
    @Timed
    public ResponseEntity<PersonFansEducationLevel> updatePersonFansEducationLevel(@RequestBody PersonFansEducationLevel personFansEducationLevel) throws URISyntaxException {
        log.debug("REST request to update PersonFansEducationLevel : {}", personFansEducationLevel);
        if (personFansEducationLevel.getId() == null) {
            return createPersonFansEducationLevel(personFansEducationLevel);
        }
        PersonFansEducationLevel result = personFansEducationLevelService.save(personFansEducationLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFansEducationLevel", personFansEducationLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fans-education-levels : get all the personFansEducationLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFansEducationLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fans-education-levels")
    @Timed
    public ResponseEntity<List<PersonFansEducationLevel>> getAllPersonFansEducationLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFansEducationLevels");
        Page<PersonFansEducationLevel> page = personFansEducationLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-education-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fans-education-levels/:id : get the "id" personFansEducationLevel.
     *
     * @param id the id of the personFansEducationLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFansEducationLevel, or with status 404 (Not Found)
     */
    @GetMapping("/person-fans-education-levels/{id}")
    @Timed
    public ResponseEntity<PersonFansEducationLevel> getPersonFansEducationLevel(@PathVariable Long id) {
        log.debug("REST request to get PersonFansEducationLevel : {}", id);
        PersonFansEducationLevel personFansEducationLevel = personFansEducationLevelService.findOne(id);
        return Optional.ofNullable(personFansEducationLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fans-education-levels/:id : delete the "id" personFansEducationLevel.
     *
     * @param id the id of the personFansEducationLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fans-education-levels/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFansEducationLevel(@PathVariable Long id) {
        log.debug("REST request to delete PersonFansEducationLevel : {}", id);
        personFansEducationLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFansEducationLevel", id.toString())).build();
    }

}
