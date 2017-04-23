package com.intelligent.chart.config.pool;

import com.gargoylesoftware.htmlunit.WebClient;
import com.intelligent.chart.domain.ProxyServer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jerry on 2017/4/23.
 */
public class WebClientPool {


    private static ConcurrentHashMap<ProxyServer, WebClient> webClientConcurrentHashMap = new ConcurrentHashMap<>();
}
