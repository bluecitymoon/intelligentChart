package com.intelligent.chart.service;

import com.intelligent.chart.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Job.
 */
public interface JobService {

    /**
     * Save a job.
     *
     * @param job the entity to save
     * @return the persisted entity
     */
    Job save(Job job);

    /**
     *  Get all the jobs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Job> findAll(Pageable pageable);

    /**
     *  Get the "id" job.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Job findOne(Long id);

    /**
     *  Delete the "id" job.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
