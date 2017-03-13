package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFanDisbribution;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFanDisbribution entity.
 */
@SuppressWarnings("unused")
public interface PersonFanDisbributionRepository extends JpaRepository<PersonFanDisbribution,Long> {

    List<PersonFanDisbribution> findByPerson_Id(Long id);
}
