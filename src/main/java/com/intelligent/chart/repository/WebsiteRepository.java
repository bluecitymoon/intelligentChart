package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Website entity.
 */
@SuppressWarnings("unused")
public interface WebsiteRepository extends JpaRepository<Website,Long> {

    Website findByName(String name);
}
