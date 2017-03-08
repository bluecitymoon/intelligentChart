package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonNetworkTexiActivity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonNetworkTexiActivity entity.
 */
@SuppressWarnings("unused")
public interface PersonNetworkTexiActivityRepository extends JpaRepository<PersonNetworkTexiActivity,Long> {

    Page<PersonNetworkTexiActivity> findByPerson_Id(Long id, Pageable pageable);
}
