package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.CreditCardActivityType;
import com.intelligent.chart.service.CreditCardActivityTypeService;
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
 * REST controller for managing CreditCardActivityType.
 */
@RestController
@RequestMapping("/api")
public class CreditCardActivityTypeResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardActivityTypeResource.class);
        
    @Inject
    private CreditCardActivityTypeService creditCardActivityTypeService;

    /**
     * POST  /credit-card-activity-types : Create a new creditCardActivityType.
     *
     * @param creditCardActivityType the creditCardActivityType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditCardActivityType, or with status 400 (Bad Request) if the creditCardActivityType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-card-activity-types")
    @Timed
    public ResponseEntity<CreditCardActivityType> createCreditCardActivityType(@RequestBody CreditCardActivityType creditCardActivityType) throws URISyntaxException {
        log.debug("REST request to save CreditCardActivityType : {}", creditCardActivityType);
        if (creditCardActivityType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("creditCardActivityType", "idexists", "A new creditCardActivityType cannot already have an ID")).body(null);
        }
        CreditCardActivityType result = creditCardActivityTypeService.save(creditCardActivityType);
        return ResponseEntity.created(new URI("/api/credit-card-activity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("creditCardActivityType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-card-activity-types : Updates an existing creditCardActivityType.
     *
     * @param creditCardActivityType the creditCardActivityType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditCardActivityType,
     * or with status 400 (Bad Request) if the creditCardActivityType is not valid,
     * or with status 500 (Internal Server Error) if the creditCardActivityType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-card-activity-types")
    @Timed
    public ResponseEntity<CreditCardActivityType> updateCreditCardActivityType(@RequestBody CreditCardActivityType creditCardActivityType) throws URISyntaxException {
        log.debug("REST request to update CreditCardActivityType : {}", creditCardActivityType);
        if (creditCardActivityType.getId() == null) {
            return createCreditCardActivityType(creditCardActivityType);
        }
        CreditCardActivityType result = creditCardActivityTypeService.save(creditCardActivityType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("creditCardActivityType", creditCardActivityType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-card-activity-types : get all the creditCardActivityTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of creditCardActivityTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/credit-card-activity-types")
    @Timed
    public ResponseEntity<List<CreditCardActivityType>> getAllCreditCardActivityTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CreditCardActivityTypes");
        Page<CreditCardActivityType> page = creditCardActivityTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/credit-card-activity-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /credit-card-activity-types/:id : get the "id" creditCardActivityType.
     *
     * @param id the id of the creditCardActivityType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditCardActivityType, or with status 404 (Not Found)
     */
    @GetMapping("/credit-card-activity-types/{id}")
    @Timed
    public ResponseEntity<CreditCardActivityType> getCreditCardActivityType(@PathVariable Long id) {
        log.debug("REST request to get CreditCardActivityType : {}", id);
        CreditCardActivityType creditCardActivityType = creditCardActivityTypeService.findOne(id);
        return Optional.ofNullable(creditCardActivityType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /credit-card-activity-types/:id : delete the "id" creditCardActivityType.
     *
     * @param id the id of the creditCardActivityType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-card-activity-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCreditCardActivityType(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardActivityType : {}", id);
        creditCardActivityTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("creditCardActivityType", id.toString())).build();
    }

}
