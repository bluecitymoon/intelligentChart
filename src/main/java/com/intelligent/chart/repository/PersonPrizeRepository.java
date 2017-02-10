package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonPrize;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonPrize entity.
 */
@SuppressWarnings("unused")
public interface PersonPrizeRepository extends JpaRepository<PersonPrize,Long> {

}
