package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.SocialMediaType;
import com.intelligent.chart.service.SocialMediaTypeService;
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
 * REST controller for managing SocialMediaType.
 */
@RestController
@RequestMapping("/api")
public class SocialMediaTypeResource {

    private final Logger log = LoggerFactory.getLogger(SocialMediaTypeResource.class);
        
    @Inject
    private SocialMediaTypeService socialMediaTypeService;

    /**
     * POST  /social-media-types : Create a new socialMediaType.
     *
     * @param socialMediaType the socialMediaType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialMediaType, or with status 400 (Bad Request) if the socialMediaType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-media-types")
    @Timed
    public ResponseEntity<SocialMediaType> createSocialMediaType(@RequestBody SocialMediaType socialMediaType) throws URISyntaxException {
        log.debug("REST request to save SocialMediaType : {}", socialMediaType);
        if (socialMediaType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("socialMediaType", "idexists", "A new socialMediaType cannot already have an ID")).body(null);
        }
        SocialMediaType result = socialMediaTypeService.save(socialMediaType);
        return ResponseEntity.created(new URI("/api/social-media-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("socialMediaType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-media-types : Updates an existing socialMediaType.
     *
     * @param socialMediaType the socialMediaType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialMediaType,
     * or with status 400 (Bad Request) if the socialMediaType is not valid,
     * or with status 500 (Internal Server Error) if the socialMediaType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-media-types")
    @Timed
    public ResponseEntity<SocialMediaType> updateSocialMediaType(@RequestBody SocialMediaType socialMediaType) throws URISyntaxException {
        log.debug("REST request to update SocialMediaType : {}", socialMediaType);
        if (socialMediaType.getId() == null) {
            return createSocialMediaType(socialMediaType);
        }
        SocialMediaType result = socialMediaTypeService.save(socialMediaType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("socialMediaType", socialMediaType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-media-types : get all the socialMediaTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialMediaTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/social-media-types")
    @Timed
    public ResponseEntity<List<SocialMediaType>> getAllSocialMediaTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SocialMediaTypes");
        Page<SocialMediaType> page = socialMediaTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-media-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /social-media-types/:id : get the "id" socialMediaType.
     *
     * @param id the id of the socialMediaType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialMediaType, or with status 404 (Not Found)
     */
    @GetMapping("/social-media-types/{id}")
    @Timed
    public ResponseEntity<SocialMediaType> getSocialMediaType(@PathVariable Long id) {
        log.debug("REST request to get SocialMediaType : {}", id);
        SocialMediaType socialMediaType = socialMediaTypeService.findOne(id);
        return Optional.ofNullable(socialMediaType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /social-media-types/:id : delete the "id" socialMediaType.
     *
     * @param id the id of the socialMediaType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-media-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialMediaType(@PathVariable Long id) {
        log.debug("REST request to delete SocialMediaType : {}", id);
        socialMediaTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("socialMediaType", id.toString())).build();
    }

}
