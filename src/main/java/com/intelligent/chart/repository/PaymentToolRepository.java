package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PaymentTool;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PaymentTool entity.
 */
@SuppressWarnings("unused")
public interface PaymentToolRepository extends JpaRepository<PaymentTool,Long> {
//    Page<PaymentTool> findByPerson_Id(Long id, Pageable pageable);


}
