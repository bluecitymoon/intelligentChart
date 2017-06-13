package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonExperience entity.
 */
@SuppressWarnings("unused")
public interface PersonExperienceRepository extends JpaRepository<PersonExperience,Long> {

    Page<PersonExperience> findByPerson_Id(Long id, Pageable pageable);

    List<PersonExperience> findByPerson_Id(Long id);


}
