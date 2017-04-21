package com.intelligent.chart.repository;

import com.intelligent.chart.domain.MovieSuccessLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MovieSuccessLog entity.
 */
@SuppressWarnings("unused")
public interface MovieSuccessLogRepository extends JpaRepository<MovieSuccessLog,Long> {

    MovieSuccessLog findByDoubanId(String doubanId);

}
