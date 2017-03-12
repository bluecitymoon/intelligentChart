package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.Sex;
import com.intelligent.chart.service.SexService;import com.intelligent.chart.repository.SexRepository;
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
 * REST controller for managing Sex.
 */
@RestController
@RequestMapping("/api")
public class SexResource {

    private final Logger log = LoggerFactory.getLogger(SexResource.class);

    @Inject
    private SexService sexService;


    /**
     * POST  /sexes : Create a new sex.
     *
     * @param sex the sex to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sex, or with status 400 (Bad Request) if the sex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sexes")
    @Timed
    public ResponseEntity<Sex> createSex(@RequestBody Sex sex) throws URISyntaxException {
        log.debug("REST request to save Sex : {}", sex);
        if (sex.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sex", "idexists", "A new sex cannot already have an ID")).body(null);
        }
        Sex result = sexService.save(sex);
        return ResponseEntity.created(new URI("/api/sexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sex", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sexes : Updates an existing sex.
     *
     * @param sex the sex to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sex,
     * or with status 400 (Bad Request) if the sex is not valid,
     * or with status 500 (Internal Server Error) if the sex couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sexes")
    @Timed
    public ResponseEntity<Sex> updateSex(@RequestBody Sex sex) throws URISyntaxException {
        log.debug("REST request to update Sex : {}", sex);
        if (sex.getId() == null) {
            return createSex(sex);
        }
        Sex result = sexService.save(sex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sex", sex.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sexes : get all the sexes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sexes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sexes")
    @Timed
    public ResponseEntity<List<Sex>> getAllSexes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sexes");
        Page<Sex> page = sexService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sexes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sexes/:id : get the "id" sex.
     *
     * @param id the id of the sex to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sex, or with status 404 (Not Found)
     */
    @GetMapping("/sexes/{id}")
    @Timed
    public ResponseEntity<Sex> getSex(@PathVariable Long id) {
        log.debug("REST request to get Sex : {}", id);
        Sex sex = sexService.findOne(id);
        return Optional.ofNullable(sex)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sexes/:id : delete the "id" sex.
     *
     * @param id the id of the sex to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sexes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSex(@PathVariable Long id) {
        log.debug("REST request to delete Sex : {}", id);
        sexService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sex", id.toString())).build();
    }

}
