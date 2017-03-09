package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonPaidNetworkDebit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the PersonPaidNetworkDebit entity.
 */
@SuppressWarnings("unused")
public interface PersonPaidNetworkDebitRepository extends JpaRepository<PersonPaidNetworkDebit,Long> {

    Page<PersonPaidNetworkDebit> findByPerson_Id(Long id, Pageable pageable);
}
