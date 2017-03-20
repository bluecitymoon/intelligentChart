package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.WordCloudService;
import com.intelligent.chart.domain.WordCloud;
import com.intelligent.chart.repository.WordCloudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WordCloud.
 */
@Service
@Transactional
public class WordCloudServiceImpl implements WordCloudService{

    private final Logger log = LoggerFactory.getLogger(WordCloudServiceImpl.class);
    
    @Inject
    private WordCloudRepository wordCloudRepository;

    /**
     * Save a wordCloud.
     *
     * @param wordCloud the entity to save
     * @return the persisted entity
     */
    public WordCloud save(WordCloud wordCloud) {
        log.debug("Request to save WordCloud : {}", wordCloud);
        WordCloud result = wordCloudRepository.save(wordCloud);
        return result;
    }

    /**
     *  Get all the wordClouds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WordCloud> findAll(Pageable pageable) {
        log.debug("Request to get all WordClouds");
        Page<WordCloud> result = wordCloudRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one wordCloud by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WordCloud findOne(Long id) {
        log.debug("Request to get WordCloud : {}", id);
        WordCloud wordCloud = wordCloudRepository.findOne(id);
        return wordCloud;
    }

    /**
     *  Delete the  wordCloud by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WordCloud : {}", id);
        wordCloudRepository.delete(id);
    }
}
