package com.intelligent.chart.repository;

import com.intelligent.chart.domain.SocialMediaType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SocialMediaType entity.
 */
@SuppressWarnings("unused")
public interface SocialMediaTypeRepository extends JpaRepository<SocialMediaType,Long> {

}
