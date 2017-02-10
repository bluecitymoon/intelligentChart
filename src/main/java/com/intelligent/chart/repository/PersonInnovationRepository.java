package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonInnovation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonInnovation entity.
 */
@SuppressWarnings("unused")
public interface PersonInnovationRepository extends JpaRepository<PersonInnovation,Long> {

}
