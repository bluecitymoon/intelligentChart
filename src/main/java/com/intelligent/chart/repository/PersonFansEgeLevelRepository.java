package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFansEgeLevel;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFansEgeLevel entity.
 */
@SuppressWarnings("unused")
public interface PersonFansEgeLevelRepository extends JpaRepository<PersonFansEgeLevel,Long> {
    Page<PersonFansEgeLevel> findByPerson_Id(Long id, Pageable pageable);


}
