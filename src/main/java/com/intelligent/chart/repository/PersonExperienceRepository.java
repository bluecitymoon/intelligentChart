package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonExperience;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonExperience entity.
 */
@SuppressWarnings("unused")
public interface PersonExperienceRepository extends JpaRepository<PersonExperience,Long> {

}
