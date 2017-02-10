package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonExperience;
import com.intelligent.chart.service.PersonExperienceService;
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
 * REST controller for managing PersonExperience.
 */
@RestController
@RequestMapping("/api")
public class PersonExperienceResource {

    private final Logger log = LoggerFactory.getLogger(PersonExperienceResource.class);
        
    @Inject
    private PersonExperienceService personExperienceService;

    /**
     * POST  /person-experiences : Create a new personExperience.
     *
     * @param personExperience the personExperience to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personExperience, or with status 400 (Bad Request) if the personExperience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-experiences")
    @Timed
    public ResponseEntity<PersonExperience> createPersonExperience(@RequestBody PersonExperience personExperience) throws URISyntaxException {
        log.debug("REST request to save PersonExperience : {}", personExperience);
        if (personExperience.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personExperience", "idexists", "A new personExperience cannot already have an ID")).body(null);
        }
        PersonExperience result = personExperienceService.save(personExperience);
        return ResponseEntity.created(new URI("/api/person-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personExperience", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-experiences : Updates an existing personExperience.
     *
     * @param personExperience the personExperience to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personExperience,
     * or with status 400 (Bad Request) if the personExperience is not valid,
     * or with status 500 (Internal Server Error) if the personExperience couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-experiences")
    @Timed
    public ResponseEntity<PersonExperience> updatePersonExperience(@RequestBody PersonExperience personExperience) throws URISyntaxException {
        log.debug("REST request to update PersonExperience : {}", personExperience);
        if (personExperience.getId() == null) {
            return createPersonExperience(personExperience);
        }
        PersonExperience result = personExperienceService.save(personExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personExperience", personExperience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-experiences : get all the personExperiences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personExperiences in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-experiences")
    @Timed
    public ResponseEntity<List<PersonExperience>> getAllPersonExperiences(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonExperiences");
        Page<PersonExperience> page = personExperienceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-experiences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-experiences/:id : get the "id" personExperience.
     *
     * @param id the id of the personExperience to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personExperience, or with status 404 (Not Found)
     */
    @GetMapping("/person-experiences/{id}")
    @Timed
    public ResponseEntity<PersonExperience> getPersonExperience(@PathVariable Long id) {
        log.debug("REST request to get PersonExperience : {}", id);
        PersonExperience personExperience = personExperienceService.findOne(id);
        return Optional.ofNullable(personExperience)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-experiences/:id : delete the "id" personExperience.
     *
     * @param id the id of the personExperience to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-experiences/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonExperience(@PathVariable Long id) {
        log.debug("REST request to delete PersonExperience : {}", id);
        personExperienceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personExperience", id.toString())).build();
    }

}
