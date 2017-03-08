package com.intelligent.chart.repository;

import com.intelligent.chart.domain.NetworkTexiCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NetworkTexiCompany entity.
 */
@SuppressWarnings("unused")
public interface NetworkTexiCompanyRepository extends JpaRepository<NetworkTexiCompany,Long> {

}
