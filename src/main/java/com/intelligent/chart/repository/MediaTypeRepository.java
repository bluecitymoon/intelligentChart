package com.intelligent.chart.repository;

import com.intelligent.chart.domain.MediaType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MediaType entity.
 */
@SuppressWarnings("unused")
public interface MediaTypeRepository extends JpaRepository<MediaType,Long> {

}
