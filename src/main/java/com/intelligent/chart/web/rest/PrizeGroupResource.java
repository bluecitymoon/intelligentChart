package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PrizeGroup;
import com.intelligent.chart.service.PrizeGroupService;
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
 * REST controller for managing PrizeGroup.
 */
@RestController
@RequestMapping("/api")
public class PrizeGroupResource {

    private final Logger log = LoggerFactory.getLogger(PrizeGroupResource.class);
        
    @Inject
    private PrizeGroupService prizeGroupService;

    /**
     * POST  /prize-groups : Create a new prizeGroup.
     *
     * @param prizeGroup the prizeGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prizeGroup, or with status 400 (Bad Request) if the prizeGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prize-groups")
    @Timed
    public ResponseEntity<PrizeGroup> createPrizeGroup(@RequestBody PrizeGroup prizeGroup) throws URISyntaxException {
        log.debug("REST request to save PrizeGroup : {}", prizeGroup);
        if (prizeGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prizeGroup", "idexists", "A new prizeGroup cannot already have an ID")).body(null);
        }
        PrizeGroup result = prizeGroupService.save(prizeGroup);
        return ResponseEntity.created(new URI("/api/prize-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prizeGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prize-groups : Updates an existing prizeGroup.
     *
     * @param prizeGroup the prizeGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prizeGroup,
     * or with status 400 (Bad Request) if the prizeGroup is not valid,
     * or with status 500 (Internal Server Error) if the prizeGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prize-groups")
    @Timed
    public ResponseEntity<PrizeGroup> updatePrizeGroup(@RequestBody PrizeGroup prizeGroup) throws URISyntaxException {
        log.debug("REST request to update PrizeGroup : {}", prizeGroup);
        if (prizeGroup.getId() == null) {
            return createPrizeGroup(prizeGroup);
        }
        PrizeGroup result = prizeGroupService.save(prizeGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prizeGroup", prizeGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prize-groups : get all the prizeGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prizeGroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/prize-groups")
    @Timed
    public ResponseEntity<List<PrizeGroup>> getAllPrizeGroups(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrizeGroups");
        Page<PrizeGroup> page = prizeGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prize-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prize-groups/:id : get the "id" prizeGroup.
     *
     * @param id the id of the prizeGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prizeGroup, or with status 404 (Not Found)
     */
    @GetMapping("/prize-groups/{id}")
    @Timed
    public ResponseEntity<PrizeGroup> getPrizeGroup(@PathVariable Long id) {
        log.debug("REST request to get PrizeGroup : {}", id);
        PrizeGroup prizeGroup = prizeGroupService.findOne(id);
        return Optional.ofNullable(prizeGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prize-groups/:id : delete the "id" prizeGroup.
     *
     * @param id the id of the prizeGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prize-groups/{id}")
    @Timed
    public ResponseEntity<Void> deletePrizeGroup(@PathVariable Long id) {
        log.debug("REST request to delete PrizeGroup : {}", id);
        prizeGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prizeGroup", id.toString())).build();
    }

}
