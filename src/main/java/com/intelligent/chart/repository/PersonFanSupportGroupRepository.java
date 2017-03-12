package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFanSupportGroup;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFanSupportGroup entity.
 */
@SuppressWarnings("unused")
public interface PersonFanSupportGroupRepository extends JpaRepository<PersonFanSupportGroup,Long> {
    Page<PersonFanSupportGroup> findByPerson_Id(Long id, Pageable pageable);


}
