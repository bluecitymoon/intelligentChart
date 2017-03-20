package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonTaxiActivity;
import com.intelligent.chart.repository.PersonTaxiActivityRepository;
import com.intelligent.chart.service.PersonTaxiActivityService;
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
 * REST controller for managing PersonTaxiActivity.
 */
@RestController
@RequestMapping("/api")
public class PersonTaxiActivityResource {

    private final Logger log = LoggerFactory.getLogger(PersonTaxiActivityResource.class);

    @Inject
    private PersonTaxiActivityService personTaxiActivityService;

    @Inject
    private PersonTaxiActivityRepository personTaxiActivityRepository;

    @GetMapping("/person-taxi-activities/person/{id}")
    @Timed
    public ResponseEntity<List<PersonTaxiActivity>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonTaxiActivity> page = personTaxiActivityRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-taxi-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * POST  /person-taxi-activities : Create a new personTaxiActivity.
     *
     * @param personTaxiActivity the personTaxiActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personTaxiActivity, or with status 400 (Bad Request) if the personTaxiActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-taxi-activities")
    @Timed
    public ResponseEntity<PersonTaxiActivity> createPersonTaxiActivity(@RequestBody PersonTaxiActivity personTaxiActivity) throws URISyntaxException {
        log.debug("REST request to save PersonTaxiActivity : {}", personTaxiActivity);
        if (personTaxiActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personTaxiActivity", "idexists", "A new personTaxiActivity cannot already have an ID")).body(null);
        }
        PersonTaxiActivity result = personTaxiActivityService.save(personTaxiActivity);
        return ResponseEntity.created(new URI("/api/person-taxi-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personTaxiActivity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-taxi-activities : Updates an existing personTaxiActivity.
     *
     * @param personTaxiActivity the personTaxiActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personTaxiActivity,
     * or with status 400 (Bad Request) if the personTaxiActivity is not valid,
     * or with status 500 (Internal Server Error) if the personTaxiActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-taxi-activities")
    @Timed
    public ResponseEntity<PersonTaxiActivity> updatePersonTaxiActivity(@RequestBody PersonTaxiActivity personTaxiActivity) throws URISyntaxException {
        log.debug("REST request to update PersonTaxiActivity : {}", personTaxiActivity);
        if (personTaxiActivity.getId() == null) {
            return createPersonTaxiActivity(personTaxiActivity);
        }
        PersonTaxiActivity result = personTaxiActivityService.save(personTaxiActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personTaxiActivity", personTaxiActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-taxi-activities : get all the personTaxiActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personTaxiActivities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-taxi-activities")
    @Timed
    public ResponseEntity<List<PersonTaxiActivity>> getAllPersonTaxiActivities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonTaxiActivities");
        Page<PersonTaxiActivity> page = personTaxiActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-taxi-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-taxi-activities/:id : get the "id" personTaxiActivity.
     *
     * @param id the id of the personTaxiActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personTaxiActivity, or with status 404 (Not Found)
     */
    @GetMapping("/person-taxi-activities/{id}")
    @Timed
    public ResponseEntity<PersonTaxiActivity> getPersonTaxiActivity(@PathVariable Long id) {
        log.debug("REST request to get PersonTaxiActivity : {}", id);
        PersonTaxiActivity personTaxiActivity = personTaxiActivityService.findOne(id);
        return Optional.ofNullable(personTaxiActivity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-taxi-activities/:id : delete the "id" personTaxiActivity.
     *
     * @param id the id of the personTaxiActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-taxi-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonTaxiActivity(@PathVariable Long id) {
        log.debug("REST request to delete PersonTaxiActivity : {}", id);
        personTaxiActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personTaxiActivity", id.toString())).build();
    }

}
