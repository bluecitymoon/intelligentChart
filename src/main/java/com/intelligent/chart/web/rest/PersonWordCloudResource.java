package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonWordCloud;
import com.intelligent.chart.repository.PersonWordCloudRepository;
import com.intelligent.chart.service.PersonWordCloudService;
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
 * REST controller for managing PersonWordCloud.
 */
@RestController
@RequestMapping("/api")
public class PersonWordCloudResource {

    private final Logger log = LoggerFactory.getLogger(PersonWordCloudResource.class);

    @Inject
    private PersonWordCloudService personWordCloudService;

    @Inject
    private PersonWordCloudRepository personWordCloudRepository;

    @GetMapping("/person-word-clouds/person/{id}")
    @Timed
    public ResponseEntity<List<PersonWordCloud>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonWordCloud> page = personWordCloudRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-word-clouds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-word-clouds : Create a new personWordCloud.
     *
     * @param personWordCloud the personWordCloud to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personWordCloud, or with status 400 (Bad Request) if the personWordCloud has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-word-clouds")
    @Timed
    public ResponseEntity<PersonWordCloud> createPersonWordCloud(@RequestBody PersonWordCloud personWordCloud) throws URISyntaxException {
        log.debug("REST request to save PersonWordCloud : {}", personWordCloud);
        if (personWordCloud.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personWordCloud", "idexists", "A new personWordCloud cannot already have an ID")).body(null);
        }
        PersonWordCloud result = personWordCloudService.save(personWordCloud);
        return ResponseEntity.created(new URI("/api/person-word-clouds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personWordCloud", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-word-clouds : Updates an existing personWordCloud.
     *
     * @param personWordCloud the personWordCloud to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personWordCloud,
     * or with status 400 (Bad Request) if the personWordCloud is not valid,
     * or with status 500 (Internal Server Error) if the personWordCloud couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-word-clouds")
    @Timed
    public ResponseEntity<PersonWordCloud> updatePersonWordCloud(@RequestBody PersonWordCloud personWordCloud) throws URISyntaxException {
        log.debug("REST request to update PersonWordCloud : {}", personWordCloud);
        if (personWordCloud.getId() == null) {
            return createPersonWordCloud(personWordCloud);
        }
        PersonWordCloud result = personWordCloudService.save(personWordCloud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personWordCloud", personWordCloud.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-word-clouds : get all the personWordClouds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personWordClouds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-word-clouds")
    @Timed
    public ResponseEntity<List<PersonWordCloud>> getAllPersonWordClouds(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonWordClouds");
        Page<PersonWordCloud> page = personWordCloudService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-word-clouds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-word-clouds/:id : get the "id" personWordCloud.
     *
     * @param id the id of the personWordCloud to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personWordCloud, or with status 404 (Not Found)
     */
    @GetMapping("/person-word-clouds/{id}")
    @Timed
    public ResponseEntity<PersonWordCloud> getPersonWordCloud(@PathVariable Long id) {
        log.debug("REST request to get PersonWordCloud : {}", id);
        PersonWordCloud personWordCloud = personWordCloudService.findOne(id);
        return Optional.ofNullable(personWordCloud)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-word-clouds/:id : delete the "id" personWordCloud.
     *
     * @param id the id of the personWordCloud to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-word-clouds/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonWordCloud(@PathVariable Long id) {
        log.debug("REST request to delete PersonWordCloud : {}", id);
        personWordCloudService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personWordCloud", id.toString())).build();
    }

}
