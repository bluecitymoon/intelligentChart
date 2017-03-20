package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFanSex;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFanSex entity.
 */
@SuppressWarnings("unused")
public interface PersonFanSexRepository extends JpaRepository<PersonFanSex,Long> {
    Page<PersonFanSex> findByPerson_Id(Long id, Pageable pageable);


}
