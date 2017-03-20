package com.intelligent.chart.repository;

import com.intelligent.chart.domain.InnovationType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InnovationType entity.
 */
@SuppressWarnings("unused")
public interface InnovationTypeRepository extends JpaRepository<InnovationType,Long> {

}
