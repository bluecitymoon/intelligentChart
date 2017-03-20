package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PopularityType;
import com.intelligent.chart.service.PopularityTypeService;
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
 * REST controller for managing PopularityType.
 */
@RestController
@RequestMapping("/api")
public class PopularityTypeResource {

    private final Logger log = LoggerFactory.getLogger(PopularityTypeResource.class);
        
    @Inject
    private PopularityTypeService popularityTypeService;

    /**
     * POST  /popularity-types : Create a new popularityType.
     *
     * @param popularityType the popularityType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new popularityType, or with status 400 (Bad Request) if the popularityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/popularity-types")
    @Timed
    public ResponseEntity<PopularityType> createPopularityType(@RequestBody PopularityType popularityType) throws URISyntaxException {
        log.debug("REST request to save PopularityType : {}", popularityType);
        if (popularityType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("popularityType", "idexists", "A new popularityType cannot already have an ID")).body(null);
        }
        PopularityType result = popularityTypeService.save(popularityType);
        return ResponseEntity.created(new URI("/api/popularity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("popularityType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /popularity-types : Updates an existing popularityType.
     *
     * @param popularityType the popularityType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated popularityType,
     * or with status 400 (Bad Request) if the popularityType is not valid,
     * or with status 500 (Internal Server Error) if the popularityType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/popularity-types")
    @Timed
    public ResponseEntity<PopularityType> updatePopularityType(@RequestBody PopularityType popularityType) throws URISyntaxException {
        log.debug("REST request to update PopularityType : {}", popularityType);
        if (popularityType.getId() == null) {
            return createPopularityType(popularityType);
        }
        PopularityType result = popularityTypeService.save(popularityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("popularityType", popularityType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /popularity-types : get all the popularityTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of popularityTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/popularity-types")
    @Timed
    public ResponseEntity<List<PopularityType>> getAllPopularityTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PopularityTypes");
        Page<PopularityType> page = popularityTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/popularity-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /popularity-types/:id : get the "id" popularityType.
     *
     * @param id the id of the popularityType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the popularityType, or with status 404 (Not Found)
     */
    @GetMapping("/popularity-types/{id}")
    @Timed
    public ResponseEntity<PopularityType> getPopularityType(@PathVariable Long id) {
        log.debug("REST request to get PopularityType : {}", id);
        PopularityType popularityType = popularityTypeService.findOne(id);
        return Optional.ofNullable(popularityType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /popularity-types/:id : delete the "id" popularityType.
     *
     * @param id the id of the popularityType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/popularity-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePopularityType(@PathVariable Long id) {
        log.debug("REST request to delete PopularityType : {}", id);
        popularityTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("popularityType", id.toString())).build();
    }

}
