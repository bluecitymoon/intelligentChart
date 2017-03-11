package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonSearchCount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonSearchCount entity.
 */
@SuppressWarnings("unused")
public interface PersonSearchCountRepository extends JpaRepository<PersonSearchCount,Long> {

    Page<PersonSearchCount> findByPerson_Id(Long id, Pageable pageable);
}
