package com.intelligent.chart.repository;

import com.intelligent.chart.domain.EgeLevel;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EgeLevel entity.
 */
@SuppressWarnings("unused")
public interface EgeLevelRepository extends JpaRepository<EgeLevel,Long> {


}
