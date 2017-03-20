package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.AreaType;
import com.intelligent.chart.service.AreaTypeService;
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
 * REST controller for managing AreaType.
 */
@RestController
@RequestMapping("/api")
public class AreaTypeResource {

    private final Logger log = LoggerFactory.getLogger(AreaTypeResource.class);
        
    @Inject
    private AreaTypeService areaTypeService;

    /**
     * POST  /area-types : Create a new areaType.
     *
     * @param areaType the areaType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new areaType, or with status 400 (Bad Request) if the areaType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/area-types")
    @Timed
    public ResponseEntity<AreaType> createAreaType(@RequestBody AreaType areaType) throws URISyntaxException {
        log.debug("REST request to save AreaType : {}", areaType);
        if (areaType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("areaType", "idexists", "A new areaType cannot already have an ID")).body(null);
        }
        AreaType result = areaTypeService.save(areaType);
        return ResponseEntity.created(new URI("/api/area-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("areaType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /area-types : Updates an existing areaType.
     *
     * @param areaType the areaType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated areaType,
     * or with status 400 (Bad Request) if the areaType is not valid,
     * or with status 500 (Internal Server Error) if the areaType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/area-types")
    @Timed
    public ResponseEntity<AreaType> updateAreaType(@RequestBody AreaType areaType) throws URISyntaxException {
        log.debug("REST request to update AreaType : {}", areaType);
        if (areaType.getId() == null) {
            return createAreaType(areaType);
        }
        AreaType result = areaTypeService.save(areaType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("areaType", areaType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /area-types : get all the areaTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of areaTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/area-types")
    @Timed
    public ResponseEntity<List<AreaType>> getAllAreaTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AreaTypes");
        Page<AreaType> page = areaTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/area-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /area-types/:id : get the "id" areaType.
     *
     * @param id the id of the areaType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the areaType, or with status 404 (Not Found)
     */
    @GetMapping("/area-types/{id}")
    @Timed
    public ResponseEntity<AreaType> getAreaType(@PathVariable Long id) {
        log.debug("REST request to get AreaType : {}", id);
        AreaType areaType = areaTypeService.findOne(id);
        return Optional.ofNullable(areaType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /area-types/:id : delete the "id" areaType.
     *
     * @param id the id of the areaType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/area-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAreaType(@PathVariable Long id) {
        log.debug("REST request to delete AreaType : {}", id);
        areaTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("areaType", id.toString())).build();
    }

}
