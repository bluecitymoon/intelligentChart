package com.intelligent.chart.repository;

import com.intelligent.chart.domain.EducationLevel;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EducationLevel entity.
 */
@SuppressWarnings("unused")
public interface EducationLevelRepository extends JpaRepository<EducationLevel,Long> {

}
