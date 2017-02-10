package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PrizeLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrizeLevel entity.
 */
@SuppressWarnings("unused")
public interface PrizeLevelRepository extends JpaRepository<PrizeLevel,Long> {

}
