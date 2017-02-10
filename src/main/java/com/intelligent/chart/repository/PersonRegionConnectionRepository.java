package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonRegionConnection;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonRegionConnection entity.
 */
@SuppressWarnings("unused")
public interface PersonRegionConnectionRepository extends JpaRepository<PersonRegionConnection,Long> {

}
