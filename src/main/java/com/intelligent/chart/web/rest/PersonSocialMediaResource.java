package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonSocialMedia;
import com.intelligent.chart.service.PersonSocialMediaService;
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
 * REST controller for managing PersonSocialMedia.
 */
@RestController
@RequestMapping("/api")
public class PersonSocialMediaResource {

    private final Logger log = LoggerFactory.getLogger(PersonSocialMediaResource.class);
        
    @Inject
    private PersonSocialMediaService personSocialMediaService;

    /**
     * POST  /person-social-medias : Create a new personSocialMedia.
     *
     * @param personSocialMedia the personSocialMedia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personSocialMedia, or with status 400 (Bad Request) if the personSocialMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-social-medias")
    @Timed
    public ResponseEntity<PersonSocialMedia> createPersonSocialMedia(@RequestBody PersonSocialMedia personSocialMedia) throws URISyntaxException {
        log.debug("REST request to save PersonSocialMedia : {}", personSocialMedia);
        if (personSocialMedia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personSocialMedia", "idexists", "A new personSocialMedia cannot already have an ID")).body(null);
        }
        PersonSocialMedia result = personSocialMediaService.save(personSocialMedia);
        return ResponseEntity.created(new URI("/api/person-social-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personSocialMedia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-social-medias : Updates an existing personSocialMedia.
     *
     * @param personSocialMedia the personSocialMedia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personSocialMedia,
     * or with status 400 (Bad Request) if the personSocialMedia is not valid,
     * or with status 500 (Internal Server Error) if the personSocialMedia couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-social-medias")
    @Timed
    public ResponseEntity<PersonSocialMedia> updatePersonSocialMedia(@RequestBody PersonSocialMedia personSocialMedia) throws URISyntaxException {
        log.debug("REST request to update PersonSocialMedia : {}", personSocialMedia);
        if (personSocialMedia.getId() == null) {
            return createPersonSocialMedia(personSocialMedia);
        }
        PersonSocialMedia result = personSocialMediaService.save(personSocialMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personSocialMedia", personSocialMedia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-social-medias : get all the personSocialMedias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personSocialMedias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-social-medias")
    @Timed
    public ResponseEntity<List<PersonSocialMedia>> getAllPersonSocialMedias(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonSocialMedias");
        Page<PersonSocialMedia> page = personSocialMediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-social-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-social-medias/:id : get the "id" personSocialMedia.
     *
     * @param id the id of the personSocialMedia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personSocialMedia, or with status 404 (Not Found)
     */
    @GetMapping("/person-social-medias/{id}")
    @Timed
    public ResponseEntity<PersonSocialMedia> getPersonSocialMedia(@PathVariable Long id) {
        log.debug("REST request to get PersonSocialMedia : {}", id);
        PersonSocialMedia personSocialMedia = personSocialMediaService.findOne(id);
        return Optional.ofNullable(personSocialMedia)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-social-medias/:id : delete the "id" personSocialMedia.
     *
     * @param id the id of the personSocialMedia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-social-medias/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonSocialMedia(@PathVariable Long id) {
        log.debug("REST request to delete PersonSocialMedia : {}", id);
        personSocialMediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personSocialMedia", id.toString())).build();
    }

}
