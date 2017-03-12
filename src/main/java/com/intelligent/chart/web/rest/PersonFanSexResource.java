package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFanSex;
import com.intelligent.chart.service.PersonFanSexService;import com.intelligent.chart.repository.PersonFanSexRepository;
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
 * REST controller for managing PersonFanSex.
 */
@RestController
@RequestMapping("/api")
public class PersonFanSexResource {

    private final Logger log = LoggerFactory.getLogger(PersonFanSexResource.class);
        
    @Inject
    private PersonFanSexService personFanSexService;

    @Inject
    private PersonFanSexRepository personFanSexRepository;


    @GetMapping("/person-fan-sexes/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFanSex>> getAllPersonPersonFanSexByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFanSex> page = personFanSexRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-sexes");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fan-sexes : Create a new personFanSex.
     *
     * @param personFanSex the personFanSex to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFanSex, or with status 400 (Bad Request) if the personFanSex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fan-sexes")
    @Timed
    public ResponseEntity<PersonFanSex> createPersonFanSex(@RequestBody PersonFanSex personFanSex) throws URISyntaxException {
        log.debug("REST request to save PersonFanSex : {}", personFanSex);
        if (personFanSex.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFanSex", "idexists", "A new personFanSex cannot already have an ID")).body(null);
        }
        PersonFanSex result = personFanSexService.save(personFanSex);
        return ResponseEntity.created(new URI("/api/person-fan-sexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFanSex", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fan-sexes : Updates an existing personFanSex.
     *
     * @param personFanSex the personFanSex to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFanSex,
     * or with status 400 (Bad Request) if the personFanSex is not valid,
     * or with status 500 (Internal Server Error) if the personFanSex couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fan-sexes")
    @Timed
    public ResponseEntity<PersonFanSex> updatePersonFanSex(@RequestBody PersonFanSex personFanSex) throws URISyntaxException {
        log.debug("REST request to update PersonFanSex : {}", personFanSex);
        if (personFanSex.getId() == null) {
            return createPersonFanSex(personFanSex);
        }
        PersonFanSex result = personFanSexService.save(personFanSex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFanSex", personFanSex.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fan-sexes : get all the personFanSexes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFanSexes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fan-sexes")
    @Timed
    public ResponseEntity<List<PersonFanSex>> getAllPersonFanSexes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFanSexes");
        Page<PersonFanSex> page = personFanSexService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-sexes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fan-sexes/:id : get the "id" personFanSex.
     *
     * @param id the id of the personFanSex to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFanSex, or with status 404 (Not Found)
     */
    @GetMapping("/person-fan-sexes/{id}")
    @Timed
    public ResponseEntity<PersonFanSex> getPersonFanSex(@PathVariable Long id) {
        log.debug("REST request to get PersonFanSex : {}", id);
        PersonFanSex personFanSex = personFanSexService.findOne(id);
        return Optional.ofNullable(personFanSex)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fan-sexes/:id : delete the "id" personFanSex.
     *
     * @param id the id of the personFanSex to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fan-sexes/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFanSex(@PathVariable Long id) {
        log.debug("REST request to delete PersonFanSex : {}", id);
        personFanSexService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFanSex", id.toString())).build();
    }

}
