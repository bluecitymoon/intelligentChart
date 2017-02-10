package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PrizeLevel;
import com.intelligent.chart.service.PrizeLevelService;
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
 * REST controller for managing PrizeLevel.
 */
@RestController
@RequestMapping("/api")
public class PrizeLevelResource {

    private final Logger log = LoggerFactory.getLogger(PrizeLevelResource.class);
        
    @Inject
    private PrizeLevelService prizeLevelService;

    /**
     * POST  /prize-levels : Create a new prizeLevel.
     *
     * @param prizeLevel the prizeLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prizeLevel, or with status 400 (Bad Request) if the prizeLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prize-levels")
    @Timed
    public ResponseEntity<PrizeLevel> createPrizeLevel(@RequestBody PrizeLevel prizeLevel) throws URISyntaxException {
        log.debug("REST request to save PrizeLevel : {}", prizeLevel);
        if (prizeLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prizeLevel", "idexists", "A new prizeLevel cannot already have an ID")).body(null);
        }
        PrizeLevel result = prizeLevelService.save(prizeLevel);
        return ResponseEntity.created(new URI("/api/prize-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prizeLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prize-levels : Updates an existing prizeLevel.
     *
     * @param prizeLevel the prizeLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prizeLevel,
     * or with status 400 (Bad Request) if the prizeLevel is not valid,
     * or with status 500 (Internal Server Error) if the prizeLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prize-levels")
    @Timed
    public ResponseEntity<PrizeLevel> updatePrizeLevel(@RequestBody PrizeLevel prizeLevel) throws URISyntaxException {
        log.debug("REST request to update PrizeLevel : {}", prizeLevel);
        if (prizeLevel.getId() == null) {
            return createPrizeLevel(prizeLevel);
        }
        PrizeLevel result = prizeLevelService.save(prizeLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prizeLevel", prizeLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prize-levels : get all the prizeLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prizeLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/prize-levels")
    @Timed
    public ResponseEntity<List<PrizeLevel>> getAllPrizeLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrizeLevels");
        Page<PrizeLevel> page = prizeLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prize-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prize-levels/:id : get the "id" prizeLevel.
     *
     * @param id the id of the prizeLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prizeLevel, or with status 404 (Not Found)
     */
    @GetMapping("/prize-levels/{id}")
    @Timed
    public ResponseEntity<PrizeLevel> getPrizeLevel(@PathVariable Long id) {
        log.debug("REST request to get PrizeLevel : {}", id);
        PrizeLevel prizeLevel = prizeLevelService.findOne(id);
        return Optional.ofNullable(prizeLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prize-levels/:id : delete the "id" prizeLevel.
     *
     * @param id the id of the prizeLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prize-levels/{id}")
    @Timed
    public ResponseEntity<Void> deletePrizeLevel(@PathVariable Long id) {
        log.debug("REST request to delete PrizeLevel : {}", id);
        prizeLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prizeLevel", id.toString())).build();
    }

}
