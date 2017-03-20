package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonMediaShowUpCount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonMediaShowUpCount entity.
 */
@SuppressWarnings("unused")
public interface PersonMediaShowUpCountRepository extends JpaRepository<PersonMediaShowUpCount,Long> {
    Page<PersonMediaShowUpCount> findByPerson_Id(Long id, Pageable pageable);


}
