package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonSearchCount;
import com.intelligent.chart.service.PersonSearchCountService;import com.intelligent.chart.repository.PersonSearchCountRepository;
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
 * REST controller for managing PersonSearchCount.
 */
@RestController
@RequestMapping("/api")
public class PersonSearchCountResource {

    private final Logger log = LoggerFactory.getLogger(PersonSearchCountResource.class);

    @Inject
    private PersonSearchCountService personSearchCountService;

    @Inject
    private PersonSearchCountRepository personSearchCountRepository;


    @GetMapping("/person-search-counts/person/{id}")
    @Timed
    public List<PersonSearchCount> getAllPersonPersonSearchCountByPersonId(@PathVariable Long id)
    throws URISyntaxException {

    List<PersonSearchCount> page = personSearchCountRepository.findByPerson_Id(id);
    return page;
    }

    /**
     * POST  /person-search-counts : Create a new personSearchCount.
     *
     * @param personSearchCount the personSearchCount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personSearchCount, or with status 400 (Bad Request) if the personSearchCount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-search-counts")
    @Timed
    public ResponseEntity<PersonSearchCount> createPersonSearchCount(@RequestBody PersonSearchCount personSearchCount) throws URISyntaxException {
        log.debug("REST request to save PersonSearchCount : {}", personSearchCount);
        if (personSearchCount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personSearchCount", "idexists", "A new personSearchCount cannot already have an ID")).body(null);
        }
        PersonSearchCount result = personSearchCountService.save(personSearchCount);
        return ResponseEntity.created(new URI("/api/person-search-counts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personSearchCount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-search-counts : Updates an existing personSearchCount.
     *
     * @param personSearchCount the personSearchCount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personSearchCount,
     * or with status 400 (Bad Request) if the personSearchCount is not valid,
     * or with status 500 (Internal Server Error) if the personSearchCount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-search-counts")
    @Timed
    public ResponseEntity<PersonSearchCount> updatePersonSearchCount(@RequestBody PersonSearchCount personSearchCount) throws URISyntaxException {
        log.debug("REST request to update PersonSearchCount : {}", personSearchCount);
        if (personSearchCount.getId() == null) {
            return createPersonSearchCount(personSearchCount);
        }
        PersonSearchCount result = personSearchCountService.save(personSearchCount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personSearchCount", personSearchCount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-search-counts : get all the personSearchCounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personSearchCounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-search-counts")
    @Timed
    public ResponseEntity<List<PersonSearchCount>> getAllPersonSearchCounts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonSearchCounts");
        Page<PersonSearchCount> page = personSearchCountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-search-counts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-search-counts/:id : get the "id" personSearchCount.
     *
     * @param id the id of the personSearchCount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personSearchCount, or with status 404 (Not Found)
     */
    @GetMapping("/person-search-counts/{id}")
    @Timed
    public ResponseEntity<PersonSearchCount> getPersonSearchCount(@PathVariable Long id) {
        log.debug("REST request to get PersonSearchCount : {}", id);
        PersonSearchCount personSearchCount = personSearchCountService.findOne(id);
        return Optional.ofNullable(personSearchCount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-search-counts/:id : delete the "id" personSearchCount.
     *
     * @param id the id of the personSearchCount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-search-counts/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonSearchCount(@PathVariable Long id) {
        log.debug("REST request to delete PersonSearchCount : {}", id);
        personSearchCountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personSearchCount", id.toString())).build();
    }

}
