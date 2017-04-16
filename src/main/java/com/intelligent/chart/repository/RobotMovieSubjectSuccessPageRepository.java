package com.intelligent.chart.repository;

import com.intelligent.chart.domain.RobotMovieSubjectSuccessPage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RobotMovieSubjectSuccessPage entity.
 */
@SuppressWarnings("unused")
public interface RobotMovieSubjectSuccessPageRepository extends JpaRepository<RobotMovieSubjectSuccessPage,Long> {

    RobotMovieSubjectSuccessPage findByPageNumberAndTag(int pageNumber, String tag);

}
