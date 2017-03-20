package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Region;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
public interface RegionRepository extends JpaRepository<Region,Long> {

}
