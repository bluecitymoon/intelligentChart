package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.FansPurchasingSection;
import com.intelligent.chart.service.FansPurchasingSectionService;import com.intelligent.chart.repository.FansPurchasingSectionRepository;
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
 * REST controller for managing FansPurchasingSection.
 */
@RestController
@RequestMapping("/api")
public class FansPurchasingSectionResource {

    private final Logger log = LoggerFactory.getLogger(FansPurchasingSectionResource.class);

    @Inject
    private FansPurchasingSectionService fansPurchasingSectionService;

    @Inject
    private FansPurchasingSectionRepository fansPurchasingSectionRepository;


    /**
     * POST  /fans-purchasing-sections : Create a new fansPurchasingSection.
     *
     * @param fansPurchasingSection the fansPurchasingSection to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fansPurchasingSection, or with status 400 (Bad Request) if the fansPurchasingSection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fans-purchasing-sections")
    @Timed
    public ResponseEntity<FansPurchasingSection> createFansPurchasingSection(@RequestBody FansPurchasingSection fansPurchasingSection) throws URISyntaxException {
        log.debug("REST request to save FansPurchasingSection : {}", fansPurchasingSection);
        if (fansPurchasingSection.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fansPurchasingSection", "idexists", "A new fansPurchasingSection cannot already have an ID")).body(null);
        }
        FansPurchasingSection result = fansPurchasingSectionService.save(fansPurchasingSection);
        return ResponseEntity.created(new URI("/api/fans-purchasing-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fansPurchasingSection", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fans-purchasing-sections : Updates an existing fansPurchasingSection.
     *
     * @param fansPurchasingSection the fansPurchasingSection to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fansPurchasingSection,
     * or with status 400 (Bad Request) if the fansPurchasingSection is not valid,
     * or with status 500 (Internal Server Error) if the fansPurchasingSection couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fans-purchasing-sections")
    @Timed
    public ResponseEntity<FansPurchasingSection> updateFansPurchasingSection(@RequestBody FansPurchasingSection fansPurchasingSection) throws URISyntaxException {
        log.debug("REST request to update FansPurchasingSection : {}", fansPurchasingSection);
        if (fansPurchasingSection.getId() == null) {
            return createFansPurchasingSection(fansPurchasingSection);
        }
        FansPurchasingSection result = fansPurchasingSectionService.save(fansPurchasingSection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fansPurchasingSection", fansPurchasingSection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fans-purchasing-sections : get all the fansPurchasingSections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fansPurchasingSections in body
     */
    @GetMapping("/fans-purchasing-sections")
    @Timed
    public List<FansPurchasingSection> getAllFansPurchasingSections() {
        log.debug("REST request to get all FansPurchasingSections");
        return fansPurchasingSectionService.findAll();
    }

    /**
     * GET  /fans-purchasing-sections/:id : get the "id" fansPurchasingSection.
     *
     * @param id the id of the fansPurchasingSection to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fansPurchasingSection, or with status 404 (Not Found)
     */
    @GetMapping("/fans-purchasing-sections/{id}")
    @Timed
    public ResponseEntity<FansPurchasingSection> getFansPurchasingSection(@PathVariable Long id) {
        log.debug("REST request to get FansPurchasingSection : {}", id);
        FansPurchasingSection fansPurchasingSection = fansPurchasingSectionService.findOne(id);
        return Optional.ofNullable(fansPurchasingSection)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fans-purchasing-sections/:id : delete the "id" fansPurchasingSection.
     *
     * @param id the id of the fansPurchasingSection to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fans-purchasing-sections/{id}")
    @Timed
    public ResponseEntity<Void> deleteFansPurchasingSection(@PathVariable Long id) {
        log.debug("REST request to delete FansPurchasingSection : {}", id);
        fansPurchasingSectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fansPurchasingSection", id.toString())).build();
    }

}
