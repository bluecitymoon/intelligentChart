package com.intelligent.chart.service.impl;

import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.common.collect.Sets;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.WebClientCookie;
import com.intelligent.chart.domain.Website;
import com.intelligent.chart.repository.WebClientCookieRepository;
import com.intelligent.chart.service.WebClientCookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing WebClientCookie.
 */
@Service
public class WebClientCookieServiceImpl implements WebClientCookieService{

    private final Logger log = LoggerFactory.getLogger(WebClientCookieServiceImpl.class);

    @Inject
    private WebClientCookieRepository webClientCookieRepository;

    /**
     * Save a webClientCookie.
     *
     * @param webClientCookie the entity to save
     * @return the persisted entity
     */
    public WebClientCookie save(WebClientCookie webClientCookie) {
        log.debug("Request to save WebClientCookie : {}", webClientCookie);
        WebClientCookie result = webClientCookieRepository.save(webClientCookie);
        return result;
    }


    public Page<WebClientCookie> findAll(Pageable pageable) {
        log.debug("Request to get all WebClientCookies");
        Page<WebClientCookie> result = webClientCookieRepository.findAll(pageable);
        return result;
    }

    public WebClientCookie findOne(Long id) {
        log.debug("Request to get WebClientCookie : {}", id);
        WebClientCookie webClientCookie = webClientCookieRepository.findOne(id);
        return webClientCookie;
    }

    /**
     *  Delete the  webClientCookie by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WebClientCookie : {}", id);
        webClientCookieRepository.delete(id);
    }

    @Override
    public void saveCookies(ProxyServer proxyServer, Set<Cookie> cookies, Website website) {

        log.info("Refresh cookie for " + proxyServer.getAddress());

        removeCookies(proxyServer, website);

        for (Cookie cookie : cookies) {

            WebClientCookie webClientCookie = WebClientCookie.builder()
                .domain(cookie.getDomain())
                .name(cookie.getName())
                .path(cookie.getPath())
                .value(cookie.getValue())
                .httpOnly(cookie.isHttpOnly())
                .secure(cookie.isSecure())
                .expires(cookie.getExpires())
                .website(website)
                .proxyServer(proxyServer)
                .build();

            save(webClientCookie);
        }

    }

    @Override
    public void removeCookies(ProxyServer proxyServer, Website website) {

        List<WebClientCookie> existedCookies = webClientCookieRepository.findByWebsiteAndProxyServer(website, proxyServer);

        if (existedCookies != null && !existedCookies.isEmpty()) {
            webClientCookieRepository.delete(existedCookies);
        }
    }

    @Override
    public Set<Cookie> getCookiesByServerAndWebsite(ProxyServer proxyServer, Website website) {

        Set<Cookie> cookies = Sets.newHashSet();

        List<WebClientCookie> databaseCookies = webClientCookieRepository.findByWebsiteAndProxyServer(website, proxyServer);
        if (databaseCookies == null || databaseCookies.isEmpty()) {
            return cookies;
        }

        log.info("Found existed cookie for " + proxyServer.getAddress() + ", size is " + databaseCookies.size());

        for (WebClientCookie databaseCookie : databaseCookies) {
            Cookie cookie = new Cookie(databaseCookie.getDomain(), databaseCookie.getName(), databaseCookie.getValue(), databaseCookie.getPath(), databaseCookie.getExpires(), databaseCookie.isSecure(), databaseCookie.isHttpOnly());
            cookies.add(cookie);
        }

        return cookies;
    }
}
