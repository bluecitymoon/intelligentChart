package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonWechatArticleService;
import com.intelligent.chart.domain.PersonWechatArticle;
import com.intelligent.chart.repository.PersonWechatArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonWechatArticle.
 */
@Service
@Transactional
public class PersonWechatArticleServiceImpl implements PersonWechatArticleService{

    private final Logger log = LoggerFactory.getLogger(PersonWechatArticleServiceImpl.class);
    
    @Inject
    private PersonWechatArticleRepository personWechatArticleRepository;

    /**
     * Save a personWechatArticle.
     *
     * @param personWechatArticle the entity to save
     * @return the persisted entity
     */
    public PersonWechatArticle save(PersonWechatArticle personWechatArticle) {
        log.debug("Request to save PersonWechatArticle : {}", personWechatArticle);
        PersonWechatArticle result = personWechatArticleRepository.save(personWechatArticle);
        return result;
    }

    /**
     *  Get all the personWechatArticles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonWechatArticle> findAll(Pageable pageable) {
        log.debug("Request to get all PersonWechatArticles");
        Page<PersonWechatArticle> result = personWechatArticleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personWechatArticle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonWechatArticle findOne(Long id) {
        log.debug("Request to get PersonWechatArticle : {}", id);
        PersonWechatArticle personWechatArticle = personWechatArticleRepository.findOne(id);
        return personWechatArticle;
    }

    /**
     *  Delete the  personWechatArticle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonWechatArticle : {}", id);
        personWechatArticleRepository.delete(id);
    }
}
