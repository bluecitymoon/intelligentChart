package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonSocialMedia;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonSocialMedia entity.
 */
@SuppressWarnings("unused")
public interface PersonSocialMediaRepository extends JpaRepository<PersonSocialMedia,Long> {

}
