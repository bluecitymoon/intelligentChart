package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonCreditCardActivity;
import com.intelligent.chart.repository.PersonCreditCardActivityRepository;
import com.intelligent.chart.service.PersonCreditCardActivityService;
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
 * REST controller for managing PersonCreditCardActivity.
 */
@RestController
@RequestMapping("/api")
public class PersonCreditCardActivityResource {

    private final Logger log = LoggerFactory.getLogger(PersonCreditCardActivityResource.class);

    @Inject
    private PersonCreditCardActivityService personCreditCardActivityService;

    @Inject
    private PersonCreditCardActivityRepository personCreditCardActivityRepository;

    @GetMapping("/person-credit-card-activities/person/{id}")
    @Timed
    public ResponseEntity<List<PersonCreditCardActivity>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonCreditCardActivity> page = personCreditCardActivityRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-credit-card-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-credit-card-activities : Create a new personCreditCardActivity.
     *
     * @param personCreditCardActivity the personCreditCardActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personCreditCardActivity, or with status 400 (Bad Request) if the personCreditCardActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-credit-card-activities")
    @Timed
    public ResponseEntity<PersonCreditCardActivity> createPersonCreditCardActivity(@RequestBody PersonCreditCardActivity personCreditCardActivity) throws URISyntaxException {
        log.debug("REST request to save PersonCreditCardActivity : {}", personCreditCardActivity);
        if (personCreditCardActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personCreditCardActivity", "idexists", "A new personCreditCardActivity cannot already have an ID")).body(null);
        }
        PersonCreditCardActivity result = personCreditCardActivityService.save(personCreditCardActivity);
        return ResponseEntity.created(new URI("/api/person-credit-card-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personCreditCardActivity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-credit-card-activities : Updates an existing personCreditCardActivity.
     *
     * @param personCreditCardActivity the personCreditCardActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personCreditCardActivity,
     * or with status 400 (Bad Request) if the personCreditCardActivity is not valid,
     * or with status 500 (Internal Server Error) if the personCreditCardActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-credit-card-activities")
    @Timed
    public ResponseEntity<PersonCreditCardActivity> updatePersonCreditCardActivity(@RequestBody PersonCreditCardActivity personCreditCardActivity) throws URISyntaxException {
        log.debug("REST request to update PersonCreditCardActivity : {}", personCreditCardActivity);
        if (personCreditCardActivity.getId() == null) {
            return createPersonCreditCardActivity(personCreditCardActivity);
        }
        PersonCreditCardActivity result = personCreditCardActivityService.save(personCreditCardActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personCreditCardActivity", personCreditCardActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-credit-card-activities : get all the personCreditCardActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personCreditCardActivities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-credit-card-activities")
    @Timed
    public ResponseEntity<List<PersonCreditCardActivity>> getAllPersonCreditCardActivities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonCreditCardActivities");
        Page<PersonCreditCardActivity> page = personCreditCardActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-credit-card-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-credit-card-activities/:id : get the "id" personCreditCardActivity.
     *
     * @param id the id of the personCreditCardActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personCreditCardActivity, or with status 404 (Not Found)
     */
    @GetMapping("/person-credit-card-activities/{id}")
    @Timed
    public ResponseEntity<PersonCreditCardActivity> getPersonCreditCardActivity(@PathVariable Long id) {
        log.debug("REST request to get PersonCreditCardActivity : {}", id);
        PersonCreditCardActivity personCreditCardActivity = personCreditCardActivityService.findOne(id);
        return Optional.ofNullable(personCreditCardActivity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-credit-card-activities/:id : delete the "id" personCreditCardActivity.
     *
     * @param id the id of the personCreditCardActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-credit-card-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonCreditCardActivity(@PathVariable Long id) {
        log.debug("REST request to delete PersonCreditCardActivity : {}", id);
        personCreditCardActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personCreditCardActivity", id.toString())).build();
    }

}
