package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonIncome;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonIncome entity.
 */
@SuppressWarnings("unused")
public interface PersonIncomeRepository extends JpaRepository<PersonIncome,Long> {

    List<PersonIncome> findByPerson_Id(Long id);
}
