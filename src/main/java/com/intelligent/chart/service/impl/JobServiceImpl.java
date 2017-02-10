package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.JobService;
import com.intelligent.chart.domain.Job;
import com.intelligent.chart.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Job.
 */
@Service
@Transactional
public class JobServiceImpl implements JobService{

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    
    @Inject
    private JobRepository jobRepository;

    /**
     * Save a job.
     *
     * @param job the entity to save
     * @return the persisted entity
     */
    public Job save(Job job) {
        log.debug("Request to save Job : {}", job);
        Job result = jobRepository.save(job);
        return result;
    }

    /**
     *  Get all the jobs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Job> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        Page<Job> result = jobRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one job by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Job findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        Job job = jobRepository.findOne(id);
        return job;
    }

    /**
     *  Delete the  job by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Job : {}", id);
        jobRepository.delete(id);
    }
}
