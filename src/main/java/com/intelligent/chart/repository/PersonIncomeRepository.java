package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonIncome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonIncome entity.
 */
@SuppressWarnings("unused")
public interface PersonIncomeRepository extends JpaRepository<PersonIncome,Long> {

    Page<PersonIncome> findByPerson_Id(Long id, Pageable pageable);
}
