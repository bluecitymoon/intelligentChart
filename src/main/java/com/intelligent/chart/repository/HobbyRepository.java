package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Hobby;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hobby entity.
 */
@SuppressWarnings("unused")
public interface HobbyRepository extends JpaRepository<Hobby,Long> {
//    Page<Hobby> findByPerson_Id(Long id, Pageable pageable);


}
