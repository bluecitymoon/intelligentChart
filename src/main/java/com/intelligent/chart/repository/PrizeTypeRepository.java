package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PrizeType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrizeType entity.
 */
@SuppressWarnings("unused")
public interface PrizeTypeRepository extends JpaRepository<PrizeType,Long> {

}
