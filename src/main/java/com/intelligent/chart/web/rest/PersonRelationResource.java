package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonRelation;
import com.intelligent.chart.service.PersonRelationService;
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
 * REST controller for managing PersonRelation.
 */
@RestController
@RequestMapping("/api")
public class PersonRelationResource {

    private final Logger log = LoggerFactory.getLogger(PersonRelationResource.class);
        
    @Inject
    private PersonRelationService personRelationService;

    /**
     * POST  /person-relations : Create a new personRelation.
     *
     * @param personRelation the personRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personRelation, or with status 400 (Bad Request) if the personRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-relations")
    @Timed
    public ResponseEntity<PersonRelation> createPersonRelation(@RequestBody PersonRelation personRelation) throws URISyntaxException {
        log.debug("REST request to save PersonRelation : {}", personRelation);
        if (personRelation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personRelation", "idexists", "A new personRelation cannot already have an ID")).body(null);
        }
        PersonRelation result = personRelationService.save(personRelation);
        return ResponseEntity.created(new URI("/api/person-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personRelation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-relations : Updates an existing personRelation.
     *
     * @param personRelation the personRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personRelation,
     * or with status 400 (Bad Request) if the personRelation is not valid,
     * or with status 500 (Internal Server Error) if the personRelation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-relations")
    @Timed
    public ResponseEntity<PersonRelation> updatePersonRelation(@RequestBody PersonRelation personRelation) throws URISyntaxException {
        log.debug("REST request to update PersonRelation : {}", personRelation);
        if (personRelation.getId() == null) {
            return createPersonRelation(personRelation);
        }
        PersonRelation result = personRelationService.save(personRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personRelation", personRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-relations : get all the personRelations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personRelations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-relations")
    @Timed
    public ResponseEntity<List<PersonRelation>> getAllPersonRelations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonRelations");
        Page<PersonRelation> page = personRelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-relations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-relations/:id : get the "id" personRelation.
     *
     * @param id the id of the personRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personRelation, or with status 404 (Not Found)
     */
    @GetMapping("/person-relations/{id}")
    @Timed
    public ResponseEntity<PersonRelation> getPersonRelation(@PathVariable Long id) {
        log.debug("REST request to get PersonRelation : {}", id);
        PersonRelation personRelation = personRelationService.findOne(id);
        return Optional.ofNullable(personRelation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-relations/:id : delete the "id" personRelation.
     *
     * @param id the id of the personRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-relations/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonRelation(@PathVariable Long id) {
        log.debug("REST request to delete PersonRelation : {}", id);
        personRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personRelation", id.toString())).build();
    }

}
