package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonInnovation;
import com.intelligent.chart.service.PersonInnovationService;
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
 * REST controller for managing PersonInnovation.
 */
@RestController
@RequestMapping("/api")
public class PersonInnovationResource {

    private final Logger log = LoggerFactory.getLogger(PersonInnovationResource.class);
        
    @Inject
    private PersonInnovationService personInnovationService;

    /**
     * POST  /person-innovations : Create a new personInnovation.
     *
     * @param personInnovation the personInnovation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personInnovation, or with status 400 (Bad Request) if the personInnovation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-innovations")
    @Timed
    public ResponseEntity<PersonInnovation> createPersonInnovation(@RequestBody PersonInnovation personInnovation) throws URISyntaxException {
        log.debug("REST request to save PersonInnovation : {}", personInnovation);
        if (personInnovation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personInnovation", "idexists", "A new personInnovation cannot already have an ID")).body(null);
        }
        PersonInnovation result = personInnovationService.save(personInnovation);
        return ResponseEntity.created(new URI("/api/person-innovations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personInnovation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-innovations : Updates an existing personInnovation.
     *
     * @param personInnovation the personInnovation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personInnovation,
     * or with status 400 (Bad Request) if the personInnovation is not valid,
     * or with status 500 (Internal Server Error) if the personInnovation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-innovations")
    @Timed
    public ResponseEntity<PersonInnovation> updatePersonInnovation(@RequestBody PersonInnovation personInnovation) throws URISyntaxException {
        log.debug("REST request to update PersonInnovation : {}", personInnovation);
        if (personInnovation.getId() == null) {
            return createPersonInnovation(personInnovation);
        }
        PersonInnovation result = personInnovationService.save(personInnovation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personInnovation", personInnovation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-innovations : get all the personInnovations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personInnovations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-innovations")
    @Timed
    public ResponseEntity<List<PersonInnovation>> getAllPersonInnovations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonInnovations");
        Page<PersonInnovation> page = personInnovationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-innovations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-innovations/:id : get the "id" personInnovation.
     *
     * @param id the id of the personInnovation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personInnovation, or with status 404 (Not Found)
     */
    @GetMapping("/person-innovations/{id}")
    @Timed
    public ResponseEntity<PersonInnovation> getPersonInnovation(@PathVariable Long id) {
        log.debug("REST request to get PersonInnovation : {}", id);
        PersonInnovation personInnovation = personInnovationService.findOne(id);
        return Optional.ofNullable(personInnovation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-innovations/:id : delete the "id" personInnovation.
     *
     * @param id the id of the personInnovation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-innovations/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonInnovation(@PathVariable Long id) {
        log.debug("REST request to delete PersonInnovation : {}", id);
        personInnovationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personInnovation", id.toString())).build();
    }

}
