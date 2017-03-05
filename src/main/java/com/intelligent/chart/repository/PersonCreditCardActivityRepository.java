package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonCreditCardActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonCreditCardActivity entity.
 */
@SuppressWarnings("unused")
public interface PersonCreditCardActivityRepository extends JpaRepository<PersonCreditCardActivity,Long> {

}
