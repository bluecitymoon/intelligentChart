package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonInnovation;
import com.intelligent.chart.domain.PersonPopularity;
import com.intelligent.chart.repository.PersonPopularityRepository;
import com.intelligent.chart.service.PersonPopularityService;
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
 * REST controller for managing PersonPopularity.
 */
@RestController
@RequestMapping("/api")
public class PersonPopularityResource {

    private final Logger log = LoggerFactory.getLogger(PersonPopularityResource.class);

    @Inject
    private PersonPopularityService personPopularityService;

    @Inject
    private PersonPopularityRepository personPopularityRepository;

    @GetMapping("/person-popularities/person/{id}")
    @Timed
    public ResponseEntity<List<PersonPopularity>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonPopularity> page = personPopularityRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-popularities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-popularities : Create a new personPopularity.
     *
     * @param personPopularity the personPopularity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPopularity, or with status 400 (Bad Request) if the personPopularity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-popularities")
    @Timed
    public ResponseEntity<PersonPopularity> createPersonPopularity(@RequestBody PersonPopularity personPopularity) throws URISyntaxException {
        log.debug("REST request to save PersonPopularity : {}", personPopularity);
        if (personPopularity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personPopularity", "idexists", "A new personPopularity cannot already have an ID")).body(null);
        }
        PersonPopularity result = personPopularityService.save(personPopularity);
        return ResponseEntity.created(new URI("/api/person-popularities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personPopularity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-popularities : Updates an existing personPopularity.
     *
     * @param personPopularity the personPopularity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPopularity,
     * or with status 400 (Bad Request) if the personPopularity is not valid,
     * or with status 500 (Internal Server Error) if the personPopularity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-popularities")
    @Timed
    public ResponseEntity<PersonPopularity> updatePersonPopularity(@RequestBody PersonPopularity personPopularity) throws URISyntaxException {
        log.debug("REST request to update PersonPopularity : {}", personPopularity);
        if (personPopularity.getId() == null) {
            return createPersonPopularity(personPopularity);
        }
        PersonPopularity result = personPopularityService.save(personPopularity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personPopularity", personPopularity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-popularities : get all the personPopularities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personPopularities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-popularities")
    @Timed
    public ResponseEntity<List<PersonPopularity>> getAllPersonPopularities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonPopularities");
        Page<PersonPopularity> page = personPopularityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-popularities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-popularities/:id : get the "id" personPopularity.
     *
     * @param id the id of the personPopularity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPopularity, or with status 404 (Not Found)
     */
    @GetMapping("/person-popularities/{id}")
    @Timed
    public ResponseEntity<PersonPopularity> getPersonPopularity(@PathVariable Long id) {
        log.debug("REST request to get PersonPopularity : {}", id);
        PersonPopularity personPopularity = personPopularityService.findOne(id);
        return Optional.ofNullable(personPopularity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-popularities/:id : delete the "id" personPopularity.
     *
     * @param id the id of the personPopularity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-popularities/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonPopularity(@PathVariable Long id) {
        log.debug("REST request to delete PersonPopularity : {}", id);
        personPopularityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personPopularity", id.toString())).build();
    }

}
