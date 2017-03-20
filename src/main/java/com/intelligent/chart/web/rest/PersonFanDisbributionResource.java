package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFanDisbribution;
import com.intelligent.chart.repository.PersonFanDisbributionRepository;
import com.intelligent.chart.service.PersonFanDisbributionService;
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
 * REST controller for managing PersonFanDisbribution.
 */
@RestController
@RequestMapping("/api")
public class PersonFanDisbributionResource {

    private final Logger log = LoggerFactory.getLogger(PersonFanDisbributionResource.class);

    @Inject
    private PersonFanDisbributionService personFanDisbributionService;

    @Inject
    private PersonFanDisbributionRepository personFanDisbributionRepository;

    @GetMapping("/person-fan-disbributions/person/{id}")
    @Timed
    public List<PersonFanDisbribution> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id)
        throws URISyntaxException {

        List<PersonFanDisbribution> page = personFanDisbributionRepository.findByPerson_Id(id);

        return page;
    }

    /**
     * POST  /person-fan-disbributions : Create a new personFanDisbribution.
     *
     * @param personFanDisbribution the personFanDisbribution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFanDisbribution, or with status 400 (Bad Request) if the personFanDisbribution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fan-disbributions")
    @Timed
    public ResponseEntity<PersonFanDisbribution> createPersonFanDisbribution(@RequestBody PersonFanDisbribution personFanDisbribution) throws URISyntaxException {
        log.debug("REST request to save PersonFanDisbribution : {}", personFanDisbribution);
        if (personFanDisbribution.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFanDisbribution", "idexists", "A new personFanDisbribution cannot already have an ID")).body(null);
        }
        PersonFanDisbribution result = personFanDisbributionService.save(personFanDisbribution);
        return ResponseEntity.created(new URI("/api/person-fan-disbributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFanDisbribution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fan-disbributions : Updates an existing personFanDisbribution.
     *
     * @param personFanDisbribution the personFanDisbribution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFanDisbribution,
     * or with status 400 (Bad Request) if the personFanDisbribution is not valid,
     * or with status 500 (Internal Server Error) if the personFanDisbribution couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fan-disbributions")
    @Timed
    public ResponseEntity<PersonFanDisbribution> updatePersonFanDisbribution(@RequestBody PersonFanDisbribution personFanDisbribution) throws URISyntaxException {
        log.debug("REST request to update PersonFanDisbribution : {}", personFanDisbribution);
        if (personFanDisbribution.getId() == null) {
            return createPersonFanDisbribution(personFanDisbribution);
        }
        PersonFanDisbribution result = personFanDisbributionService.save(personFanDisbribution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFanDisbribution", personFanDisbribution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fan-disbributions : get all the personFanDisbributions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFanDisbributions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fan-disbributions")
    @Timed
    public ResponseEntity<List<PersonFanDisbribution>> getAllPersonFanDisbributions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFanDisbributions");
        Page<PersonFanDisbribution> page = personFanDisbributionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-disbributions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fan-disbributions/:id : get the "id" personFanDisbribution.
     *
     * @param id the id of the personFanDisbribution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFanDisbribution, or with status 404 (Not Found)
     */
    @GetMapping("/person-fan-disbributions/{id}")
    @Timed
    public ResponseEntity<PersonFanDisbribution> getPersonFanDisbribution(@PathVariable Long id) {
        log.debug("REST request to get PersonFanDisbribution : {}", id);
        PersonFanDisbribution personFanDisbribution = personFanDisbributionService.findOne(id);
        return Optional.ofNullable(personFanDisbribution)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fan-disbributions/:id : delete the "id" personFanDisbribution.
     *
     * @param id the id of the personFanDisbribution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fan-disbributions/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFanDisbribution(@PathVariable Long id) {
        log.debug("REST request to delete PersonFanDisbribution : {}", id);
        personFanDisbributionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFanDisbribution", id.toString())).build();
    }

}
