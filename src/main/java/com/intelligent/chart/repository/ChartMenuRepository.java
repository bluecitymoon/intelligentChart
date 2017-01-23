package com.intelligent.chart.repository;

import com.intelligent.chart.domain.ChartMenu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChartMenu entity.
 */
@SuppressWarnings("unused")
public interface ChartMenuRepository extends JpaRepository<ChartMenu,Long> {

}
