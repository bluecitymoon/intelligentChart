package com.intelligent.chart.repository;

import com.intelligent.chart.domain.MovieCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MovieCategory entity.
 */
@SuppressWarnings("unused")
public interface MovieCategoryRepository extends JpaRepository<MovieCategory,Long> {

}
