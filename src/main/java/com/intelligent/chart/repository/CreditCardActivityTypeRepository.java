package com.intelligent.chart.repository;

import com.intelligent.chart.domain.CreditCardActivityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CreditCardActivityType entity.
 */
@SuppressWarnings("unused")
public interface CreditCardActivityTypeRepository extends JpaRepository<CreditCardActivityType,Long> {

}
