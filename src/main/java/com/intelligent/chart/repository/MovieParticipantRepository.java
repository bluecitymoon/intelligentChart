package com.intelligent.chart.repository;

import com.intelligent.chart.domain.MovieParticipant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MovieParticipant entity.
 */
@SuppressWarnings("unused")
public interface MovieParticipantRepository extends JpaRepository<MovieParticipant,Long> {

}
