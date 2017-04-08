package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Robot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Robot entity.
 */
@SuppressWarnings("unused")
public interface RobotRepository extends JpaRepository<Robot,Long> {

}
