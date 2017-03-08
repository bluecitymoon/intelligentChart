package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.NetworkTexiCompany;
import com.intelligent.chart.service.NetworkTexiCompanyService;
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
 * REST controller for managing NetworkTexiCompany.
 */
@RestController
@RequestMapping("/api")
public class NetworkTexiCompanyResource {

    private final Logger log = LoggerFactory.getLogger(NetworkTexiCompanyResource.class);
        
    @Inject
    private NetworkTexiCompanyService networkTexiCompanyService;

    /**
     * POST  /network-texi-companies : Create a new networkTexiCompany.
     *
     * @param networkTexiCompany the networkTexiCompany to create
     * @return the ResponseEntity with status 201 (Created) and with body the new networkTexiCompany, or with status 400 (Bad Request) if the networkTexiCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/network-texi-companies")
    @Timed
    public ResponseEntity<NetworkTexiCompany> createNetworkTexiCompany(@RequestBody NetworkTexiCompany networkTexiCompany) throws URISyntaxException {
        log.debug("REST request to save NetworkTexiCompany : {}", networkTexiCompany);
        if (networkTexiCompany.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("networkTexiCompany", "idexists", "A new networkTexiCompany cannot already have an ID")).body(null);
        }
        NetworkTexiCompany result = networkTexiCompanyService.save(networkTexiCompany);
        return ResponseEntity.created(new URI("/api/network-texi-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("networkTexiCompany", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /network-texi-companies : Updates an existing networkTexiCompany.
     *
     * @param networkTexiCompany the networkTexiCompany to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated networkTexiCompany,
     * or with status 400 (Bad Request) if the networkTexiCompany is not valid,
     * or with status 500 (Internal Server Error) if the networkTexiCompany couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/network-texi-companies")
    @Timed
    public ResponseEntity<NetworkTexiCompany> updateNetworkTexiCompany(@RequestBody NetworkTexiCompany networkTexiCompany) throws URISyntaxException {
        log.debug("REST request to update NetworkTexiCompany : {}", networkTexiCompany);
        if (networkTexiCompany.getId() == null) {
            return createNetworkTexiCompany(networkTexiCompany);
        }
        NetworkTexiCompany result = networkTexiCompanyService.save(networkTexiCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("networkTexiCompany", networkTexiCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /network-texi-companies : get all the networkTexiCompanies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of networkTexiCompanies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/network-texi-companies")
    @Timed
    public ResponseEntity<List<NetworkTexiCompany>> getAllNetworkTexiCompanies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of NetworkTexiCompanies");
        Page<NetworkTexiCompany> page = networkTexiCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/network-texi-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /network-texi-companies/:id : get the "id" networkTexiCompany.
     *
     * @param id the id of the networkTexiCompany to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the networkTexiCompany, or with status 404 (Not Found)
     */
    @GetMapping("/network-texi-companies/{id}")
    @Timed
    public ResponseEntity<NetworkTexiCompany> getNetworkTexiCompany(@PathVariable Long id) {
        log.debug("REST request to get NetworkTexiCompany : {}", id);
        NetworkTexiCompany networkTexiCompany = networkTexiCompanyService.findOne(id);
        return Optional.ofNullable(networkTexiCompany)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /network-texi-companies/:id : delete the "id" networkTexiCompany.
     *
     * @param id the id of the networkTexiCompany to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/network-texi-companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteNetworkTexiCompany(@PathVariable Long id) {
        log.debug("REST request to delete NetworkTexiCompany : {}", id);
        networkTexiCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("networkTexiCompany", id.toString())).build();
    }

}
