package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.DoubanMovieTagService;
import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.repository.DoubanMovieTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DoubanMovieTag.
 */
@Service
@Transactional
public class DoubanMovieTagServiceImpl implements DoubanMovieTagService{

    private final Logger log = LoggerFactory.getLogger(DoubanMovieTagServiceImpl.class);
    
    @Inject
    private DoubanMovieTagRepository doubanMovieTagRepository;

    /**
     * Save a doubanMovieTag.
     *
     * @param doubanMovieTag the entity to save
     * @return the persisted entity
     */
    public DoubanMovieTag save(DoubanMovieTag doubanMovieTag) {
        log.debug("Request to save DoubanMovieTag : {}", doubanMovieTag);
        DoubanMovieTag result = doubanMovieTagRepository.save(doubanMovieTag);
        return result;
    }

    /**
     *  Get all the doubanMovieTags.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DoubanMovieTag> findAll(Pageable pageable) {
        log.debug("Request to get all DoubanMovieTags");
        Page<DoubanMovieTag> result = doubanMovieTagRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one doubanMovieTag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DoubanMovieTag findOne(Long id) {
        log.debug("Request to get DoubanMovieTag : {}", id);
        DoubanMovieTag doubanMovieTag = doubanMovieTagRepository.findOne(id);
        return doubanMovieTag;
    }

    /**
     *  Delete the  doubanMovieTag by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DoubanMovieTag : {}", id);
        doubanMovieTagRepository.delete(id);
    }
}
