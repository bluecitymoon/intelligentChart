package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.MediaType;
import com.intelligent.chart.service.MediaTypeService;
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
 * REST controller for managing MediaType.
 */
@RestController
@RequestMapping("/api")
public class MediaTypeResource {

    private final Logger log = LoggerFactory.getLogger(MediaTypeResource.class);
        
    @Inject
    private MediaTypeService mediaTypeService;

    /**
     * POST  /media-types : Create a new mediaType.
     *
     * @param mediaType the mediaType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaType, or with status 400 (Bad Request) if the mediaType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media-types")
    @Timed
    public ResponseEntity<MediaType> createMediaType(@RequestBody MediaType mediaType) throws URISyntaxException {
        log.debug("REST request to save MediaType : {}", mediaType);
        if (mediaType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mediaType", "idexists", "A new mediaType cannot already have an ID")).body(null);
        }
        MediaType result = mediaTypeService.save(mediaType);
        return ResponseEntity.created(new URI("/api/media-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mediaType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media-types : Updates an existing mediaType.
     *
     * @param mediaType the mediaType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaType,
     * or with status 400 (Bad Request) if the mediaType is not valid,
     * or with status 500 (Internal Server Error) if the mediaType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media-types")
    @Timed
    public ResponseEntity<MediaType> updateMediaType(@RequestBody MediaType mediaType) throws URISyntaxException {
        log.debug("REST request to update MediaType : {}", mediaType);
        if (mediaType.getId() == null) {
            return createMediaType(mediaType);
        }
        MediaType result = mediaTypeService.save(mediaType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mediaType", mediaType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media-types : get all the mediaTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mediaTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/media-types")
    @Timed
    public ResponseEntity<List<MediaType>> getAllMediaTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MediaTypes");
        Page<MediaType> page = mediaTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /media-types/:id : get the "id" mediaType.
     *
     * @param id the id of the mediaType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaType, or with status 404 (Not Found)
     */
    @GetMapping("/media-types/{id}")
    @Timed
    public ResponseEntity<MediaType> getMediaType(@PathVariable Long id) {
        log.debug("REST request to get MediaType : {}", id);
        MediaType mediaType = mediaTypeService.findOne(id);
        return Optional.ofNullable(mediaType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /media-types/:id : delete the "id" mediaType.
     *
     * @param id the id of the mediaType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMediaType(@PathVariable Long id) {
        log.debug("REST request to delete MediaType : {}", id);
        mediaTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mediaType", id.toString())).build();
    }

}
