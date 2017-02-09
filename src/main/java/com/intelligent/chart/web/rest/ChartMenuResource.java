package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.ChartMenu;
import com.intelligent.chart.service.ChartMenuService;
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
 * REST controller for managing ChartMenu.
 */
@RestController
@RequestMapping("/api")
public class ChartMenuResource {

    private final Logger log = LoggerFactory.getLogger(ChartMenuResource.class);
        
    @Inject
    private ChartMenuService chartMenuService;

    /**
     * POST  /chart-menus : Create a new chartMenu.
     *
     * @param chartMenu the chartMenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartMenu, or with status 400 (Bad Request) if the chartMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-menus")
    @Timed
    public ResponseEntity<ChartMenu> createChartMenu(@RequestBody ChartMenu chartMenu) throws URISyntaxException {
        log.debug("REST request to save ChartMenu : {}", chartMenu);
        if (chartMenu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartMenu", "idexists", "A new chartMenu cannot already have an ID")).body(null);
        }
        ChartMenu result = chartMenuService.save(chartMenu);
        return ResponseEntity.created(new URI("/api/chart-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartMenu", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-menus : Updates an existing chartMenu.
     *
     * @param chartMenu the chartMenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartMenu,
     * or with status 400 (Bad Request) if the chartMenu is not valid,
     * or with status 500 (Internal Server Error) if the chartMenu couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-menus")
    @Timed
    public ResponseEntity<ChartMenu> updateChartMenu(@RequestBody ChartMenu chartMenu) throws URISyntaxException {
        log.debug("REST request to update ChartMenu : {}", chartMenu);
        if (chartMenu.getId() == null) {
            return createChartMenu(chartMenu);
        }
        ChartMenu result = chartMenuService.save(chartMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartMenu", chartMenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-menus : get all the chartMenus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chartMenus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/chart-menus")
    @Timed
    public ResponseEntity<List<ChartMenu>> getAllChartMenus(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ChartMenus");
        Page<ChartMenu> page = chartMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chart-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chart-menus/:id : get the "id" chartMenu.
     *
     * @param id the id of the chartMenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartMenu, or with status 404 (Not Found)
     */
    @GetMapping("/chart-menus/{id}")
    @Timed
    public ResponseEntity<ChartMenu> getChartMenu(@PathVariable Long id) {
        log.debug("REST request to get ChartMenu : {}", id);
        ChartMenu chartMenu = chartMenuService.findOne(id);
        return Optional.ofNullable(chartMenu)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-menus/:id : delete the "id" chartMenu.
     *
     * @param id the id of the chartMenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartMenu(@PathVariable Long id) {
        log.debug("REST request to delete ChartMenu : {}", id);
        chartMenuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartMenu", id.toString())).build();
    }

}
