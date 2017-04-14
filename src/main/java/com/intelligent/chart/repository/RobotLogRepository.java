package com.intelligent.chart.repository;

import com.intelligent.chart.domain.RobotLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RobotLog entity.
 */
@SuppressWarnings("unused")
public interface RobotLogRepository extends JpaRepository<RobotLog,Long> {

}
