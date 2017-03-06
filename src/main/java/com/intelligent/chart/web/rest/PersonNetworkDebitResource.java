package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonNetworkDebit;
import com.intelligent.chart.repository.PersonNetworkDebitRepository;
import com.intelligent.chart.service.PersonNetworkDebitService;
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
 * REST controller for managing PersonNetworkDebit.
 */
@RestController
@RequestMapping("/api")
public class PersonNetworkDebitResource {

    private final Logger log = LoggerFactory.getLogger(PersonNetworkDebitResource.class);

    @Inject
    private PersonNetworkDebitService personNetworkDebitService;

    @Inject
    private PersonNetworkDebitRepository personNetworkDebitRepository;

    @GetMapping("/person-network-debits/person/{id}")
    @Timed
    public ResponseEntity<List<PersonNetworkDebit>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonNetworkDebit> page = personNetworkDebitRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-network-debits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-network-debits : Create a new personNetworkDebit.
     *
     * @param personNetworkDebit the personNetworkDebit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personNetworkDebit, or with status 400 (Bad Request) if the personNetworkDebit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-network-debits")
    @Timed
    public ResponseEntity<PersonNetworkDebit> createPersonNetworkDebit(@RequestBody PersonNetworkDebit personNetworkDebit) throws URISyntaxException {
        log.debug("REST request to save PersonNetworkDebit : {}", personNetworkDebit);
        if (personNetworkDebit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personNetworkDebit", "idexists", "A new personNetworkDebit cannot already have an ID")).body(null);
        }
        PersonNetworkDebit result = personNetworkDebitService.save(personNetworkDebit);
        return ResponseEntity.created(new URI("/api/person-network-debits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personNetworkDebit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-network-debits : Updates an existing personNetworkDebit.
     *
     * @param personNetworkDebit the personNetworkDebit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personNetworkDebit,
     * or with status 400 (Bad Request) if the personNetworkDebit is not valid,
     * or with status 500 (Internal Server Error) if the personNetworkDebit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-network-debits")
    @Timed
    public ResponseEntity<PersonNetworkDebit> updatePersonNetworkDebit(@RequestBody PersonNetworkDebit personNetworkDebit) throws URISyntaxException {
        log.debug("REST request to update PersonNetworkDebit : {}", personNetworkDebit);
        if (personNetworkDebit.getId() == null) {
            return createPersonNetworkDebit(personNetworkDebit);
        }
        PersonNetworkDebit result = personNetworkDebitService.save(personNetworkDebit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personNetworkDebit", personNetworkDebit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-network-debits : get all the personNetworkDebits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personNetworkDebits in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-network-debits")
    @Timed
    public ResponseEntity<List<PersonNetworkDebit>> getAllPersonNetworkDebits(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonNetworkDebits");
        Page<PersonNetworkDebit> page = personNetworkDebitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-network-debits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-network-debits/:id : get the "id" personNetworkDebit.
     *
     * @param id the id of the personNetworkDebit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personNetworkDebit, or with status 404 (Not Found)
     */
    @GetMapping("/person-network-debits/{id}")
    @Timed
    public ResponseEntity<PersonNetworkDebit> getPersonNetworkDebit(@PathVariable Long id) {
        log.debug("REST request to get PersonNetworkDebit : {}", id);
        PersonNetworkDebit personNetworkDebit = personNetworkDebitService.findOne(id);
        return Optional.ofNullable(personNetworkDebit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-network-debits/:id : delete the "id" personNetworkDebit.
     *
     * @param id the id of the personNetworkDebit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-network-debits/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonNetworkDebit(@PathVariable Long id) {
        log.debug("REST request to delete PersonNetworkDebit : {}", id);
        personNetworkDebitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personNetworkDebit", id.toString())).build();
    }

}
