package com.intelligent.chart.repository;

import com.intelligent.chart.domain.DoubleMovieSubject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DoubleMovieSubject entity.
 */
@SuppressWarnings("unused")
public interface DoubleMovieSubjectRepository extends JpaRepository<DoubleMovieSubject,Long> {

}
