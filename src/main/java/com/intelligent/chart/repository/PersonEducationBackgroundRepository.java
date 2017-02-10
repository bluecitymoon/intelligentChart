package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonEducationBackground;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonEducationBackground entity.
 */
@SuppressWarnings("unused")
public interface PersonEducationBackgroundRepository extends JpaRepository<PersonEducationBackground,Long> {

}
