package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PrizeGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrizeGroup entity.
 */
@SuppressWarnings("unused")
public interface PrizeGroupRepository extends JpaRepository<PrizeGroup,Long> {

}
