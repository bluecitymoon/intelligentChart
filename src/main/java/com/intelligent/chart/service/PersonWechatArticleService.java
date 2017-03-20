package com.intelligent.chart.service;

import com.intelligent.chart.domain.PersonWechatArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PersonWechatArticle.
 */
public interface PersonWechatArticleService {

    /**
     * Save a personWechatArticle.
     *
     * @param personWechatArticle the entity to save
     * @return the persisted entity
     */
    PersonWechatArticle save(PersonWechatArticle personWechatArticle);

    /**
     *  Get all the personWechatArticles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonWechatArticle> findAll(Pageable pageable);

    /**
     *  Get the "id" personWechatArticle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonWechatArticle findOne(Long id);

    /**
     *  Delete the "id" personWechatArticle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
