package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Job;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Job entity.
 */
@SuppressWarnings("unused")
public interface JobRepository extends JpaRepository<Job,Long> {

}
