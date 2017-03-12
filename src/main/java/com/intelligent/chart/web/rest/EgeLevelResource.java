package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.EgeLevel;
import com.intelligent.chart.service.EgeLevelService;import com.intelligent.chart.repository.EgeLevelRepository;
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
 * REST controller for managing EgeLevel.
 */
@RestController
@RequestMapping("/api")
public class EgeLevelResource {

    private final Logger log = LoggerFactory.getLogger(EgeLevelResource.class);

    @Inject
    private EgeLevelService egeLevelService;



    /**
     * POST  /ege-levels : Create a new egeLevel.
     *
     * @param egeLevel the egeLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new egeLevel, or with status 400 (Bad Request) if the egeLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ege-levels")
    @Timed
    public ResponseEntity<EgeLevel> createEgeLevel(@RequestBody EgeLevel egeLevel) throws URISyntaxException {
        log.debug("REST request to save EgeLevel : {}", egeLevel);
        if (egeLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("egeLevel", "idexists", "A new egeLevel cannot already have an ID")).body(null);
        }
        EgeLevel result = egeLevelService.save(egeLevel);
        return ResponseEntity.created(new URI("/api/ege-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("egeLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ege-levels : Updates an existing egeLevel.
     *
     * @param egeLevel the egeLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated egeLevel,
     * or with status 400 (Bad Request) if the egeLevel is not valid,
     * or with status 500 (Internal Server Error) if the egeLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ege-levels")
    @Timed
    public ResponseEntity<EgeLevel> updateEgeLevel(@RequestBody EgeLevel egeLevel) throws URISyntaxException {
        log.debug("REST request to update EgeLevel : {}", egeLevel);
        if (egeLevel.getId() == null) {
            return createEgeLevel(egeLevel);
        }
        EgeLevel result = egeLevelService.save(egeLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("egeLevel", egeLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ege-levels : get all the egeLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of egeLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/ege-levels")
    @Timed
    public ResponseEntity<List<EgeLevel>> getAllEgeLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EgeLevels");
        Page<EgeLevel> page = egeLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ege-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ege-levels/:id : get the "id" egeLevel.
     *
     * @param id the id of the egeLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the egeLevel, or with status 404 (Not Found)
     */
    @GetMapping("/ege-levels/{id}")
    @Timed
    public ResponseEntity<EgeLevel> getEgeLevel(@PathVariable Long id) {
        log.debug("REST request to get EgeLevel : {}", id);
        EgeLevel egeLevel = egeLevelService.findOne(id);
        return Optional.ofNullable(egeLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ege-levels/:id : delete the "id" egeLevel.
     *
     * @param id the id of the egeLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ege-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteEgeLevel(@PathVariable Long id) {
        log.debug("REST request to delete EgeLevel : {}", id);
        egeLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("egeLevel", id.toString())).build();
    }

}
