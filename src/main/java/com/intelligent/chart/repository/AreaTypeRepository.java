package com.intelligent.chart.repository;

import com.intelligent.chart.domain.AreaType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AreaType entity.
 */
@SuppressWarnings("unused")
public interface AreaTypeRepository extends JpaRepository<AreaType,Long> {

    AreaType findByName(String name);

}
