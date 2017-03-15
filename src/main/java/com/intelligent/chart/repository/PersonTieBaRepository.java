package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonTieBa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonTieBa entity.
 */
@SuppressWarnings("unused")
public interface PersonTieBaRepository extends JpaRepository<PersonTieBa,Long> {

    Page<PersonTieBa> findByPerson_Id(Long id, Pageable pageable);
}
