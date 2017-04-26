package com.intelligent.chart.repository;

import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.WebClientCookie;
import com.intelligent.chart.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the WebClientCookie entity.
 */
@SuppressWarnings("unused")
public interface WebClientCookieRepository extends JpaRepository<WebClientCookie,Long> {

    List<WebClientCookie> findByWebsiteAndProxyServer(Website website, ProxyServer proxyServer);
}
