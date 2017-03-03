package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonLawBusiness;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonLawBusiness entity.
 */
@SuppressWarnings("unused")
public interface PersonLawBusinessRepository extends JpaRepository<PersonLawBusiness,Long> {

}
