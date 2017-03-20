package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonPopularity;
import com.intelligent.chart.domain.PersonPrize;
import com.intelligent.chart.repository.PersonPopularityRepository;
import com.intelligent.chart.repository.PersonPrizeRepository;
import com.intelligent.chart.service.PersonPrizeService;
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
 * REST controller for managing PersonPrize.
 */
@RestController
@RequestMapping("/api")
public class PersonPrizeResource {

    private final Logger log = LoggerFactory.getLogger(PersonPrizeResource.class);

    @Inject
    private PersonPrizeService personPrizeService;

    @Inject
    private PersonPrizeRepository personPrizeRepository;

    @GetMapping("/person-prizes/person/{id}")
    @Timed
    public ResponseEntity<List<PersonPrize>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonPrize> page = personPrizeRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-prizes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-prizes : Create a new personPrize.
     *
     * @param personPrize the personPrize to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personPrize, or with status 400 (Bad Request) if the personPrize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-prizes")
    @Timed
    public ResponseEntity<PersonPrize> createPersonPrize(@RequestBody PersonPrize personPrize) throws URISyntaxException {
        log.debug("REST request to save PersonPrize : {}", personPrize);
        if (personPrize.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personPrize", "idexists", "A new personPrize cannot already have an ID")).body(null);
        }
        PersonPrize result = personPrizeService.save(personPrize);
        return ResponseEntity.created(new URI("/api/person-prizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personPrize", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-prizes : Updates an existing personPrize.
     *
     * @param personPrize the personPrize to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personPrize,
     * or with status 400 (Bad Request) if the personPrize is not valid,
     * or with status 500 (Internal Server Error) if the personPrize couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-prizes")
    @Timed
    public ResponseEntity<PersonPrize> updatePersonPrize(@RequestBody PersonPrize personPrize) throws URISyntaxException {
        log.debug("REST request to update PersonPrize : {}", personPrize);
        if (personPrize.getId() == null) {
            return createPersonPrize(personPrize);
        }
        PersonPrize result = personPrizeService.save(personPrize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personPrize", personPrize.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-prizes : get all the personPrizes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personPrizes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-prizes")
    @Timed
    public ResponseEntity<List<PersonPrize>> getAllPersonPrizes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonPrizes");
        Page<PersonPrize> page = personPrizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-prizes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-prizes/:id : get the "id" personPrize.
     *
     * @param id the id of the personPrize to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personPrize, or with status 404 (Not Found)
     */
    @GetMapping("/person-prizes/{id}")
    @Timed
    public ResponseEntity<PersonPrize> getPersonPrize(@PathVariable Long id) {
        log.debug("REST request to get PersonPrize : {}", id);
        PersonPrize personPrize = personPrizeService.findOne(id);
        return Optional.ofNullable(personPrize)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-prizes/:id : delete the "id" personPrize.
     *
     * @param id the id of the personPrize to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-prizes/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonPrize(@PathVariable Long id) {
        log.debug("REST request to delete PersonPrize : {}", id);
        personPrizeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personPrize", id.toString())).build();
    }

}
