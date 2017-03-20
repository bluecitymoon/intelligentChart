package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonNetworkDebit;
import com.intelligent.chart.domain.PersonPaidNetworkDebit;
import com.intelligent.chart.repository.PersonNetworkDebitRepository;
import com.intelligent.chart.repository.PersonPaidNetworkDebitRepository;
import com.intelligent.chart.service.PersonPaidNetworkDebitService;
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
 * REST controller for managing PersonPaidNetworkDebit.
 */
@RestController
@RequestMapping("/api")
public class PersonPaidNetworkDebitResource {

    private final Logger log = LoggerFactory.getLogger(PersonPaidNetworkDebitResource.class);

    @Inject
    private PersonPaidNetworkDebitService personPaidNetworkDebitService;

    @Inject
    private PersonPaidNetworkDebitRepository personPaidNetworkDebitRepository;

    @GetMapping("/person-paid-network-debits/person/{id}")
    @Timed
    public ResponseEntity<List<PersonPaidNetworkDebit>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonPaidNetworkDebit> page = personPaidNetworkDebitRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-paid-network-debits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * POST  /person-paid-network-debits : Create a new personPaidNetworkDebit.
     *
     * @param personPaidNetworkDebit the personPaidNetworkDebit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPaidNetworkDebit, or with status 400 (Bad Request) if the personPaidNetworkDebit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-paid-network-debits")
    @Timed
    public ResponseEntity<PersonPaidNetworkDebit> createPersonPaidNetworkDebit(@RequestBody PersonPaidNetworkDebit personPaidNetworkDebit) throws URISyntaxException {
        log.debug("REST request to save PersonPaidNetworkDebit : {}", personPaidNetworkDebit);
        if (personPaidNetworkDebit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personPaidNetworkDebit", "idexists", "A new personPaidNetworkDebit cannot already have an ID")).body(null);
        }
        PersonPaidNetworkDebit result = personPaidNetworkDebitService.save(personPaidNetworkDebit);
        return ResponseEntity.created(new URI("/api/person-paid-network-debits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personPaidNetworkDebit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-paid-network-debits : Updates an existing personPaidNetworkDebit.
     *
     * @param personPaidNetworkDebit the personPaidNetworkDebit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPaidNetworkDebit,
     * or with status 400 (Bad Request) if the personPaidNetworkDebit is not valid,
     * or with status 500 (Internal Server Error) if the personPaidNetworkDebit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-paid-network-debits")
    @Timed
    public ResponseEntity<PersonPaidNetworkDebit> updatePersonPaidNetworkDebit(@RequestBody PersonPaidNetworkDebit personPaidNetworkDebit) throws URISyntaxException {
        log.debug("REST request to update PersonPaidNetworkDebit : {}", personPaidNetworkDebit);
        if (personPaidNetworkDebit.getId() == null) {
            return createPersonPaidNetworkDebit(personPaidNetworkDebit);
        }
        PersonPaidNetworkDebit result = personPaidNetworkDebitService.save(personPaidNetworkDebit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personPaidNetworkDebit", personPaidNetworkDebit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-paid-network-debits : get all the personPaidNetworkDebits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personPaidNetworkDebits in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-paid-network-debits")
    @Timed
    public ResponseEntity<List<PersonPaidNetworkDebit>> getAllPersonPaidNetworkDebits(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonPaidNetworkDebits");
        Page<PersonPaidNetworkDebit> page = personPaidNetworkDebitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-paid-network-debits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-paid-network-debits/:id : get the "id" personPaidNetworkDebit.
     *
     * @param id the id of the personPaidNetworkDebit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPaidNetworkDebit, or with status 404 (Not Found)
     */
    @GetMapping("/person-paid-network-debits/{id}")
    @Timed
    public ResponseEntity<PersonPaidNetworkDebit> getPersonPaidNetworkDebit(@PathVariable Long id) {
        log.debug("REST request to get PersonPaidNetworkDebit : {}", id);
        PersonPaidNetworkDebit personPaidNetworkDebit = personPaidNetworkDebitService.findOne(id);
        return Optional.ofNullable(personPaidNetworkDebit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-paid-network-debits/:id : delete the "id" personPaidNetworkDebit.
     *
     * @param id the id of the personPaidNetworkDebit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-paid-network-debits/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonPaidNetworkDebit(@PathVariable Long id) {
        log.debug("REST request to delete PersonPaidNetworkDebit : {}", id);
        personPaidNetworkDebitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personPaidNetworkDebit", id.toString())).build();
    }

}
