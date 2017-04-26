package com.intelligent.chart.service;

import com.gargoylesoftware.htmlunit.util.Cookie;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.WebClientCookie;
import com.intelligent.chart.domain.Website;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Service Interface for managing WebClientCookie.
 */
public interface WebClientCookieService {

    /**
     * Save a webClientCookie.
     *
     * @param webClientCookie the entity to save
     * @return the persisted entity
     */
    WebClientCookie save(WebClientCookie webClientCookie);

    /**
     *  Get all the webClientCookies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebClientCookie> findAll(Pageable pageable);

    /**
     *  Get the "id" webClientCookie.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebClientCookie findOne(Long id);

    /**
     *  Delete the "id" webClientCookie.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void saveCookies(ProxyServer proxyServer, Set<Cookie> cookies, Website website);

    Set<Cookie> getCookiesByServerAndWebsite(ProxyServer proxyServer, Website website);
}
