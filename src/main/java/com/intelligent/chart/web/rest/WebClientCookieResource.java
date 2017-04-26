package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.WebClientCookie;
import com.intelligent.chart.service.WebClientCookieService;
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
 * REST controller for managing WebClientCookie.
 */
@RestController
@RequestMapping("/api")
public class WebClientCookieResource {

    private final Logger log = LoggerFactory.getLogger(WebClientCookieResource.class);
        
    @Inject
    private WebClientCookieService webClientCookieService;

    /**
     * POST  /web-client-cookies : Create a new webClientCookie.
     *
     * @param webClientCookie the webClientCookie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webClientCookie, or with status 400 (Bad Request) if the webClientCookie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-client-cookies")
    @Timed
    public ResponseEntity<WebClientCookie> createWebClientCookie(@RequestBody WebClientCookie webClientCookie) throws URISyntaxException {
        log.debug("REST request to save WebClientCookie : {}", webClientCookie);
        if (webClientCookie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("webClientCookie", "idexists", "A new webClientCookie cannot already have an ID")).body(null);
        }
        WebClientCookie result = webClientCookieService.save(webClientCookie);
        return ResponseEntity.created(new URI("/api/web-client-cookies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("webClientCookie", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-client-cookies : Updates an existing webClientCookie.
     *
     * @param webClientCookie the webClientCookie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webClientCookie,
     * or with status 400 (Bad Request) if the webClientCookie is not valid,
     * or with status 500 (Internal Server Error) if the webClientCookie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-client-cookies")
    @Timed
    public ResponseEntity<WebClientCookie> updateWebClientCookie(@RequestBody WebClientCookie webClientCookie) throws URISyntaxException {
        log.debug("REST request to update WebClientCookie : {}", webClientCookie);
        if (webClientCookie.getId() == null) {
            return createWebClientCookie(webClientCookie);
        }
        WebClientCookie result = webClientCookieService.save(webClientCookie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("webClientCookie", webClientCookie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-client-cookies : get all the webClientCookies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webClientCookies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/web-client-cookies")
    @Timed
    public ResponseEntity<List<WebClientCookie>> getAllWebClientCookies(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WebClientCookies");
        Page<WebClientCookie> page = webClientCookieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-client-cookies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-client-cookies/:id : get the "id" webClientCookie.
     *
     * @param id the id of the webClientCookie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webClientCookie, or with status 404 (Not Found)
     */
    @GetMapping("/web-client-cookies/{id}")
    @Timed
    public ResponseEntity<WebClientCookie> getWebClientCookie(@PathVariable Long id) {
        log.debug("REST request to get WebClientCookie : {}", id);
        WebClientCookie webClientCookie = webClientCookieService.findOne(id);
        return Optional.ofNullable(webClientCookie)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /web-client-cookies/:id : delete the "id" webClientCookie.
     *
     * @param id the id of the webClientCookie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-client-cookies/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebClientCookie(@PathVariable Long id) {
        log.debug("REST request to delete WebClientCookie : {}", id);
        webClientCookieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("webClientCookie", id.toString())).build();
    }

}
