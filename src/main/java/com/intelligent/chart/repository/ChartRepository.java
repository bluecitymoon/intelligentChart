package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Chart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Chart entity.
 */
@SuppressWarnings("unused")
public interface ChartRepository extends JpaRepository<Chart,Long> {

}
