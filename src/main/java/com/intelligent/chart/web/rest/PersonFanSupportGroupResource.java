package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonFanSupportGroup;
import com.intelligent.chart.service.PersonFanSupportGroupService;import com.intelligent.chart.repository.PersonFanSupportGroupRepository;
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
 * REST controller for managing PersonFanSupportGroup.
 */
@RestController
@RequestMapping("/api")
public class PersonFanSupportGroupResource {

    private final Logger log = LoggerFactory.getLogger(PersonFanSupportGroupResource.class);
        
    @Inject
    private PersonFanSupportGroupService personFanSupportGroupService;

    @Inject
    private PersonFanSupportGroupRepository personFanSupportGroupRepository;


    @GetMapping("/person-fan-support-groups/person/{id}")
    @Timed
    public ResponseEntity<List<PersonFanSupportGroup>> getAllPersonPersonFanSupportGroupByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
    throws URISyntaxException {

    Page<PersonFanSupportGroup> page = personFanSupportGroupRepository.findByPerson_Id(id, pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-support-groups");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-fan-support-groups : Create a new personFanSupportGroup.
     *
     * @param personFanSupportGroup the personFanSupportGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFanSupportGroup, or with status 400 (Bad Request) if the personFanSupportGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-fan-support-groups")
    @Timed
    public ResponseEntity<PersonFanSupportGroup> createPersonFanSupportGroup(@RequestBody PersonFanSupportGroup personFanSupportGroup) throws URISyntaxException {
        log.debug("REST request to save PersonFanSupportGroup : {}", personFanSupportGroup);
        if (personFanSupportGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personFanSupportGroup", "idexists", "A new personFanSupportGroup cannot already have an ID")).body(null);
        }
        PersonFanSupportGroup result = personFanSupportGroupService.save(personFanSupportGroup);
        return ResponseEntity.created(new URI("/api/person-fan-support-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personFanSupportGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-fan-support-groups : Updates an existing personFanSupportGroup.
     *
     * @param personFanSupportGroup the personFanSupportGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFanSupportGroup,
     * or with status 400 (Bad Request) if the personFanSupportGroup is not valid,
     * or with status 500 (Internal Server Error) if the personFanSupportGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-fan-support-groups")
    @Timed
    public ResponseEntity<PersonFanSupportGroup> updatePersonFanSupportGroup(@RequestBody PersonFanSupportGroup personFanSupportGroup) throws URISyntaxException {
        log.debug("REST request to update PersonFanSupportGroup : {}", personFanSupportGroup);
        if (personFanSupportGroup.getId() == null) {
            return createPersonFanSupportGroup(personFanSupportGroup);
        }
        PersonFanSupportGroup result = personFanSupportGroupService.save(personFanSupportGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personFanSupportGroup", personFanSupportGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-fan-support-groups : get all the personFanSupportGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personFanSupportGroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-fan-support-groups")
    @Timed
    public ResponseEntity<List<PersonFanSupportGroup>> getAllPersonFanSupportGroups(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonFanSupportGroups");
        Page<PersonFanSupportGroup> page = personFanSupportGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-fan-support-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-fan-support-groups/:id : get the "id" personFanSupportGroup.
     *
     * @param id the id of the personFanSupportGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFanSupportGroup, or with status 404 (Not Found)
     */
    @GetMapping("/person-fan-support-groups/{id}")
    @Timed
    public ResponseEntity<PersonFanSupportGroup> getPersonFanSupportGroup(@PathVariable Long id) {
        log.debug("REST request to get PersonFanSupportGroup : {}", id);
        PersonFanSupportGroup personFanSupportGroup = personFanSupportGroupService.findOne(id);
        return Optional.ofNullable(personFanSupportGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-fan-support-groups/:id : delete the "id" personFanSupportGroup.
     *
     * @param id the id of the personFanSupportGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-fan-support-groups/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFanSupportGroup(@PathVariable Long id) {
        log.debug("REST request to delete PersonFanSupportGroup : {}", id);
        personFanSupportGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personFanSupportGroup", id.toString())).build();
    }

}
