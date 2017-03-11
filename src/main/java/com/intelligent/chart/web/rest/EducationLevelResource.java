package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.EducationLevel;
import com.intelligent.chart.service.EducationLevelService;import com.intelligent.chart.repository.EducationLevelRepository;
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
 * REST controller for managing EducationLevel.
 */
@RestController
@RequestMapping("/api")
public class EducationLevelResource {

    private final Logger log = LoggerFactory.getLogger(EducationLevelResource.class);

    @Inject
    private EducationLevelService educationLevelService;


    /**
     * POST  /education-levels : Create a new educationLevel.
     *
     * @param educationLevel the educationLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationLevel, or with status 400 (Bad Request) if the educationLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/education-levels")
    @Timed
    public ResponseEntity<EducationLevel> createEducationLevel(@RequestBody EducationLevel educationLevel) throws URISyntaxException {
        log.debug("REST request to save EducationLevel : {}", educationLevel);
        if (educationLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("educationLevel", "idexists", "A new educationLevel cannot already have an ID")).body(null);
        }
        EducationLevel result = educationLevelService.save(educationLevel);
        return ResponseEntity.created(new URI("/api/education-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("educationLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education-levels : Updates an existing educationLevel.
     *
     * @param educationLevel the educationLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationLevel,
     * or with status 400 (Bad Request) if the educationLevel is not valid,
     * or with status 500 (Internal Server Error) if the educationLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/education-levels")
    @Timed
    public ResponseEntity<EducationLevel> updateEducationLevel(@RequestBody EducationLevel educationLevel) throws URISyntaxException {
        log.debug("REST request to update EducationLevel : {}", educationLevel);
        if (educationLevel.getId() == null) {
            return createEducationLevel(educationLevel);
        }
        EducationLevel result = educationLevelService.save(educationLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("educationLevel", educationLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education-levels : get all the educationLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of educationLevels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/education-levels")
    @Timed
    public ResponseEntity<List<EducationLevel>> getAllEducationLevels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EducationLevels");
        Page<EducationLevel> page = educationLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/education-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /education-levels/:id : get the "id" educationLevel.
     *
     * @param id the id of the educationLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationLevel, or with status 404 (Not Found)
     */
    @GetMapping("/education-levels/{id}")
    @Timed
    public ResponseEntity<EducationLevel> getEducationLevel(@PathVariable Long id) {
        log.debug("REST request to get EducationLevel : {}", id);
        EducationLevel educationLevel = educationLevelService.findOne(id);
        return Optional.ofNullable(educationLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /education-levels/:id : delete the "id" educationLevel.
     *
     * @param id the id of the educationLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/education-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationLevel(@PathVariable Long id) {
        log.debug("REST request to delete EducationLevel : {}", id);
        educationLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("educationLevel", id.toString())).build();
    }

}
