package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.service.DoubanMovieTagService;
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
 * REST controller for managing DoubanMovieTag.
 */
@RestController
@RequestMapping("/api")
public class DoubanMovieTagResource {

    private final Logger log = LoggerFactory.getLogger(DoubanMovieTagResource.class);
        
    @Inject
    private DoubanMovieTagService doubanMovieTagService;

    /**
     * POST  /douban-movie-tags : Create a new doubanMovieTag.
     *
     * @param doubanMovieTag the doubanMovieTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doubanMovieTag, or with status 400 (Bad Request) if the doubanMovieTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/douban-movie-tags")
    @Timed
    public ResponseEntity<DoubanMovieTag> createDoubanMovieTag(@RequestBody DoubanMovieTag doubanMovieTag) throws URISyntaxException {
        log.debug("REST request to save DoubanMovieTag : {}", doubanMovieTag);
        if (doubanMovieTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("doubanMovieTag", "idexists", "A new doubanMovieTag cannot already have an ID")).body(null);
        }
        DoubanMovieTag result = doubanMovieTagService.save(doubanMovieTag);
        return ResponseEntity.created(new URI("/api/douban-movie-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("doubanMovieTag", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /douban-movie-tags : Updates an existing doubanMovieTag.
     *
     * @param doubanMovieTag the doubanMovieTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doubanMovieTag,
     * or with status 400 (Bad Request) if the doubanMovieTag is not valid,
     * or with status 500 (Internal Server Error) if the doubanMovieTag couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/douban-movie-tags")
    @Timed
    public ResponseEntity<DoubanMovieTag> updateDoubanMovieTag(@RequestBody DoubanMovieTag doubanMovieTag) throws URISyntaxException {
        log.debug("REST request to update DoubanMovieTag : {}", doubanMovieTag);
        if (doubanMovieTag.getId() == null) {
            return createDoubanMovieTag(doubanMovieTag);
        }
        DoubanMovieTag result = doubanMovieTagService.save(doubanMovieTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("doubanMovieTag", doubanMovieTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /douban-movie-tags : get all the doubanMovieTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doubanMovieTags in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/douban-movie-tags")
    @Timed
    public ResponseEntity<List<DoubanMovieTag>> getAllDoubanMovieTags(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DoubanMovieTags");
        Page<DoubanMovieTag> page = doubanMovieTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/douban-movie-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /douban-movie-tags/:id : get the "id" doubanMovieTag.
     *
     * @param id the id of the doubanMovieTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doubanMovieTag, or with status 404 (Not Found)
     */
    @GetMapping("/douban-movie-tags/{id}")
    @Timed
    public ResponseEntity<DoubanMovieTag> getDoubanMovieTag(@PathVariable Long id) {
        log.debug("REST request to get DoubanMovieTag : {}", id);
        DoubanMovieTag doubanMovieTag = doubanMovieTagService.findOne(id);
        return Optional.ofNullable(doubanMovieTag)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /douban-movie-tags/:id : delete the "id" doubanMovieTag.
     *
     * @param id the id of the doubanMovieTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/douban-movie-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoubanMovieTag(@PathVariable Long id) {
        log.debug("REST request to delete DoubanMovieTag : {}", id);
        doubanMovieTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("doubanMovieTag", id.toString())).build();
    }

}
