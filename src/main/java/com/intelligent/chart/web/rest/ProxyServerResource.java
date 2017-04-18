package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.service.ProxyServerService;
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
 * REST controller for managing ProxyServer.
 */
@RestController
@RequestMapping("/api")
public class ProxyServerResource {

    private final Logger log = LoggerFactory.getLogger(ProxyServerResource.class);
        
    @Inject
    private ProxyServerService proxyServerService;

    /**
     * POST  /proxy-servers : Create a new proxyServer.
     *
     * @param proxyServer the proxyServer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proxyServer, or with status 400 (Bad Request) if the proxyServer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proxy-servers")
    @Timed
    public ResponseEntity<ProxyServer> createProxyServer(@RequestBody ProxyServer proxyServer) throws URISyntaxException {
        log.debug("REST request to save ProxyServer : {}", proxyServer);
        if (proxyServer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proxyServer", "idexists", "A new proxyServer cannot already have an ID")).body(null);
        }
        ProxyServer result = proxyServerService.save(proxyServer);
        return ResponseEntity.created(new URI("/api/proxy-servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proxyServer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proxy-servers : Updates an existing proxyServer.
     *
     * @param proxyServer the proxyServer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proxyServer,
     * or with status 400 (Bad Request) if the proxyServer is not valid,
     * or with status 500 (Internal Server Error) if the proxyServer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proxy-servers")
    @Timed
    public ResponseEntity<ProxyServer> updateProxyServer(@RequestBody ProxyServer proxyServer) throws URISyntaxException {
        log.debug("REST request to update ProxyServer : {}", proxyServer);
        if (proxyServer.getId() == null) {
            return createProxyServer(proxyServer);
        }
        ProxyServer result = proxyServerService.save(proxyServer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proxyServer", proxyServer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proxy-servers : get all the proxyServers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of proxyServers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/proxy-servers")
    @Timed
    public ResponseEntity<List<ProxyServer>> getAllProxyServers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProxyServers");
        Page<ProxyServer> page = proxyServerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proxy-servers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proxy-servers/:id : get the "id" proxyServer.
     *
     * @param id the id of the proxyServer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proxyServer, or with status 404 (Not Found)
     */
    @GetMapping("/proxy-servers/{id}")
    @Timed
    public ResponseEntity<ProxyServer> getProxyServer(@PathVariable Long id) {
        log.debug("REST request to get ProxyServer : {}", id);
        ProxyServer proxyServer = proxyServerService.findOne(id);
        return Optional.ofNullable(proxyServer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proxy-servers/:id : delete the "id" proxyServer.
     *
     * @param id the id of the proxyServer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proxy-servers/{id}")
    @Timed
    public ResponseEntity<Void> deleteProxyServer(@PathVariable Long id) {
        log.debug("REST request to delete ProxyServer : {}", id);
        proxyServerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proxyServer", id.toString())).build();
    }

}
