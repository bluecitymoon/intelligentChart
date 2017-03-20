package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFansHobby;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFansHobby entity.
 */
@SuppressWarnings("unused")
public interface PersonFansHobbyRepository extends JpaRepository<PersonFansHobby,Long> {
    Page<PersonFansHobby> findByPerson_Id(Long id, Pageable pageable);


}
