package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonNetworkDebit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonNetworkDebit entity.
 */
@SuppressWarnings("unused")
public interface PersonNetworkDebitRepository extends JpaRepository<PersonNetworkDebit,Long> {

    Page<PersonNetworkDebit> findByPerson_Id(Long id, Pageable pageable);
}
