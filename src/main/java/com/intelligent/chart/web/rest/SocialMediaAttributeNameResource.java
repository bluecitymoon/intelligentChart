package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.SocialMediaAttributeName;
import com.intelligent.chart.service.SocialMediaAttributeNameService;
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
 * REST controller for managing SocialMediaAttributeName.
 */
@RestController
@RequestMapping("/api")
public class SocialMediaAttributeNameResource {

    private final Logger log = LoggerFactory.getLogger(SocialMediaAttributeNameResource.class);
        
    @Inject
    private SocialMediaAttributeNameService socialMediaAttributeNameService;

    /**
     * POST  /social-media-attribute-names : Create a new socialMediaAttributeName.
     *
     * @param socialMediaAttributeName the socialMediaAttributeName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialMediaAttributeName, or with status 400 (Bad Request) if the socialMediaAttributeName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-media-attribute-names")
    @Timed
    public ResponseEntity<SocialMediaAttributeName> createSocialMediaAttributeName(@RequestBody SocialMediaAttributeName socialMediaAttributeName) throws URISyntaxException {
        log.debug("REST request to save SocialMediaAttributeName : {}", socialMediaAttributeName);
        if (socialMediaAttributeName.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("socialMediaAttributeName", "idexists", "A new socialMediaAttributeName cannot already have an ID")).body(null);
        }
        SocialMediaAttributeName result = socialMediaAttributeNameService.save(socialMediaAttributeName);
        return ResponseEntity.created(new URI("/api/social-media-attribute-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("socialMediaAttributeName", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-media-attribute-names : Updates an existing socialMediaAttributeName.
     *
     * @param socialMediaAttributeName the socialMediaAttributeName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialMediaAttributeName,
     * or with status 400 (Bad Request) if the socialMediaAttributeName is not valid,
     * or with status 500 (Internal Server Error) if the socialMediaAttributeName couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-media-attribute-names")
    @Timed
    public ResponseEntity<SocialMediaAttributeName> updateSocialMediaAttributeName(@RequestBody SocialMediaAttributeName socialMediaAttributeName) throws URISyntaxException {
        log.debug("REST request to update SocialMediaAttributeName : {}", socialMediaAttributeName);
        if (socialMediaAttributeName.getId() == null) {
            return createSocialMediaAttributeName(socialMediaAttributeName);
        }
        SocialMediaAttributeName result = socialMediaAttributeNameService.save(socialMediaAttributeName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("socialMediaAttributeName", socialMediaAttributeName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-media-attribute-names : get all the socialMediaAttributeNames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialMediaAttributeNames in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/social-media-attribute-names")
    @Timed
    public ResponseEntity<List<SocialMediaAttributeName>> getAllSocialMediaAttributeNames(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SocialMediaAttributeNames");
        Page<SocialMediaAttributeName> page = socialMediaAttributeNameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-media-attribute-names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /social-media-attribute-names/:id : get the "id" socialMediaAttributeName.
     *
     * @param id the id of the socialMediaAttributeName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialMediaAttributeName, or with status 404 (Not Found)
     */
    @GetMapping("/social-media-attribute-names/{id}")
    @Timed
    public ResponseEntity<SocialMediaAttributeName> getSocialMediaAttributeName(@PathVariable Long id) {
        log.debug("REST request to get SocialMediaAttributeName : {}", id);
        SocialMediaAttributeName socialMediaAttributeName = socialMediaAttributeNameService.findOne(id);
        return Optional.ofNullable(socialMediaAttributeName)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /social-media-attribute-names/:id : delete the "id" socialMediaAttributeName.
     *
     * @param id the id of the socialMediaAttributeName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-media-attribute-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialMediaAttributeName(@PathVariable Long id) {
        log.debug("REST request to delete SocialMediaAttributeName : {}", id);
        socialMediaAttributeNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("socialMediaAttributeName", id.toString())).build();
    }

}
