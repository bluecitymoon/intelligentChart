package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonPopularity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonPopularity entity.
 */
@SuppressWarnings("unused")
public interface PersonPopularityRepository extends JpaRepository<PersonPopularity,Long> {

    Page<PersonPopularity> findByPerson_Id(Long id, Pageable pageable);
}
