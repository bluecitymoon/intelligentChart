package com.intelligent.chart.repository;

import com.intelligent.chart.domain.FansPurchasingSection;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FansPurchasingSection entity.
 */
@SuppressWarnings("unused")
public interface FansPurchasingSectionRepository extends JpaRepository<FansPurchasingSection,Long> {
//    Page<FansPurchasingSection> findByPerson_Id(Long id, Pageable pageable);


}
