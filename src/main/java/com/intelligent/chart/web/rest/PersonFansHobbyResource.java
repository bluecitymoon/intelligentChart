package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFansHobby;
import com.intelligent.chart.service.PersonFansHobbyService;import com.intelligent.chart.repository.PersonFansHobbyRepository;
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
 * REST controller for managing PersonFansHobby.
 */
@RestController
@RequestMapping("/api")
public class PersonFansHobbyResource {

    private final Logger log = LoggerFactory.getLogger(PersonFansHobbyResource.class);
        
    @Inject
    private PersonFansHobbyService personFansHobbyService;

    @Inject
    private PersonFansHobbyRepository personFansHobbyRepository;


    @GetMapping("/person-fans-hobbies/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFansHobby>> getAllPersonPersonFansHobbyByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFansHobby> page = personFansHobbyRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-hobbies");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fans-hobbies : Create a new personFansHobby.
     *
     * @param personFansHobby the personFansHobby to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFansHobby, or with status 400 (Bad Request) if the personFansHobby has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fans-hobbies")
    @Timed
    public ResponseEntity<PersonFansHobby> createPersonFansHobby(@RequestBody PersonFansHobby personFansHobby) throws URISyntaxException {
        log.debug("REST request to save PersonFansHobby : {}", personFansHobby);
        if (personFansHobby.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFansHobby", "idexists", "A new personFansHobby cannot already have an ID")).body(null);
        }
        PersonFansHobby result = personFansHobbyService.save(personFansHobby);
        return ResponseEntity.created(new URI("/api/person-fans-hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFansHobby", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fans-hobbies : Updates an existing personFansHobby.
     *
     * @param personFansHobby the personFansHobby to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFansHobby,
     * or with status 400 (Bad Request) if the personFansHobby is not valid,
     * or with status 500 (Internal Server Error) if the personFansHobby couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fans-hobbies")
    @Timed
    public ResponseEntity<PersonFansHobby> updatePersonFansHobby(@RequestBody PersonFansHobby personFansHobby) throws URISyntaxException {
        log.debug("REST request to update PersonFansHobby : {}", personFansHobby);
        if (personFansHobby.getId() == null) {
            return createPersonFansHobby(personFansHobby);
        }
        PersonFansHobby result = personFansHobbyService.save(personFansHobby);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFansHobby", personFansHobby.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fans-hobbies : get all the personFansHobbies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFansHobbies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fans-hobbies")
    @Timed
    public ResponseEntity<List<PersonFansHobby>> getAllPersonFansHobbies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFansHobbies");
        Page<PersonFansHobby> page = personFansHobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fans-hobbies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fans-hobbies/:id : get the "id" personFansHobby.
     *
     * @param id the id of the personFansHobby to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFansHobby, or with status 404 (Not Found)
     */
    @GetMapping("/person-fans-hobbies/{id}")
    @Timed
    public ResponseEntity<PersonFansHobby> getPersonFansHobby(@PathVariable Long id) {
        log.debug("REST request to get PersonFansHobby : {}", id);
        PersonFansHobby personFansHobby = personFansHobbyService.findOne(id);
        return Optional.ofNullable(personFansHobby)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fans-hobbies/:id : delete the "id" personFansHobby.
     *
     * @param id the id of the personFansHobby to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fans-hobbies/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFansHobby(@PathVariable Long id) {
        log.debug("REST request to delete PersonFansHobby : {}", id);
        personFansHobbyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFansHobby", id.toString())).build();
    }

}
