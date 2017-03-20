package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonEndorsement;
import com.intelligent.chart.repository.PersonEndorsementRepository;
import com.intelligent.chart.service.PersonEndorsementService;
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
 * REST controller for managing PersonEndorsement.
 */
@RestController
@RequestMapping("/api")
public class PersonEndorsementResource {

    private final Logger log = LoggerFactory.getLogger(PersonEndorsementResource.class);

    @Inject
    private PersonEndorsementService personEndorsementService;

    @Inject
    private PersonEndorsementRepository personEndorsementRepository;


    @GetMapping("/person-endorsements/person/{id}")
    @Timed
    public ResponseEntity<List<PersonEndorsement>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonEndorsement> page = personEndorsementRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-endorsements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-endorsements : Create a new personEndorsement.
     *
     * @param personEndorsement the personEndorsement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personEndorsement, or with status 400 (Bad Request) if the personEndorsement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-endorsements")
    @Timed
    public ResponseEntity<PersonEndorsement> createPersonEndorsement(@RequestBody PersonEndorsement personEndorsement) throws URISyntaxException {
        log.debug("REST request to save PersonEndorsement : {}", personEndorsement);
        if (personEndorsement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personEndorsement", "idexists", "A new personEndorsement cannot already have an ID")).body(null);
        }
        PersonEndorsement result = personEndorsementService.save(personEndorsement);
        return ResponseEntity.created(new URI("/api/person-endorsements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personEndorsement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-endorsements : Updates an existing personEndorsement.
     *
     * @param personEndorsement the personEndorsement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personEndorsement,
     * or with status 400 (Bad Request) if the personEndorsement is not valid,
     * or with status 500 (Internal Server Error) if the personEndorsement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-endorsements")
    @Timed
    public ResponseEntity<PersonEndorsement> updatePersonEndorsement(@RequestBody PersonEndorsement personEndorsement) throws URISyntaxException {
        log.debug("REST request to update PersonEndorsement : {}", personEndorsement);
        if (personEndorsement.getId() == null) {
            return createPersonEndorsement(personEndorsement);
        }
        PersonEndorsement result = personEndorsementService.save(personEndorsement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personEndorsement", personEndorsement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-endorsements : get all the personEndorsements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personEndorsements in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-endorsements")
    @Timed
    public ResponseEntity<List<PersonEndorsement>> getAllPersonEndorsements(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonEndorsements");
        Page<PersonEndorsement> page = personEndorsementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-endorsements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-endorsements/:id : get the "id" personEndorsement.
     *
     * @param id the id of the personEndorsement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personEndorsement, or with status 404 (Not Found)
     */
    @GetMapping("/person-endorsements/{id}")
    @Timed
    public ResponseEntity<PersonEndorsement> getPersonEndorsement(@PathVariable Long id) {
        log.debug("REST request to get PersonEndorsement : {}", id);
        PersonEndorsement personEndorsement = personEndorsementService.findOne(id);
        return Optional.ofNullable(personEndorsement)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-endorsements/:id : delete the "id" personEndorsement.
     *
     * @param id the id of the personEndorsement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-endorsements/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonEndorsement(@PathVariable Long id) {
        log.debug("REST request to delete PersonEndorsement : {}", id);
        personEndorsementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personEndorsement", id.toString())).build();
    }

}
