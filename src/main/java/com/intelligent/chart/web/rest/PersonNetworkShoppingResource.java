package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonNetworkShopping;
import com.intelligent.chart.service.PersonNetworkShoppingService;
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
 * REST controller for managing PersonNetworkShopping.
 */
@RestController
@RequestMapping("/api")
public class PersonNetworkShoppingResource {

    private final Logger log = LoggerFactory.getLogger(PersonNetworkShoppingResource.class);
        
    @Inject
    private PersonNetworkShoppingService personNetworkShoppingService;

    /**
     * POST  /person-network-shoppings : Create a new personNetworkShopping.
     *
     * @param personNetworkShopping the personNetworkShopping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personNetworkShopping, or with status 400 (Bad Request) if the personNetworkShopping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-network-shoppings")
    @Timed
    public ResponseEntity<PersonNetworkShopping> createPersonNetworkShopping(@RequestBody PersonNetworkShopping personNetworkShopping) throws URISyntaxException {
        log.debug("REST request to save PersonNetworkShopping : {}", personNetworkShopping);
        if (personNetworkShopping.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personNetworkShopping", "idexists", "A new personNetworkShopping cannot already have an ID")).body(null);
        }
        PersonNetworkShopping result = personNetworkShoppingService.save(personNetworkShopping);
        return ResponseEntity.created(new URI("/api/person-network-shoppings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personNetworkShopping", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-network-shoppings : Updates an existing personNetworkShopping.
     *
     * @param personNetworkShopping the personNetworkShopping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personNetworkShopping,
     * or with status 400 (Bad Request) if the personNetworkShopping is not valid,
     * or with status 500 (Internal Server Error) if the personNetworkShopping couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-network-shoppings")
    @Timed
    public ResponseEntity<PersonNetworkShopping> updatePersonNetworkShopping(@RequestBody PersonNetworkShopping personNetworkShopping) throws URISyntaxException {
        log.debug("REST request to update PersonNetworkShopping : {}", personNetworkShopping);
        if (personNetworkShopping.getId() == null) {
            return createPersonNetworkShopping(personNetworkShopping);
        }
        PersonNetworkShopping result = personNetworkShoppingService.save(personNetworkShopping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personNetworkShopping", personNetworkShopping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-network-shoppings : get all the personNetworkShoppings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personNetworkShoppings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-network-shoppings")
    @Timed
    public ResponseEntity<List<PersonNetworkShopping>> getAllPersonNetworkShoppings(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonNetworkShoppings");
        Page<PersonNetworkShopping> page = personNetworkShoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-network-shoppings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-network-shoppings/:id : get the "id" personNetworkShopping.
     *
     * @param id the id of the personNetworkShopping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personNetworkShopping, or with status 404 (Not Found)
     */
    @GetMapping("/person-network-shoppings/{id}")
    @Timed
    public ResponseEntity<PersonNetworkShopping> getPersonNetworkShopping(@PathVariable Long id) {
        log.debug("REST request to get PersonNetworkShopping : {}", id);
        PersonNetworkShopping personNetworkShopping = personNetworkShoppingService.findOne(id);
        return Optional.ofNullable(personNetworkShopping)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-network-shoppings/:id : delete the "id" personNetworkShopping.
     *
     * @param id the id of the personNetworkShopping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-network-shoppings/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonNetworkShopping(@PathVariable Long id) {
        log.debug("REST request to delete PersonNetworkShopping : {}", id);
        personNetworkShoppingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personNetworkShopping", id.toString())).build();
    }

}
