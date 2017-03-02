package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.WordCloud;
import com.intelligent.chart.service.WordCloudService;
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
 * REST controller for managing WordCloud.
 */
@RestController
@RequestMapping("/api")
public class WordCloudResource {

    private final Logger log = LoggerFactory.getLogger(WordCloudResource.class);
        
    @Inject
    private WordCloudService wordCloudService;

    /**
     * POST  /word-clouds : Create a new wordCloud.
     *
     * @param wordCloud the wordCloud to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wordCloud, or with status 400 (Bad Request) if the wordCloud has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/word-clouds")
    @Timed
    public ResponseEntity<WordCloud> createWordCloud(@RequestBody WordCloud wordCloud) throws URISyntaxException {
        log.debug("REST request to save WordCloud : {}", wordCloud);
        if (wordCloud.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wordCloud", "idexists", "A new wordCloud cannot already have an ID")).body(null);
        }
        WordCloud result = wordCloudService.save(wordCloud);
        return ResponseEntity.created(new URI("/api/word-clouds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wordCloud", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /word-clouds : Updates an existing wordCloud.
     *
     * @param wordCloud the wordCloud to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wordCloud,
     * or with status 400 (Bad Request) if the wordCloud is not valid,
     * or with status 500 (Internal Server Error) if the wordCloud couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/word-clouds")
    @Timed
    public ResponseEntity<WordCloud> updateWordCloud(@RequestBody WordCloud wordCloud) throws URISyntaxException {
        log.debug("REST request to update WordCloud : {}", wordCloud);
        if (wordCloud.getId() == null) {
            return createWordCloud(wordCloud);
        }
        WordCloud result = wordCloudService.save(wordCloud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wordCloud", wordCloud.getId().toString()))
            .body(result);
    }

    /**
     * GET  /word-clouds : get all the wordClouds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wordClouds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/word-clouds")
    @Timed
    public ResponseEntity<List<WordCloud>> getAllWordClouds(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WordClouds");
        Page<WordCloud> page = wordCloudService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/word-clouds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /word-clouds/:id : get the "id" wordCloud.
     *
     * @param id the id of the wordCloud to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wordCloud, or with status 404 (Not Found)
     */
    @GetMapping("/word-clouds/{id}")
    @Timed
    public ResponseEntity<WordCloud> getWordCloud(@PathVariable Long id) {
        log.debug("REST request to get WordCloud : {}", id);
        WordCloud wordCloud = wordCloudService.findOne(id);
        return Optional.ofNullable(wordCloud)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /word-clouds/:id : delete the "id" wordCloud.
     *
     * @param id the id of the wordCloud to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/word-clouds/{id}")
    @Timed
    public ResponseEntity<Void> deleteWordCloud(@PathVariable Long id) {
        log.debug("REST request to delete WordCloud : {}", id);
        wordCloudService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wordCloud", id.toString())).build();
    }

}
