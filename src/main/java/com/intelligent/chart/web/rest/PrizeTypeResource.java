package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PrizeType;
import com.intelligent.chart.service.PrizeTypeService;
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
 * REST controller for managing PrizeType.
 */
@RestController
@RequestMapping("/api")
public class PrizeTypeResource {

    private final Logger log = LoggerFactory.getLogger(PrizeTypeResource.class);
        
    @Inject
    private PrizeTypeService prizeTypeService;

    /**
     * POST  /prize-types : Create a new prizeType.
     *
     * @param prizeType the prizeType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prizeType, or with status 400 (Bad Request) if the prizeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prize-types")
    @Timed
    public ResponseEntity<PrizeType> createPrizeType(@RequestBody PrizeType prizeType) throws URISyntaxException {
        log.debug("REST request to save PrizeType : {}", prizeType);
        if (prizeType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prizeType", "idexists", "A new prizeType cannot already have an ID")).body(null);
        }
        PrizeType result = prizeTypeService.save(prizeType);
        return ResponseEntity.created(new URI("/api/prize-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prizeType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prize-types : Updates an existing prizeType.
     *
     * @param prizeType the prizeType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prizeType,
     * or with status 400 (Bad Request) if the prizeType is not valid,
     * or with status 500 (Internal Server Error) if the prizeType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prize-types")
    @Timed
    public ResponseEntity<PrizeType> updatePrizeType(@RequestBody PrizeType prizeType) throws URISyntaxException {
        log.debug("REST request to update PrizeType : {}", prizeType);
        if (prizeType.getId() == null) {
            return createPrizeType(prizeType);
        }
        PrizeType result = prizeTypeService.save(prizeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prizeType", prizeType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prize-types : get all the prizeTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prizeTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/prize-types")
    @Timed
    public ResponseEntity<List<PrizeType>> getAllPrizeTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrizeTypes");
        Page<PrizeType> page = prizeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prize-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prize-types/:id : get the "id" prizeType.
     *
     * @param id the id of the prizeType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prizeType, or with status 404 (Not Found)
     */
    @GetMapping("/prize-types/{id}")
    @Timed
    public ResponseEntity<PrizeType> getPrizeType(@PathVariable Long id) {
        log.debug("REST request to get PrizeType : {}", id);
        PrizeType prizeType = prizeTypeService.findOne(id);
        return Optional.ofNullable(prizeType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prize-types/:id : delete the "id" prizeType.
     *
     * @param id the id of the prizeType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prize-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePrizeType(@PathVariable Long id) {
        log.debug("REST request to delete PrizeType : {}", id);
        prizeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prizeType", id.toString())).build();
    }

}
