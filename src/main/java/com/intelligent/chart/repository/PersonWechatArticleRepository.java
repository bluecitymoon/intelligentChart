package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonWechatArticle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonWechatArticle entity.
 */
@SuppressWarnings("unused")
public interface PersonWechatArticleRepository extends JpaRepository<PersonWechatArticle,Long> {

    Page<PersonWechatArticle> findByPerson_Id(Long id, Pageable pageable);

    List<PersonWechatArticle> findByPerson_IdOrderByCreateDateDesc(Long id);
}
