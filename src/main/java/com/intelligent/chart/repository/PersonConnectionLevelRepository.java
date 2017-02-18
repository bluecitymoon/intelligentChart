package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonConnectionLevel;

import com.intelligent.chart.domain.PersonEducationBackground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonConnectionLevel entity.
 */
@SuppressWarnings("unused")
public interface PersonConnectionLevelRepository extends JpaRepository<PersonConnectionLevel,Long> {

    Page<PersonConnectionLevel> findByPerson_Id(Long id, Pageable pageable);
}