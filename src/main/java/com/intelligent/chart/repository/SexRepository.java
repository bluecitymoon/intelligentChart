package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Sex;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sex entity.
 */
@SuppressWarnings("unused")
public interface SexRepository extends JpaRepository<Sex,Long> {
//    Page<Sex> findByPerson_Id(Long id, Pageable pageable);


}
