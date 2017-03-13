package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonIncome;
import com.intelligent.chart.repository.PersonIncomeRepository;
import com.intelligent.chart.service.PersonIncomeService;
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
 * REST controller for managing PersonIncome.
 */
@RestController
@RequestMapping("/api")
public class PersonIncomeResource {

    private final Logger log = LoggerFactory.getLogger(PersonIncomeResource.class);

    @Inject
    private PersonIncomeService personIncomeService;

    @Inject
    private PersonIncomeRepository personIncomeRepository;

    @GetMapping("/person-incomes/person/{id}")
    @Timed
    public List<PersonIncome> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id)
        throws URISyntaxException {

        List<PersonIncome> page = personIncomeRepository.findByPerson_Id(id);

        return page;
    }

    /**
     * POST  /person-incomes : Create a new personIncome.
     *
     * @param personIncome the personIncome to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personIncome, or with status 400 (Bad Request) if the personIncome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-incomes")
    @Timed
    public ResponseEntity<PersonIncome> createPersonIncome(@RequestBody PersonIncome personIncome) throws URISyntaxException {
        log.debug("REST request to save PersonIncome : {}", personIncome);
        if (personIncome.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personIncome", "idexists", "A new personIncome cannot already have an ID")).body(null);
        }
        PersonIncome result = personIncomeService.save(personIncome);
        return ResponseEntity.created(new URI("/api/person-incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personIncome", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-incomes : Updates an existing personIncome.
     *
     * @param personIncome the personIncome to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personIncome,
     * or with status 400 (Bad Request) if the personIncome is not valid,
     * or with status 500 (Internal Server Error) if the personIncome couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-incomes")
    @Timed
    public ResponseEntity<PersonIncome> updatePersonIncome(@RequestBody PersonIncome personIncome) throws URISyntaxException {
        log.debug("REST request to update PersonIncome : {}", personIncome);
        if (personIncome.getId() == null) {
            return createPersonIncome(personIncome);
        }
        PersonIncome result = personIncomeService.save(personIncome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personIncome", personIncome.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-incomes : get all the personIncomes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personIncomes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-incomes")
    @Timed
    public ResponseEntity<List<PersonIncome>> getAllPersonIncomes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonIncomes");
        Page<PersonIncome> page = personIncomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-incomes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-incomes/:id : get the "id" personIncome.
     *
     * @param id the id of the personIncome to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personIncome, or with status 404 (Not Found)
     */
    @GetMapping("/person-incomes/{id}")
    @Timed
    public ResponseEntity<PersonIncome> getPersonIncome(@PathVariable Long id) {
        log.debug("REST request to get PersonIncome : {}", id);
        PersonIncome personIncome = personIncomeService.findOne(id);
        return Optional.ofNullable(personIncome)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-incomes/:id : delete the "id" personIncome.
     *
     * @param id the id of the personIncome to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-incomes/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonIncome(@PathVariable Long id) {
        log.debug("REST request to delete PersonIncome : {}", id);
        personIncomeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personIncome", id.toString())).build();
    }

}
