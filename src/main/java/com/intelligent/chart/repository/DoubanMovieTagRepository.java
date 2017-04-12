package com.intelligent.chart.repository;

import com.intelligent.chart.domain.DoubanMovieTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DoubanMovieTag entity.
 */
@SuppressWarnings("unused")
public interface DoubanMovieTagRepository extends JpaRepository<DoubanMovieTag,Long> {

    DoubanMovieTag findByName(String name);
}
