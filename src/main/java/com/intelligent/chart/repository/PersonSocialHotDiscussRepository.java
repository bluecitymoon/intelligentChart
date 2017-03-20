package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonSocialHotDiscuss;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonSocialHotDiscuss entity.
 */
@SuppressWarnings("unused")
public interface PersonSocialHotDiscussRepository extends JpaRepository<PersonSocialHotDiscuss,Long> {
    Page<PersonSocialHotDiscuss> findByPerson_Id(Long id, Pageable pageable);


}
