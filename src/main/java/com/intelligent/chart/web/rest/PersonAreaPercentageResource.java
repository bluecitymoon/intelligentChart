package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.PersonAreaPercentage;
import com.intelligent.chart.repository.PersonAreaPercentageRepository;
import com.intelligent.chart.service.PersonAreaPercentageService;
import com.intelligent.chart.vo.CommonChartData;
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
import javax.persistence.EntityManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonAreaPercentage.
 */
@RestController
@RequestMapping("/api")
public class PersonAreaPercentageResource {

    private final Logger log = LoggerFactory.getLogger(PersonAreaPercentageResource.class);

    @Inject
    private PersonAreaPercentageService personAreaPercentageService;

    @Inject
    private PersonAreaPercentageRepository personAreaPercentageRepository;

    @Inject
    private EntityManager entityManager;

    //SELECT sum(a.percentage), b.name FROM person_area_percentage a left join area_type b on a.area_type_id = b.id where person_id = 1 GROUP BY a.area_type_id
    @GetMapping("/person-area-percentages/person/total/{id}")
    @Timed
    public ResponseEntity<List<CommonChartData>> getTotalChartData(@PathVariable Long id)
        throws URISyntaxException {

        String sql = "SELECT sum(a.percentage) as x, b.name  as y FROM person_area_percentage a left join area_type b on a.area_type_id = b.id where person_id = " + id + " GROUP BY a.area_type_id";
        List<List<Object>> result =  entityManager.createNativeQuery(sql).getResultList();
        List<CommonChartData> transformedList = new ArrayList<>();
        for (List<Object> object: result) {

            Integer x = (Integer) object.get(0);
            String y = (String) object.get(1);

            CommonChartData commonChartData = new CommonChartData();
            commonChartData.setX(x);
            commonChartData.setY(y);
            transformedList.add(commonChartData);
        }
        return new ResponseEntity<>(transformedList, HttpStatus.OK);
    }

    @GetMapping("/person-area-percentages/person/{id}/with/type/{type}")
    @Timed
    public ResponseEntity<List<PersonAreaPercentage>> getAllPersonAreaPercentagesByPersonIdAndType(@PathVariable Long id, @PathVariable String type, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonAreaPercentages");
        Page<PersonAreaPercentage> page = personAreaPercentageRepository.findByPerson_IdAndMediaType_Identifier(id, type, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-area-percentages");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/person-area-percentages/person/{id}")
    @Timed
    public ResponseEntity<List<PersonAreaPercentage>> getAllPersonAreaPercentagesByPersonId(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonAreaPercentages");
        Page<PersonAreaPercentage> page = personAreaPercentageRepository.findByPerson_Id(id, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-area-percentages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /person-area-percentages : Create a new personAreaPercentage.
     *
     * @param personAreaPercentage the personAreaPercentage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personAreaPercentage, or with status 400 (Bad Request) if the personAreaPercentage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-area-percentages")
    @Timed
    public ResponseEntity<PersonAreaPercentage> createPersonAreaPercentage(@RequestBody PersonAreaPercentage personAreaPercentage) throws URISyntaxException {
        log.debug("REST request to save PersonAreaPercentage : {}", personAreaPercentage);
        if (personAreaPercentage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personAreaPercentage", "idexists", "A new personAreaPercentage cannot already have an ID")).body(null);
        }
        PersonAreaPercentage result = personAreaPercentageService.save(personAreaPercentage);
        return ResponseEntity.created(new URI("/api/person-area-percentages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personAreaPercentage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-area-percentages : Updates an existing personAreaPercentage.
     *
     * @param personAreaPercentage the personAreaPercentage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personAreaPercentage,
     * or with status 400 (Bad Request) if the personAreaPercentage is not valid,
     * or with status 500 (Internal Server Error) if the personAreaPercentage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-area-percentages")
    @Timed
    public ResponseEntity<PersonAreaPercentage> updatePersonAreaPercentage(@RequestBody PersonAreaPercentage personAreaPercentage) throws URISyntaxException {
        log.debug("REST request to update PersonAreaPercentage : {}", personAreaPercentage);
        if (personAreaPercentage.getId() == null) {
            return createPersonAreaPercentage(personAreaPercentage);
        }
        PersonAreaPercentage result = personAreaPercentageService.save(personAreaPercentage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personAreaPercentage", personAreaPercentage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-area-percentages : get all the personAreaPercentages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personAreaPercentages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/person-area-percentages")
    @Timed
    public ResponseEntity<List<PersonAreaPercentage>> getAllPersonAreaPercentages(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PersonAreaPercentages");
        Page<PersonAreaPercentage> page = personAreaPercentageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-area-percentages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-area-percentages/:id : get the "id" personAreaPercentage.
     *
     * @param id the id of the personAreaPercentage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personAreaPercentage, or with status 404 (Not Found)
     */
    @GetMapping("/person-area-percentages/{id}")
    @Timed
    public ResponseEntity<PersonAreaPercentage> getPersonAreaPercentage(@PathVariable Long id) {
        log.debug("REST request to get PersonAreaPercentage : {}", id);
        PersonAreaPercentage personAreaPercentage = personAreaPercentageService.findOne(id);
        return Optional.ofNullable(personAreaPercentage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /person-area-percentages/:id : delete the "id" personAreaPercentage.
     *
     * @param id the id of the personAreaPercentage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-area-percentages/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonAreaPercentage(@PathVariable Long id) {
        log.debug("REST request to delete PersonAreaPercentage : {}", id);
        personAreaPercentageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personAreaPercentage", id.toString())).build();
    }

}
