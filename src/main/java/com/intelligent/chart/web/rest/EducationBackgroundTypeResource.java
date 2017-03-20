package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.EducationBackgroundType;
import com.intelligent.chart.service.EducationBackgroundTypeService;
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
 * REST controller for managing EducationBackgroundType.
 */
@RestController
@RequestMapping("/api")
public class EducationBackgroundTypeResource {

    private final Logger log = LoggerFactory.getLogger(EducationBackgroundTypeResource.class);
        
    @Inject
    private EducationBackgroundTypeService educationBackgroundTypeService;

    /**
     * POST  /education-background-types : Create a new educationBackgroundType.
     *
     * @param educationBackgroundType the educationBackgroundType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationBackgroundType, or with status 400 (Bad Request) if the educationBackgroundType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/education-background-types")
    @Timed
    public ResponseEntity<EducationBackgroundType> createEducationBackgroundType(@RequestBody EducationBackgroundType educationBackgroundType) throws URISyntaxException {
        log.debug("REST request to save EducationBackgroundType : {}", educationBackgroundType);
        if (educationBackgroundType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("educationBackgroundType", "idexists", "A new educationBackgroundType cannot already have an ID")).body(null);
        }
        EducationBackgroundType result = educationBackgroundTypeService.save(educationBackgroundType);
        return ResponseEntity.created(new URI("/api/education-background-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("educationBackgroundType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education-background-types : Updates an existing educationBackgroundType.
     *
     * @param educationBackgroundType the educationBackgroundType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationBackgroundType,
     * or with status 400 (Bad Request) if the educationBackgroundType is not valid,
     * or with status 500 (Internal Server Error) if the educationBackgroundType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/education-background-types")
    @Timed
    public ResponseEntity<EducationBackgroundType> updateEducationBackgroundType(@RequestBody EducationBackgroundType educationBackgroundType) throws URISyntaxException {
        log.debug("REST request to update EducationBackgroundType : {}", educationBackgroundType);
        if (educationBackgroundType.getId() == null) {
            return createEducationBackgroundType(educationBackgroundType);
        }
        EducationBackgroundType result = educationBackgroundTypeService.save(educationBackgroundType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("educationBackgroundType", educationBackgroundType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education-background-types : get all the educationBackgroundTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of educationBackgroundTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/education-background-types")
    @Timed
    public ResponseEntity<List<EducationBackgroundType>> getAllEducationBackgroundTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EducationBackgroundTypes");
        Page<EducationBackgroundType> page = educationBackgroundTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/education-background-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /education-background-types/:id : get the "id" educationBackgroundType.
     *
     * @param id the id of the educationBackgroundType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationBackgroundType, or with status 404 (Not Found)
     */
    @GetMapping("/education-background-types/{id}")
    @Timed
    public ResponseEntity<EducationBackgroundType> getEducationBackgroundType(@PathVariable Long id) {
        log.debug("REST request to get EducationBackgroundType : {}", id);
        EducationBackgroundType educationBackgroundType = educationBackgroundTypeService.findOne(id);
        return Optional.ofNullable(educationBackgroundType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /education-background-types/:id : delete the "id" educationBackgroundType.
     *
     * @param id the id of the educationBackgroundType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/education-background-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationBackgroundType(@PathVariable Long id) {
        log.debug("REST request to delete EducationBackgroundType : {}", id);
        educationBackgroundTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("educationBackgroundType", id.toString())).build();
    }

}
