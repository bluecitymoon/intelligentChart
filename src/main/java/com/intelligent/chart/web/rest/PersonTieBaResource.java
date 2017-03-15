package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonTieBa;
import com.intelligent.chart.repository.PersonTieBaRepository;
import com.intelligent.chart.service.PersonTieBaService;
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
 * REST controller for managing PersonTieBa.
 */
@RestController
@RequestMapping("/api")
public class PersonTieBaResource {

    private final Logger log = LoggerFactory.getLogger(PersonTieBaResource.class);

    @Inject
    private PersonTieBaService personTieBaService;

    @Inject
    private PersonTieBaRepository personTieBaRepository;

    @GetMapping("/person-tie-bas/person/{id}")
    @Timed
    public ResponseEntity<List<PersonTieBa>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonTieBa> page = personTieBaRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-tie-bas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-tie-bas : Create a new personTieBa.
     *
     * @param personTieBa the personTieBa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personTieBa, or with status 400 (Bad Request) if the personTieBa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-tie-bas")
    @Timed
    public ResponseEntity<PersonTieBa> createPersonTieBa(@RequestBody PersonTieBa personTieBa) throws URISyntaxException {
        log.debug("REST request to save PersonTieBa : {}", personTieBa);
        if (personTieBa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personTieBa", "idexists", "A new personTieBa cannot already have an ID")).body(null);
        }
        PersonTieBa result = personTieBaService.save(personTieBa);
        return ResponseEntity.created(new URI("/api/person-tie-bas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personTieBa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-tie-bas : Updates an existing personTieBa.
     *
     * @param personTieBa the personTieBa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personTieBa,
     * or with status 400 (Bad Request) if the personTieBa is not valid,
     * or with status 500 (Internal Server Error) if the personTieBa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-tie-bas")
    @Timed
    public ResponseEntity<PersonTieBa> updatePersonTieBa(@RequestBody PersonTieBa personTieBa) throws URISyntaxException {
        log.debug("REST request to update PersonTieBa : {}", personTieBa);
        if (personTieBa.getId() == null) {
            return createPersonTieBa(personTieBa);
        }
        PersonTieBa result = personTieBaService.save(personTieBa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personTieBa", personTieBa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-tie-bas : get all the personTieBas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personTieBas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-tie-bas")
    @Timed
    public ResponseEntity<List<PersonTieBa>> getAllPersonTieBas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonTieBas");
        Page<PersonTieBa> page = personTieBaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-tie-bas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-tie-bas/:id : get the "id" personTieBa.
     *
     * @param id the id of the personTieBa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personTieBa, or with status 404 (Not Found)
     */
    @GetMapping("/person-tie-bas/{id}")
    @Timed
    public ResponseEntity<PersonTieBa> getPersonTieBa(@PathVariable Long id) {
        log.debug("REST request to get PersonTieBa : {}", id);
        PersonTieBa personTieBa = personTieBaService.findOne(id);
        return Optional.ofNullable(personTieBa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-tie-bas/:id : delete the "id" personTieBa.
     *
     * @param id the id of the personTieBa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-tie-bas/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonTieBa(@PathVariable Long id) {
        log.debug("REST request to delete PersonTieBa : {}", id);
        personTieBaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personTieBa", id.toString())).build();
    }

}
