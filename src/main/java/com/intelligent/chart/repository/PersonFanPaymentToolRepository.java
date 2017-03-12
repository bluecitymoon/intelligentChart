package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFanPaymentTool;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFanPaymentTool entity.
 */
@SuppressWarnings("unused")
public interface PersonFanPaymentToolRepository extends JpaRepository<PersonFanPaymentTool,Long> {
    Page<PersonFanPaymentTool> findByPerson_Id(Long id, Pageable pageable);


}
