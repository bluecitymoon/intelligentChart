package com.intelligent.chart.repository;

import com.intelligent.chart.domain.LawBusinessType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LawBusinessType entity.
 */
@SuppressWarnings("unused")
public interface LawBusinessTypeRepository extends JpaRepository<LawBusinessType,Long> {

}
