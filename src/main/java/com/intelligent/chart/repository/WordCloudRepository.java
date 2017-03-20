package com.intelligent.chart.repository;

import com.intelligent.chart.domain.WordCloud;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WordCloud entity.
 */
@SuppressWarnings("unused")
public interface WordCloudRepository extends JpaRepository<WordCloud,Long> {

}
