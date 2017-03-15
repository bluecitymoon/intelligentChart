package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonWechatArticle;
import com.intelligent.chart.repository.PersonWechatArticleRepository;
import com.intelligent.chart.service.PersonWechatArticleService;
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
 * REST controller for managing PersonWechatArticle.
 */
@RestController
@RequestMapping("/api")
public class PersonWechatArticleResource {

    private final Logger log = LoggerFactory.getLogger(PersonWechatArticleResource.class);

    @Inject
    private PersonWechatArticleService personWechatArticleService;

    @Inject
    private PersonWechatArticleRepository personWechatArticleRepository;

    @GetMapping("/person-wechat-articles/person/{id}")
    @Timed
    public ResponseEntity<List<PersonWechatArticle>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {

        Page<PersonWechatArticle> page = personWechatArticleRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-wechat-articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/person-wechat-articles/person/{id}/current")
    @Timed
    public ResponseEntity<PersonWechatArticle> getCurrentWechatArticle(@PathVariable Long id)
        throws URISyntaxException {

        List<PersonWechatArticle> personWechatArticle = personWechatArticleRepository.findByPerson_IdOrderByCreateDateDesc(id);

        if (personWechatArticle != null && !personWechatArticle.isEmpty()) {

            return new ResponseEntity<>(personWechatArticle.get(0), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * POST  /person-wechat-articles : Create a new personWechatArticle.
     *
     * @param personWechatArticle the personWechatArticle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personWechatArticle, or with status 400 (Bad Request) if the personWechatArticle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-wechat-articles")
    @Timed
    public ResponseEntity<PersonWechatArticle> createPersonWechatArticle(@RequestBody PersonWechatArticle personWechatArticle) throws URISyntaxException {
        log.debug("REST request to save PersonWechatArticle : {}", personWechatArticle);
        if (personWechatArticle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personWechatArticle", "idexists", "A new personWechatArticle cannot already have an ID")).body(null);
        }
        PersonWechatArticle result = personWechatArticleService.save(personWechatArticle);
        return ResponseEntity.created(new URI("/api/person-wechat-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personWechatArticle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-wechat-articles : Updates an existing personWechatArticle.
     *
     * @param personWechatArticle the personWechatArticle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personWechatArticle,
     * or with status 400 (Bad Request) if the personWechatArticle is not valid,
     * or with status 500 (Internal Server Error) if the personWechatArticle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-wechat-articles")
    @Timed
    public ResponseEntity<PersonWechatArticle> updatePersonWechatArticle(@RequestBody PersonWechatArticle personWechatArticle) throws URISyntaxException {
        log.debug("REST request to update PersonWechatArticle : {}", personWechatArticle);
        if (personWechatArticle.getId() == null) {
            return createPersonWechatArticle(personWechatArticle);
        }
        PersonWechatArticle result = personWechatArticleService.save(personWechatArticle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personWechatArticle", personWechatArticle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-wechat-articles : get all the personWechatArticles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personWechatArticles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-wechat-articles")
    @Timed
    public ResponseEntity<List<PersonWechatArticle>> getAllPersonWechatArticles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonWechatArticles");
        Page<PersonWechatArticle> page = personWechatArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-wechat-articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-wechat-articles/:id : get the "id" personWechatArticle.
     *
     * @param id the id of the personWechatArticle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personWechatArticle, or with status 404 (Not Found)
     */
    @GetMapping("/person-wechat-articles/{id}")
    @Timed
    public ResponseEntity<PersonWechatArticle> getPersonWechatArticle(@PathVariable Long id) {
        log.debug("REST request to get PersonWechatArticle : {}", id);
        PersonWechatArticle personWechatArticle = personWechatArticleService.findOne(id);
        return Optional.ofNullable(personWechatArticle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-wechat-articles/:id : delete the "id" personWechatArticle.
     *
     * @param id the id of the personWechatArticle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-wechat-articles/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonWechatArticle(@PathVariable Long id) {
        log.debug("REST request to delete PersonWechatArticle : {}", id);
        personWechatArticleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personWechatArticle", id.toString())).build();
    }

}
