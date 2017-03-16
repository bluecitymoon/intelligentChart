package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonRelation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonRelation entity.
 */
@SuppressWarnings("unused")
public interface PersonRelationRepository extends JpaRepository<PersonRelation,Long> {

    List<PersonRelation> findByThePerson_Id(Long id);
}
