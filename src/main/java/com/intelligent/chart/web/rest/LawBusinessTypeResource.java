package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.LawBusinessType;
import com.intelligent.chart.service.LawBusinessTypeService;
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
 * REST controller for managing LawBusinessType.
 */
@RestController
@RequestMapping("/api")
public class LawBusinessTypeResource {

    private final Logger log = LoggerFactory.getLogger(LawBusinessTypeResource.class);
        
    @Inject
    private LawBusinessTypeService lawBusinessTypeService;

    /**
     * POST  /law-business-types : Create a new lawBusinessType.
     *
     * @param lawBusinessType the lawBusinessType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lawBusinessType, or with status 400 (Bad Request) if the lawBusinessType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/law-business-types")
    @Timed
    public ResponseEntity<LawBusinessType> createLawBusinessType(@RequestBody LawBusinessType lawBusinessType) throws URISyntaxException {
        log.debug("REST request to save LawBusinessType : {}", lawBusinessType);
        if (lawBusinessType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lawBusinessType", "idexists", "A new lawBusinessType cannot already have an ID")).body(null);
        }
        LawBusinessType result = lawBusinessTypeService.save(lawBusinessType);
        return ResponseEntity.created(new URI("/api/law-business-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lawBusinessType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /law-business-types : Updates an existing lawBusinessType.
     *
     * @param lawBusinessType the lawBusinessType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lawBusinessType,
     * or with status 400 (Bad Request) if the lawBusinessType is not valid,
     * or with status 500 (Internal Server Error) if the lawBusinessType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/law-business-types")
    @Timed
    public ResponseEntity<LawBusinessType> updateLawBusinessType(@RequestBody LawBusinessType lawBusinessType) throws URISyntaxException {
        log.debug("REST request to update LawBusinessType : {}", lawBusinessType);
        if (lawBusinessType.getId() == null) {
            return createLawBusinessType(lawBusinessType);
        }
        LawBusinessType result = lawBusinessTypeService.save(lawBusinessType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lawBusinessType", lawBusinessType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /law-business-types : get all the lawBusinessTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lawBusinessTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/law-business-types")
    @Timed
    public ResponseEntity<List<LawBusinessType>> getAllLawBusinessTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LawBusinessTypes");
        Page<LawBusinessType> page = lawBusinessTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/law-business-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /law-business-types/:id : get the "id" lawBusinessType.
     *
     * @param id the id of the lawBusinessType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lawBusinessType, or with status 404 (Not Found)
     */
    @GetMapping("/law-business-types/{id}")
    @Timed
    public ResponseEntity<LawBusinessType> getLawBusinessType(@PathVariable Long id) {
        log.debug("REST request to get LawBusinessType : {}", id);
        LawBusinessType lawBusinessType = lawBusinessTypeService.findOne(id);
        return Optional.ofNullable(lawBusinessType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /law-business-types/:id : delete the "id" lawBusinessType.
     *
     * @param id the id of the lawBusinessType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/law-business-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLawBusinessType(@PathVariable Long id) {
        log.debug("REST request to delete LawBusinessType : {}", id);
        lawBusinessTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lawBusinessType", id.toString())).build();
    }

}
