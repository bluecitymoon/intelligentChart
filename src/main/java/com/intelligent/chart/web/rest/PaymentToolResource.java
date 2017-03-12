package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PaymentTool;
import com.intelligent.chart.service.PaymentToolService;import com.intelligent.chart.repository.PaymentToolRepository;
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
 * REST controller for managing PaymentTool.
 */
@RestController
@RequestMapping("/api")
public class PaymentToolResource {

    private final Logger log = LoggerFactory.getLogger(PaymentToolResource.class);

    @Inject
    private PaymentToolService paymentToolService;

    /**
     * POST  /payment-tools : Create a new paymentTool.
     *
     * @param paymentTool the paymentTool to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentTool, or with status 400 (Bad Request) if the paymentTool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-tools")
    @Timed
    public ResponseEntity<PaymentTool> createPaymentTool(@RequestBody PaymentTool paymentTool) throws URISyntaxException {
        log.debug("REST request to save PaymentTool : {}", paymentTool);
        if (paymentTool.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("paymentTool", "idexists", "A new paymentTool cannot already have an ID")).body(null);
        }
        PaymentTool result = paymentToolService.save(paymentTool);
        return ResponseEntity.created(new URI("/api/payment-tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paymentTool", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-tools : Updates an existing paymentTool.
     *
     * @param paymentTool the paymentTool to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentTool,
     * or with status 400 (Bad Request) if the paymentTool is not valid,
     * or with status 500 (Internal Server Error) if the paymentTool couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-tools")
    @Timed
    public ResponseEntity<PaymentTool> updatePaymentTool(@RequestBody PaymentTool paymentTool) throws URISyntaxException {
        log.debug("REST request to update PaymentTool : {}", paymentTool);
        if (paymentTool.getId() == null) {
            return createPaymentTool(paymentTool);
        }
        PaymentTool result = paymentToolService.save(paymentTool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paymentTool", paymentTool.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-tools : get all the paymentTools.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTools in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/payment-tools")
    @Timed
    public ResponseEntity<List<PaymentTool>> getAllPaymentTools(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PaymentTools");
        Page<PaymentTool> page = paymentToolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-tools");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-tools/:id : get the "id" paymentTool.
     *
     * @param id the id of the paymentTool to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentTool, or with status 404 (Not Found)
     */
    @GetMapping("/payment-tools/{id}")
    @Timed
    public ResponseEntity<PaymentTool> getPaymentTool(@PathVariable Long id) {
        log.debug("REST request to get PaymentTool : {}", id);
        PaymentTool paymentTool = paymentToolService.findOne(id);
        return Optional.ofNullable(paymentTool)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment-tools/:id : delete the "id" paymentTool.
     *
     * @param id the id of the paymentTool to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-tools/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentTool(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTool : {}", id);
        paymentToolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paymentTool", id.toString())).build();
    }

}
