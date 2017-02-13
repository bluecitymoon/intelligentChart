package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonInnovation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the PersonInnovation entity.
 */
@SuppressWarnings("unused")
public interface PersonInnovationRepository extends JpaRepository<PersonInnovation,Long> {

    Page<PersonInnovation> findByPerson_Id(Long id, Pageable pageable);
}
