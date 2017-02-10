package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PopularityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopularityType entity.
 */
@SuppressWarnings("unused")
public interface PopularityTypeRepository extends JpaRepository<PopularityType,Long> {

}
