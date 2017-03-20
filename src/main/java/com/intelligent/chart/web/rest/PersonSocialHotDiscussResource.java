package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonSocialHotDiscuss;
import com.intelligent.chart.service.PersonSocialHotDiscussService;import com.intelligent.chart.repository.PersonSocialHotDiscussRepository;
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
 * REST controller for managing PersonSocialHotDiscuss.
 */
@RestController
@RequestMapping("/api")
public class  PersonSocialHotDiscussResource {

    private final Logger log = LoggerFactory.getLogger(PersonSocialHotDiscussResource.class);

    @Inject
    private PersonSocialHotDiscussService personSocialHotDiscussService;

    @Inject
    private PersonSocialHotDiscussRepository personSocialHotDiscussRepository;


    @GetMapping("/person-social-hot-discusses/person/{id}")
    @Timed
    public ResponseEntity<List<PersonSocialHotDiscuss>> getAllPersonPersonSocialHotDiscussByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonSocialHotDiscuss> page = personSocialHotDiscussRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-social-hot-discusses");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-social-hot-discusses : Create a new personSocialHotDiscuss.
     *
     * @param personSocialHotDiscuss the personSocialHotDiscuss to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personSocialHotDiscuss, or with status 400 (Bad Request) if the personSocialHotDiscuss has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-social-hot-discusses")
    @Timed
    public ResponseEntity<PersonSocialHotDiscuss> createPersonSocialHotDiscuss(@RequestBody PersonSocialHotDiscuss personSocialHotDiscuss) throws URISyntaxException {
        log.debug("REST request to save PersonSocialHotDiscuss : {}", personSocialHotDiscuss);
        if (personSocialHotDiscuss.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personSocialHotDiscuss", "idexists", "A new personSocialHotDiscuss cannot already have an ID")).body(null);
        }
        PersonSocialHotDiscuss result = personSocialHotDiscussService.save(personSocialHotDiscuss);
        return ResponseEntity.created(new URI("/api/person-social-hot-discusses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personSocialHotDiscuss", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-social-hot-discusses : Updates an existing personSocialHotDiscuss.
     *
     * @param personSocialHotDiscuss the personSocialHotDiscuss to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personSocialHotDiscuss,
     * or with status 400 (Bad Request) if the personSocialHotDiscuss is not valid,
     * or with status 500 (Internal Server Error) if the personSocialHotDiscuss couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-social-hot-discusses")
    @Timed
    public ResponseEntity<PersonSocialHotDiscuss> updatePersonSocialHotDiscuss(@RequestBody PersonSocialHotDiscuss personSocialHotDiscuss) throws URISyntaxException {
        log.debug("REST request to update PersonSocialHotDiscuss : {}", personSocialHotDiscuss);
        if (personSocialHotDiscuss.getId() == null) {
            return createPersonSocialHotDiscuss(personSocialHotDiscuss);
        }
        PersonSocialHotDiscuss result = personSocialHotDiscussService.save(personSocialHotDiscuss);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personSocialHotDiscuss", personSocialHotDiscuss.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-social-hot-discusses : get all the personSocialHotDiscusses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personSocialHotDiscusses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-social-hot-discusses")
    @Timed
    public ResponseEntity<List<PersonSocialHotDiscuss>> getAllPersonSocialHotDiscusses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonSocialHotDiscusses");
        Page<PersonSocialHotDiscuss> page = personSocialHotDiscussService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-social-hot-discusses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-social-hot-discusses/:id : get the "id" personSocialHotDiscuss.
     *
     * @param id the id of the personSocialHotDiscuss to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personSocialHotDiscuss, or with status 404 (Not Found)
     */
    @GetMapping("/person-social-hot-discusses/{id}")
    @Timed
    public ResponseEntity<PersonSocialHotDiscuss> getPersonSocialHotDiscuss(@PathVariable Long id) {
        log.debug("REST request to get PersonSocialHotDiscuss : {}", id);
        PersonSocialHotDiscuss personSocialHotDiscuss = personSocialHotDiscussService.findOne(id);
        return Optional.ofNullable(personSocialHotDiscuss)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-social-hot-discusses/:id : delete the "id" personSocialHotDiscuss.
     *
     * @param id the id of the personSocialHotDiscuss to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-social-hot-discusses/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonSocialHotDiscuss(@PathVariable Long id) {
        log.debug("REST request to delete PersonSocialHotDiscuss : {}", id);
        personSocialHotDiscussService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personSocialHotDiscuss", id.toString())).build();
    }

}
