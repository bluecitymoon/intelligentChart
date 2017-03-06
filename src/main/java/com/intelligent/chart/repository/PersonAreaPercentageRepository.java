package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonAreaPercentage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonAreaPercentage entity.
 */
@SuppressWarnings("unused")
public interface PersonAreaPercentageRepository extends JpaRepository<PersonAreaPercentage,Long> {

    Page<PersonAreaPercentage> findByPerson_Id(Long id, Pageable pageable);

    Page<PersonAreaPercentage> findByPerson_IdAndMediaType_Identifier(Long id, String type, Pageable pageable);
}
