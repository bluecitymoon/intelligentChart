package com.intelligent.chart.config;

import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.repository.ProxyServerRepository;
import com.intelligent.chart.repository.WebClientCookieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;

/**
 * Created by Jerry on 2017/4/20.
 */
@Configuration
public class ProxyServerPoolConfiguration {


    private final static Logger log = LoggerFactory.getLogger(ProxyServerPoolConfiguration.class);

    @Inject
    ProxyServerRepository proxyServerRepository;

    @Inject
    WebClientCookieRepository webClientCookieRepository;

    @Bean
    @Scope("singleton")
    public ProxyServerPool createServerPool() {
        ProxyServerPool proxyServerPool = new ProxyServerPool();

       // proxyServerRepository.findByIsReachableTrueAndIsBlockedFalseOrderByTotalFailCountAscTotalSuccessCountAsc().forEach(e -> proxyServerPool.push(e));

        webClientCookieRepository.findAll().forEach(e -> {

            ProxyServer proxyServer = e.getProxyServer();

            if (!proxyServer.getIsBlocked() && !proxyServerPool.getProxyServers().contains(proxyServer)) {

                proxyServerPool.push(e.getProxyServer());
            }
        });

        log.info("loaded " + proxyServerPool.getProxyServers().size() + " proxy servers.");

        return proxyServerPool;
    }
}
