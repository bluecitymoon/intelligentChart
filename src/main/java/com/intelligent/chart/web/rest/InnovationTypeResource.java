package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.InnovationType;
import com.intelligent.chart.service.InnovationTypeService;
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
 * REST controller for managing InnovationType.
 */
@RestController
@RequestMapping("/api")
public class InnovationTypeResource {

    private final Logger log = LoggerFactory.getLogger(InnovationTypeResource.class);
        
    @Inject
    private InnovationTypeService innovationTypeService;

    /**
     * POST  /innovation-types : Create a new innovationType.
     *
     * @param innovationType the innovationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new innovationType, or with status 400 (Bad Request) if the innovationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/innovation-types")
    @Timed
    public ResponseEntity<InnovationType> createInnovationType(@RequestBody InnovationType innovationType) throws URISyntaxException {
        log.debug("REST request to save InnovationType : {}", innovationType);
        if (innovationType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("innovationType", "idexists", "A new innovationType cannot already have an ID")).body(null);
        }
        InnovationType result = innovationTypeService.save(innovationType);
        return ResponseEntity.created(new URI("/api/innovation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("innovationType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /innovation-types : Updates an existing innovationType.
     *
     * @param innovationType the innovationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated innovationType,
     * or with status 400 (Bad Request) if the innovationType is not valid,
     * or with status 500 (Internal Server Error) if the innovationType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/innovation-types")
    @Timed
    public ResponseEntity<InnovationType> updateInnovationType(@RequestBody InnovationType innovationType) throws URISyntaxException {
        log.debug("REST request to update InnovationType : {}", innovationType);
        if (innovationType.getId() == null) {
            return createInnovationType(innovationType);
        }
        InnovationType result = innovationTypeService.save(innovationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("innovationType", innovationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /innovation-types : get all the innovationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of innovationTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/innovation-types")
    @Timed
    public ResponseEntity<List<InnovationType>> getAllInnovationTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of InnovationTypes");
        Page<InnovationType> page = innovationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/innovation-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /innovation-types/:id : get the "id" innovationType.
     *
     * @param id the id of the innovationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the innovationType, or with status 404 (Not Found)
     */
    @GetMapping("/innovation-types/{id}")
    @Timed
    public ResponseEntity<InnovationType> getInnovationType(@PathVariable Long id) {
        log.debug("REST request to get InnovationType : {}", id);
        InnovationType innovationType = innovationTypeService.findOne(id);
        return Optional.ofNullable(innovationType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /innovation-types/:id : delete the "id" innovationType.
     *
     * @param id the id of the innovationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/innovation-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteInnovationType(@PathVariable Long id) {
        log.debug("REST request to delete InnovationType : {}", id);
        innovationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("innovationType", id.toString())).build();
    }

}
