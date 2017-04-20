package com.intelligent.chart.service.impl;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.service.DoubanMovieTagService;
import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.repository.DoubanMovieTagRepository;
import com.intelligent.chart.service.util.HttpUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Service Implementation for managing DoubanMovieTag.
 */
@Service
public class DoubanMovieTagServiceImpl implements DoubanMovieTagService{

    private final Logger log = LoggerFactory.getLogger(DoubanMovieTagServiceImpl.class);

    @Inject
    private DoubanMovieTagRepository doubanMovieTagRepository;

    @Inject
    private ProxyServerPool proxyServerPool;

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

    @Override
    public void getAllMaxCount() {

        doubanMovieTagRepository.findAll().forEach(this::getSingleCategoryMaxCount);

    }

    private void getSingleCategoryMaxCount(DoubanMovieTag e) {

        try {
            String url = "https://movie.douban.com/tag/" + URLEncoder.encode(e.getName(), "UTF-8");
            ProxyServer proxyServer = proxyServerPool.pull();

            WebResponse response = HttpUtils.newNormalWebClient().getPage(url).getWebResponse();

//            if (response.getStatusCode() != 200) {
//                getSingleCategoryMaxCount(e);
//
//                return;
//            }

            String content = response.getContentAsString();
            Document document = Jsoup.parse(content);

            HashSet<Integer> pageNumbers = Sets.newHashSet();
            Elements lastPage = document.getElementsByAttributeValueStarting("href", url);
            lastPage.forEach( lp -> {

                if (NumberUtils.isNumber(lp.text())) {
                    pageNumbers.add(Integer.valueOf(lp.text()));
                }
            });

            if (pageNumbers.isEmpty()) {
                try {
//                    Integer lastIndex = Integer.valueOf(lastPage.text());
                    e.setMaxPageCount(1);

                    log.info("setting " + e.getName() + " max count to " + e.getMaxPageCount());
                    save(e);

                } catch (Exception n) {
                    n.printStackTrace();
                }
            } else {

                try {

                    Integer max = Collections.max(pageNumbers);

                    e.setMaxPageCount(max);

                    save(e);

                    log.info("setting " + e.getName() + " max count to " + max);

                } catch (Exception n) {
                    n.printStackTrace();
                }
            }


        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
