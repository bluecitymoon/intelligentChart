package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFanPaymentTool;
import com.intelligent.chart.service.PersonFanPaymentToolService;import com.intelligent.chart.repository.PersonFanPaymentToolRepository;
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
 * REST controller for managing PersonFanPaymentTool.
 */
@RestController
@RequestMapping("/api")
public class PersonFanPaymentToolResource {

    private final Logger log = LoggerFactory.getLogger(PersonFanPaymentToolResource.class);
        
    @Inject
    private PersonFanPaymentToolService personFanPaymentToolService;

    @Inject
    private PersonFanPaymentToolRepository personFanPaymentToolRepository;


    @GetMapping("/person-fan-payment-tools/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFanPaymentTool>> getAllPersonPersonFanPaymentToolByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFanPaymentTool> page = personFanPaymentToolRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-payment-tools");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fan-payment-tools : Create a new personFanPaymentTool.
     *
     * @param personFanPaymentTool the personFanPaymentTool to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFanPaymentTool, or with status 400 (Bad Request) if the personFanPaymentTool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fan-payment-tools")
    @Timed
    public ResponseEntity<PersonFanPaymentTool> createPersonFanPaymentTool(@RequestBody PersonFanPaymentTool personFanPaymentTool) throws URISyntaxException {
        log.debug("REST request to save PersonFanPaymentTool : {}", personFanPaymentTool);
        if (personFanPaymentTool.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFanPaymentTool", "idexists", "A new personFanPaymentTool cannot already have an ID")).body(null);
        }
        PersonFanPaymentTool result = personFanPaymentToolService.save(personFanPaymentTool);
        return ResponseEntity.created(new URI("/api/person-fan-payment-tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFanPaymentTool", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fan-payment-tools : Updates an existing personFanPaymentTool.
     *
     * @param personFanPaymentTool the personFanPaymentTool to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFanPaymentTool,
     * or with status 400 (Bad Request) if the personFanPaymentTool is not valid,
     * or with status 500 (Internal Server Error) if the personFanPaymentTool couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fan-payment-tools")
    @Timed
    public ResponseEntity<PersonFanPaymentTool> updatePersonFanPaymentTool(@RequestBody PersonFanPaymentTool personFanPaymentTool) throws URISyntaxException {
        log.debug("REST request to update PersonFanPaymentTool : {}", personFanPaymentTool);
        if (personFanPaymentTool.getId() == null) {
            return createPersonFanPaymentTool(personFanPaymentTool);
        }
        PersonFanPaymentTool result = personFanPaymentToolService.save(personFanPaymentTool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFanPaymentTool", personFanPaymentTool.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fan-payment-tools : get all the personFanPaymentTools.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFanPaymentTools in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fan-payment-tools")
    @Timed
    public ResponseEntity<List<PersonFanPaymentTool>> getAllPersonFanPaymentTools(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFanPaymentTools");
        Page<PersonFanPaymentTool> page = personFanPaymentToolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-payment-tools");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fan-payment-tools/:id : get the "id" personFanPaymentTool.
     *
     * @param id the id of the personFanPaymentTool to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFanPaymentTool, or with status 404 (Not Found)
     */
    @GetMapping("/person-fan-payment-tools/{id}")
    @Timed
    public ResponseEntity<PersonFanPaymentTool> getPersonFanPaymentTool(@PathVariable Long id) {
        log.debug("REST request to get PersonFanPaymentTool : {}", id);
        PersonFanPaymentTool personFanPaymentTool = personFanPaymentToolService.findOne(id);
        return Optional.ofNullable(personFanPaymentTool)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fan-payment-tools/:id : delete the "id" personFanPaymentTool.
     *
     * @param id the id of the personFanPaymentTool to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fan-payment-tools/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFanPaymentTool(@PathVariable Long id) {
        log.debug("REST request to delete PersonFanPaymentTool : {}", id);
        personFanPaymentToolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFanPaymentTool", id.toString())).build();
    }

}
