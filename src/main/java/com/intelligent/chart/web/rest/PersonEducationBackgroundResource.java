package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonEducationBackground;
import com.intelligent.chart.repository.PersonEducationBackgroundRepository;
import com.intelligent.chart.service.PersonEducationBackgroundService;
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
 * REST controller for managing PersonEducationBackground.
 */
@RestController
@RequestMapping("/api")
public class PersonEducationBackgroundResource {

    private final Logger log = LoggerFactory.getLogger(PersonEducationBackgroundResource.class);

    @Inject
    private PersonEducationBackgroundService personEducationBackgroundService;

        @Inject
        private PersonEducationBackgroundRepository personEducationBackgroundRepository;

        @GetMapping("/person-education-backgrounds/person/{id}")
        @Timed
        public ResponseEntity<List<PersonEducationBackground>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
            throws URISyntaxException {

            Page<PersonEducationBackground> page = personEducationBackgroundRepository.findByPerson_Id(id, pageable);

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-education-backgrounds");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }

    /**
     * POST  /person-education-backgrounds : Create a new personEducationBackground.
     *
     * @param personEducationBackground the personEducationBackground to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personEducationBackground, or with status 400 (Bad Request) if the personEducationBackground has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-education-backgrounds")
    @Timed
    public ResponseEntity<PersonEducationBackground> createPersonEducationBackground(@RequestBody PersonEducationBackground personEducationBackground) throws URISyntaxException {
        log.debug("REST request to save PersonEducationBackground : {}", personEducationBackground);
        if (personEducationBackground.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personEducationBackground", "idexists", "A new personEducationBackground cannot already have an ID")).body(null);
        }
        PersonEducationBackground result = personEducationBackgroundService.save(personEducationBackground);
        return ResponseEntity.created(new URI("/api/person-education-backgrounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personEducationBackground", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-education-backgrounds : Updates an existing personEducationBackground.
     *
     * @param personEducationBackground the personEducationBackground to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personEducationBackground,
     * or with status 400 (Bad Request) if the personEducationBackground is not valid,
     * or with status 500 (Internal Server Error) if the personEducationBackground couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-education-backgrounds")
    @Timed
    public ResponseEntity<PersonEducationBackground> updatePersonEducationBackground(@RequestBody PersonEducationBackground personEducationBackground) throws URISyntaxException {
        log.debug("REST request to update PersonEducationBackground : {}", personEducationBackground);
        if (personEducationBackground.getId() == null) {
            return createPersonEducationBackground(personEducationBackground);
        }
        PersonEducationBackground result = personEducationBackgroundService.save(personEducationBackground);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personEducationBackground", personEducationBackground.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-education-backgrounds : get all the personEducationBackgrounds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personEducationBackgrounds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-education-backgrounds")
    @Timed
    public ResponseEntity<List<PersonEducationBackground>> getAllPersonEducationBackgrounds(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonEducationBackgrounds");
        Page<PersonEducationBackground> page = personEducationBackgroundService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-education-backgrounds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-education-backgrounds/:id : get the "id" personEducationBackground.
     *
     * @param id the id of the personEducationBackground to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personEducationBackground, or with status 404 (Not Found)
     */
    @GetMapping("/person-education-backgrounds/{id}")
    @Timed
    public ResponseEntity<PersonEducationBackground> getPersonEducationBackground(@PathVariable Long id) {
        log.debug("REST request to get PersonEducationBackground : {}", id);
        PersonEducationBackground personEducationBackground = personEducationBackgroundService.findOne(id);
        return Optional.ofNullable(personEducationBackground)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-education-backgrounds/:id : delete the "id" personEducationBackground.
     *
     * @param id the id of the personEducationBackground to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-education-backgrounds/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonEducationBackground(@PathVariable Long id) {
        log.debug("REST request to delete PersonEducationBackground : {}", id);
        personEducationBackgroundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personEducationBackground", id.toString())).build();
    }

}
