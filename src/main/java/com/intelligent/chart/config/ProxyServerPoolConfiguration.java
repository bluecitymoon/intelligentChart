package com.intelligent.chart.config;

import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.repository.ProxyServerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;

/**
 * Created by Jerry on 2017/4/20.
 */
@Configuration
public class ProxyServerPoolConfiguration {

    @Inject
    ProxyServerRepository proxyServerRepository;

    @Bean
    @Scope("singleton")
    public ProxyServerPool createServerPool() {
        ProxyServerPool proxyServerPool = new ProxyServerPool();

        proxyServerRepository.findByIsReachableTrueOrderByTotalFailCountAscTotalSuccessCountAsc().forEach(e -> proxyServerPool.push(e));

        return proxyServerPool;
    }
}
