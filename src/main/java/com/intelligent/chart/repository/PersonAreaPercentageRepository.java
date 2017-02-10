package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonAreaPercentage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonAreaPercentage entity.
 */
@SuppressWarnings("unused")
public interface PersonAreaPercentageRepository extends JpaRepository<PersonAreaPercentage,Long> {

}
