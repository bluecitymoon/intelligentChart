package com.intelligent.chart.repository;

import com.intelligent.chart.domain.ConnectionLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ConnectionLevel entity.
 */
@SuppressWarnings("unused")
public interface ConnectionLevelRepository extends JpaRepository<ConnectionLevel,Long> {

}
