package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonNetworkDebit;
import com.intelligent.chart.domain.PersonNetworkTexiActivity;
import com.intelligent.chart.repository.PersonNetworkTexiActivityRepository;
import com.intelligent.chart.service.PersonNetworkTexiActivityService;
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
 * REST controller for managing PersonNetworkTexiActivity.
 */
@RestController
@RequestMapping("/api")
public class PersonNetworkTexiActivityResource {

    private final Logger log = LoggerFactory.getLogger(PersonNetworkTexiActivityResource.class);

    @Inject
    private PersonNetworkTexiActivityService personNetworkTexiActivityService;

    @Inject
    private PersonNetworkTexiActivityRepository personNetworkTexiActivityRepository;

    @GetMapping("/person-network-texi-activities/person/{id}")
    @Timed
    public ResponseEntity<List<PersonNetworkTexiActivity>> getAllPersonNetworkShoppingByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonNetworkTexiActivity> page = personNetworkTexiActivityRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-network-texi-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * POST  /person-network-texi-activities : Create a new personNetworkTexiActivity.
     *
     * @param personNetworkTexiActivity the personNetworkTexiActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personNetworkTexiActivity, or with status 400 (Bad Request) if the personNetworkTexiActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-network-texi-activities")
    @Timed
    public ResponseEntity<PersonNetworkTexiActivity> createPersonNetworkTexiActivity(@RequestBody PersonNetworkTexiActivity personNetworkTexiActivity) throws URISyntaxException {
        log.debug("REST request to save PersonNetworkTexiActivity : {}", personNetworkTexiActivity);
        if (personNetworkTexiActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personNetworkTexiActivity", "idexists", "A new personNetworkTexiActivity cannot already have an ID")).body(null);
        }
        PersonNetworkTexiActivity result = personNetworkTexiActivityService.save(personNetworkTexiActivity);
        return ResponseEntity.created(new URI("/api/person-network-texi-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personNetworkTexiActivity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-network-texi-activities : Updates an existing personNetworkTexiActivity.
     *
     * @param personNetworkTexiActivity the personNetworkTexiActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personNetworkTexiActivity,
     * or with status 400 (Bad Request) if the personNetworkTexiActivity is not valid,
     * or with status 500 (Internal Server Error) if the personNetworkTexiActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-network-texi-activities")
    @Timed
    public ResponseEntity<PersonNetworkTexiActivity> updatePersonNetworkTexiActivity(@RequestBody PersonNetworkTexiActivity personNetworkTexiActivity) throws URISyntaxException {
        log.debug("REST request to update PersonNetworkTexiActivity : {}", personNetworkTexiActivity);
        if (personNetworkTexiActivity.getId() == null) {
            return createPersonNetworkTexiActivity(personNetworkTexiActivity);
        }
        PersonNetworkTexiActivity result = personNetworkTexiActivityService.save(personNetworkTexiActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personNetworkTexiActivity", personNetworkTexiActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-network-texi-activities : get all the personNetworkTexiActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personNetworkTexiActivities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-network-texi-activities")
    @Timed
    public ResponseEntity<List<PersonNetworkTexiActivity>> getAllPersonNetworkTexiActivities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonNetworkTexiActivities");
        Page<PersonNetworkTexiActivity> page = personNetworkTexiActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-network-texi-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-network-texi-activities/:id : get the "id" personNetworkTexiActivity.
     *
     * @param id the id of the personNetworkTexiActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personNetworkTexiActivity, or with status 404 (Not Found)
     */
    @GetMapping("/person-network-texi-activities/{id}")
    @Timed
    public ResponseEntity<PersonNetworkTexiActivity> getPersonNetworkTexiActivity(@PathVariable Long id) {
        log.debug("REST request to get PersonNetworkTexiActivity : {}", id);
        PersonNetworkTexiActivity personNetworkTexiActivity = personNetworkTexiActivityService.findOne(id);
        return Optional.ofNullable(personNetworkTexiActivity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-network-texi-activities/:id : delete the "id" personNetworkTexiActivity.
     *
     * @param id the id of the personNetworkTexiActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-network-texi-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonNetworkTexiActivity(@PathVariable Long id) {
        log.debug("REST request to delete PersonNetworkTexiActivity : {}", id);
        personNetworkTexiActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personNetworkTexiActivity", id.toString())).build();
    }

}
