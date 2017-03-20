package com.intelligent.chart.repository;

import com.intelligent.chart.domain.EducationBackgroundType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EducationBackgroundType entity.
 */
@SuppressWarnings("unused")
public interface EducationBackgroundTypeRepository extends JpaRepository<EducationBackgroundType,Long> {

}
