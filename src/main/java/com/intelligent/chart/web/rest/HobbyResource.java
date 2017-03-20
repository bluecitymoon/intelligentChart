package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.Hobby;
import com.intelligent.chart.service.HobbyService;import com.intelligent.chart.repository.HobbyRepository;
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
 * REST controller for managing Hobby.
 */
@RestController
@RequestMapping("/api")
public class HobbyResource {

    private final Logger log = LoggerFactory.getLogger(HobbyResource.class);

    @Inject
    private HobbyService hobbyService;

    /**
     * POST  /hobbies : Create a new hobby.
     *
     * @param hobby the hobby to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hobby, or with status 400 (Bad Request) if the hobby has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hobbies")
    @Timed
    public ResponseEntity<Hobby> createHobby(@RequestBody Hobby hobby) throws URISyntaxException {
        log.debug("REST request to save Hobby : {}", hobby);
        if (hobby.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hobby", "idexists", "A new hobby cannot already have an ID")).body(null);
        }
        Hobby result = hobbyService.save(hobby);
        return ResponseEntity.created(new URI("/api/hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hobby", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hobbies : Updates an existing hobby.
     *
     * @param hobby the hobby to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hobby,
     * or with status 400 (Bad Request) if the hobby is not valid,
     * or with status 500 (Internal Server Error) if the hobby couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hobbies")
    @Timed
    public ResponseEntity<Hobby> updateHobby(@RequestBody Hobby hobby) throws URISyntaxException {
        log.debug("REST request to update Hobby : {}", hobby);
        if (hobby.getId() == null) {
            return createHobby(hobby);
        }
        Hobby result = hobbyService.save(hobby);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hobby", hobby.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hobbies : get all the hobbies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hobbies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/hobbies")
    @Timed
    public ResponseEntity<List<Hobby>> getAllHobbies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Hobbies");
        Page<Hobby> page = hobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hobbies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hobbies/:id : get the "id" hobby.
     *
     * @param id the id of the hobby to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hobby, or with status 404 (Not Found)
     */
    @GetMapping("/hobbies/{id}")
    @Timed
    public ResponseEntity<Hobby> getHobby(@PathVariable Long id) {
        log.debug("REST request to get Hobby : {}", id);
        Hobby hobby = hobbyService.findOne(id);
        return Optional.ofNullable(hobby)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hobbies/:id : delete the "id" hobby.
     *
     * @param id the id of the hobby to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hobbies/{id}")
    @Timed
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        log.debug("REST request to delete Hobby : {}", id);
        hobbyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hobby", id.toString())).build();
    }

}
