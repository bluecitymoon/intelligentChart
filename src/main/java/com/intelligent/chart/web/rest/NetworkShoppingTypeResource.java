package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.NetworkShoppingType;
import com.intelligent.chart.service.NetworkShoppingTypeService;
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
 * REST controller for managing NetworkShoppingType.
 */
@RestController
@RequestMapping("/api")
public class NetworkShoppingTypeResource {

    private final Logger log = LoggerFactory.getLogger(NetworkShoppingTypeResource.class);
        
    @Inject
    private NetworkShoppingTypeService networkShoppingTypeService;

    /**
     * POST  /network-shopping-types : Create a new networkShoppingType.
     *
     * @param networkShoppingType the networkShoppingType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new networkShoppingType, or with status 400 (Bad Request) if the networkShoppingType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/network-shopping-types")
    @Timed
    public ResponseEntity<NetworkShoppingType> createNetworkShoppingType(@RequestBody NetworkShoppingType networkShoppingType) throws URISyntaxException {
        log.debug("REST request to save NetworkShoppingType : {}", networkShoppingType);
        if (networkShoppingType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("networkShoppingType", "idexists", "A new networkShoppingType cannot already have an ID")).body(null);
        }
        NetworkShoppingType result = networkShoppingTypeService.save(networkShoppingType);
        return ResponseEntity.created(new URI("/api/network-shopping-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("networkShoppingType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /network-shopping-types : Updates an existing networkShoppingType.
     *
     * @param networkShoppingType the networkShoppingType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated networkShoppingType,
     * or with status 400 (Bad Request) if the networkShoppingType is not valid,
     * or with status 500 (Internal Server Error) if the networkShoppingType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/network-shopping-types")
    @Timed
    public ResponseEntity<NetworkShoppingType> updateNetworkShoppingType(@RequestBody NetworkShoppingType networkShoppingType) throws URISyntaxException {
        log.debug("REST request to update NetworkShoppingType : {}", networkShoppingType);
        if (networkShoppingType.getId() == null) {
            return createNetworkShoppingType(networkShoppingType);
        }
        NetworkShoppingType result = networkShoppingTypeService.save(networkShoppingType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("networkShoppingType", networkShoppingType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /network-shopping-types : get all the networkShoppingTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of networkShoppingTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/network-shopping-types")
    @Timed
    public ResponseEntity<List<NetworkShoppingType>> getAllNetworkShoppingTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of NetworkShoppingTypes");
        Page<NetworkShoppingType> page = networkShoppingTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/network-shopping-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /network-shopping-types/:id : get the "id" networkShoppingType.
     *
     * @param id the id of the networkShoppingType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the networkShoppingType, or with status 404 (Not Found)
     */
    @GetMapping("/network-shopping-types/{id}")
    @Timed
    public ResponseEntity<NetworkShoppingType> getNetworkShoppingType(@PathVariable Long id) {
        log.debug("REST request to get NetworkShoppingType : {}", id);
        NetworkShoppingType networkShoppingType = networkShoppingTypeService.findOne(id);
        return Optional.ofNullable(networkShoppingType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /network-shopping-types/:id : delete the "id" networkShoppingType.
     *
     * @param id the id of the networkShoppingType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/network-shopping-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteNetworkShoppingType(@PathVariable Long id) {
        log.debug("REST request to delete NetworkShoppingType : {}", id);
        networkShoppingTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("networkShoppingType", id.toString())).build();
    }

}
