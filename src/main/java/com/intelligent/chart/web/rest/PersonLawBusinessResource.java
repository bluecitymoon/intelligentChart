package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonLawBusiness;
import com.intelligent.chart.domain.PersonWordCloud;
import com.intelligent.chart.repository.PersonLawBusinessRepository;
import com.intelligent.chart.service.PersonLawBusinessService;
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
 * REST controller for managing PersonLawBusiness.
 */
@RestController
@RequestMapping("/api")
public class PersonLawBusinessResource {

    private final Logger log = LoggerFactory.getLogger(PersonLawBusinessResource.class);

    @Inject
    private PersonLawBusinessService personLawBusinessService;

    @Inject
    private PersonLawBusinessRepository personLawBusinessRepository;

    @GetMapping("/person-law-businesses/person/{id}")
    @Timed
    public ResponseEntity<List<PersonLawBusiness>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonLawBusiness> page = personLawBusinessRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-law-businesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-law-businesses : Create a new personLawBusiness.
     *
     * @param personLawBusiness the personLawBusiness to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personLawBusiness, or with status 400 (Bad Request) if the personLawBusiness has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-law-businesses")
    @Timed
    public ResponseEntity<PersonLawBusiness> createPersonLawBusiness(@RequestBody PersonLawBusiness personLawBusiness) throws URISyntaxException {
        log.debug("REST request to save PersonLawBusiness : {}", personLawBusiness);
        if (personLawBusiness.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personLawBusiness", "idexists", "A new personLawBusiness cannot already have an ID")).body(null);
        }
        PersonLawBusiness result = personLawBusinessService.save(personLawBusiness);
        return ResponseEntity.created(new URI("/api/person-law-businesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personLawBusiness", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-law-businesses : Updates an existing personLawBusiness.
     *
     * @param personLawBusiness the personLawBusiness to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personLawBusiness,
     * or with status 400 (Bad Request) if the personLawBusiness is not valid,
     * or with status 500 (Internal Server Error) if the personLawBusiness couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-law-businesses")
    @Timed
    public ResponseEntity<PersonLawBusiness> updatePersonLawBusiness(@RequestBody PersonLawBusiness personLawBusiness) throws URISyntaxException {
        log.debug("REST request to update PersonLawBusiness : {}", personLawBusiness);
        if (personLawBusiness.getId() == null) {
            return createPersonLawBusiness(personLawBusiness);
        }
        PersonLawBusiness result = personLawBusinessService.save(personLawBusiness);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personLawBusiness", personLawBusiness.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-law-businesses : get all the personLawBusinesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personLawBusinesses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-law-businesses")
    @Timed
    public ResponseEntity<List<PersonLawBusiness>> getAllPersonLawBusinesses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonLawBusinesses");
        Page<PersonLawBusiness> page = personLawBusinessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-law-businesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-law-businesses/:id : get the "id" personLawBusiness.
     *
     * @param id the id of the personLawBusiness to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personLawBusiness, or with status 404 (Not Found)
     */
    @GetMapping("/person-law-businesses/{id}")
    @Timed
    public ResponseEntity<PersonLawBusiness> getPersonLawBusiness(@PathVariable Long id) {
        log.debug("REST request to get PersonLawBusiness : {}", id);
        PersonLawBusiness personLawBusiness = personLawBusinessService.findOne(id);
        return Optional.ofNullable(personLawBusiness)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-law-businesses/:id : delete the "id" personLawBusiness.
     *
     * @param id the id of the personLawBusiness to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-law-businesses/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonLawBusiness(@PathVariable Long id) {
        log.debug("REST request to delete PersonLawBusiness : {}", id);
        personLawBusinessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personLawBusiness", id.toString())).build();
    }

}
