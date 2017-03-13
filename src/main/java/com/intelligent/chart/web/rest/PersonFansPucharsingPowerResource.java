package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFansPucharsingPower;
import com.intelligent.chart.service.PersonFansPucharsingPowerService;import com.intelligent.chart.repository.PersonFansPucharsingPowerRepository;
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
 * REST controller for managing PersonFansPucharsingPower.
 */
@RestController
@RequestMapping("/api")
public class PersonFansPucharsingPowerResource {

    private final Logger log = LoggerFactory.getLogger(PersonFansPucharsingPowerResource.class);

    @Inject
    private PersonFansPucharsingPowerService personFansPucharsingPowerService;

    @Inject
    private PersonFansPucharsingPowerRepository personFansPucharsingPowerRepository;


    @GetMapping("/person-fans-pucharsing-powers/person/{id}")
    @Timed
    public List<PersonFansPucharsingPower> getAllPersonPersonFansPucharsingPowerByPersonId(@PathVariable Long id)
    throws URISyntaxException {

        List<PersonFansPucharsingPower> page = personFansPucharsingPowerRepository.findByPerson_Id(id);

        return page;
    }

    /**
     * POST  /person-fans-pucharsing-powers : Create a new personFansPucharsingPower.
     *
     * @param personFansPucharsingPower the personFansPucharsingPower to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFansPucharsingPower, or with status 400 (Bad Request) if the personFansPucharsingPower has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fans-pucharsing-powers")
    @Timed
    public ResponseEntity<PersonFansPucharsingPower> createPersonFansPucharsingPower(@RequestBody PersonFansPucharsingPower personFansPucharsingPower) throws URISyntaxException {
        log.debug("REST request to save PersonFansPucharsingPower : {}", personFansPucharsingPower);
        if (personFansPucharsingPower.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFansPucharsingPower", "idexists", "A new personFansPucharsingPower cannot already have an ID")).body(null);
        }
        PersonFansPucharsingPower result = personFansPucharsingPowerService.save(personFansPucharsingPower);
        return ResponseEntity.created(new URI("/api/person-fans-pucharsing-powers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFansPucharsingPower", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fans-pucharsing-powers : Updates an existing personFansPucharsingPower.
     *
     * @param personFansPucharsingPower the personFansPucharsingPower to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFansPucharsingPower,
     * or with status 400 (Bad Request) if the personFansPucharsingPower is not valid,
     * or with status 500 (Internal Server Error) if the personFansPucharsingPower couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fans-pucharsing-powers")
    @Timed
    public ResponseEntity<PersonFansPucharsingPower> updatePersonFansPucharsingPower(@RequestBody PersonFansPucharsingPower personFansPucharsingPower) throws URISyntaxException {
        log.debug("REST request to update PersonFansPucharsingPower : {}", personFansPucharsingPower);
        if (personFansPucharsingPower.getId() == null) {
            return createPersonFansPucharsingPower(personFansPucharsingPower);
        }
        PersonFansPucharsingPower result = personFansPucharsingPowerService.save(personFansPucharsingPower);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFansPucharsingPower", personFansPucharsingPower.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fans-pucharsing-powers : get all the personFansPucharsingPowers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFansPucharsingPowers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fans-pucharsing-powers")
    @Timed
    public ResponseEntity<List<PersonFansPucharsingPower>> getAllPersonFansPucharsingPowers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFansPucharsingPowers");
        Page<PersonFansPucharsingPower> page = personFansPucharsingPowerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-pucharsing-powers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fans-pucharsing-powers/:id : get the "id" personFansPucharsingPower.
     *
     * @param id the id of the personFansPucharsingPower to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFansPucharsingPower, or with status 404 (Not Found)
     */
    @GetMapping("/person-fans-pucharsing-powers/{id}")
    @Timed
    public ResponseEntity<PersonFansPucharsingPower> getPersonFansPucharsingPower(@PathVariable Long id) {
        log.debug("REST request to get PersonFansPucharsingPower : {}", id);
        PersonFansPucharsingPower personFansPucharsingPower = personFansPucharsingPowerService.findOne(id);
        return Optional.ofNullable(personFansPucharsingPower)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fans-pucharsing-powers/:id : delete the "id" personFansPucharsingPower.
     *
     * @param id the id of the personFansPucharsingPower to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fans-pucharsing-powers/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFansPucharsingPower(@PathVariable Long id) {
        log.debug("REST request to delete PersonFansPucharsingPower : {}", id);
        personFansPucharsingPowerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFansPucharsingPower", id.toString())).build();
    }

}
