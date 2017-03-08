package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonEndorsement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonEndorsement entity.
 */
@SuppressWarnings("unused")
public interface PersonEndorsementRepository extends JpaRepository<PersonEndorsement,Long> {

    Page<PersonEndorsement> findByPerson_Id(Long id, Pageable pageable);
}
