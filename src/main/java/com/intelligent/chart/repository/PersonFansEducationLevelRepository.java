package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFansEducationLevel;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFansEducationLevel entity.
 */
@SuppressWarnings("unused")
public interface PersonFansEducationLevelRepository extends JpaRepository<PersonFansEducationLevel,Long> {
    Page<PersonFansEducationLevel> findByPerson_Id(Long id, Pageable pageable);


}
