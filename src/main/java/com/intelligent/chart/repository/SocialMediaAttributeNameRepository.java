package com.intelligent.chart.repository;

import com.intelligent.chart.domain.SocialMediaAttributeName;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SocialMediaAttributeName entity.
 */
@SuppressWarnings("unused")
public interface SocialMediaAttributeNameRepository extends JpaRepository<SocialMediaAttributeName,Long> {

}
