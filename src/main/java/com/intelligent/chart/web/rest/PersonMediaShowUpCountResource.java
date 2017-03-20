package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonMediaShowUpCount;
import com.intelligent.chart.service.PersonMediaShowUpCountService;import com.intelligent.chart.repository.PersonMediaShowUpCountRepository;
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
 * REST controller for managing PersonMediaShowUpCount.
 */
@RestController
@RequestMapping("/api")
public class PersonMediaShowUpCountResource {

    private final Logger log = LoggerFactory.getLogger(PersonMediaShowUpCountResource.class);
        
    @Inject
    private PersonMediaShowUpCountService personMediaShowUpCountService;

    @Inject
    private PersonMediaShowUpCountRepository personMediaShowUpCountRepository;


    @GetMapping("/person-media-show-up-counts/person/{id}")
    @Timed
    public ResponseEntity<List<PersonMediaShowUpCount>> getAllPersonPersonMediaShowUpCountByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonMediaShowUpCount> page = personMediaShowUpCountRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-media-show-up-counts");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-media-show-up-counts : Create a new personMediaShowUpCount.
     *
     * @param personMediaShowUpCount the personMediaShowUpCount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personMediaShowUpCount, or with status 400 (Bad Request) if the personMediaShowUpCount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-media-show-up-counts")
    @Timed
    public ResponseEntity<PersonMediaShowUpCount> createPersonMediaShowUpCount(@RequestBody PersonMediaShowUpCount personMediaShowUpCount) throws URISyntaxException {
        log.debug("REST request to save PersonMediaShowUpCount : {}", personMediaShowUpCount);
        if (personMediaShowUpCount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personMediaShowUpCount", "idexists", "A new personMediaShowUpCount cannot already have an ID")).body(null);
        }
        PersonMediaShowUpCount result = personMediaShowUpCountService.save(personMediaShowUpCount);
        return ResponseEntity.created(new URI("/api/person-media-show-up-counts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personMediaShowUpCount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-media-show-up-counts : Updates an existing personMediaShowUpCount.
     *
     * @param personMediaShowUpCount the personMediaShowUpCount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personMediaShowUpCount,
     * or with status 400 (Bad Request) if the personMediaShowUpCount is not valid,
     * or with status 500 (Internal Server Error) if the personMediaShowUpCount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-media-show-up-counts")
    @Timed
    public ResponseEntity<PersonMediaShowUpCount> updatePersonMediaShowUpCount(@RequestBody PersonMediaShowUpCount personMediaShowUpCount) throws URISyntaxException {
        log.debug("REST request to update PersonMediaShowUpCount : {}", personMediaShowUpCount);
        if (personMediaShowUpCount.getId() == null) {
            return createPersonMediaShowUpCount(personMediaShowUpCount);
        }
        PersonMediaShowUpCount result = personMediaShowUpCountService.save(personMediaShowUpCount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personMediaShowUpCount", personMediaShowUpCount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-media-show-up-counts : get all the personMediaShowUpCounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personMediaShowUpCounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-media-show-up-counts")
    @Timed
    public ResponseEntity<List<PersonMediaShowUpCount>> getAllPersonMediaShowUpCounts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonMediaShowUpCounts");
        Page<PersonMediaShowUpCount> page = personMediaShowUpCountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-media-show-up-counts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-media-show-up-counts/:id : get the "id" personMediaShowUpCount.
     *
     * @param id the id of the personMediaShowUpCount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personMediaShowUpCount, or with status 404 (Not Found)
     */
    @GetMapping("/person-media-show-up-counts/{id}")
    @Timed
    public ResponseEntity<PersonMediaShowUpCount> getPersonMediaShowUpCount(@PathVariable Long id) {
        log.debug("REST request to get PersonMediaShowUpCount : {}", id);
        PersonMediaShowUpCount personMediaShowUpCount = personMediaShowUpCountService.findOne(id);
        return Optional.ofNullable(personMediaShowUpCount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-media-show-up-counts/:id : delete the "id" personMediaShowUpCount.
     *
     * @param id the id of the personMediaShowUpCount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-media-show-up-counts/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonMediaShowUpCount(@PathVariable Long id) {
        log.debug("REST request to delete PersonMediaShowUpCount : {}", id);
        personMediaShowUpCountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personMediaShowUpCount", id.toString())).build();
    }

}
