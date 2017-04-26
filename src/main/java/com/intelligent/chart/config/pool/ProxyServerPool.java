package com.intelligent.chart.config.pool;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.common.collect.Lists;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.Website;
import com.intelligent.chart.service.WebClientCookieService;
import com.intelligent.chart.service.util.HttpUtils;
import com.intelligent.chart.vo.TimestapWebclient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jerry on 2017/4/20.
 */
@Scope("singleton")
@Component
public class ProxyServerPool {
    private final static Logger log = LoggerFactory.getLogger(ProxyServerPool.class);

    public static final Long vistInterval = 1000 * 5L;
    private static LinkedList<ProxyServer> proxyServers = Lists.newLinkedList();

    private static ConcurrentHashMap<ProxyServer, TimestapWebclient> webClientConcurrentHashMap = new ConcurrentHashMap<>();

    @Inject
    private WebClientCookieService webClientCookieService;

    public synchronized void push(ProxyServer proxyServer) { proxyServers.push(proxyServer);}

    public synchronized ProxyServer pull() {
        ProxyServer proxyServer = proxyServers.pollLast();

        push(proxyServer);
        return proxyServer;
    }

    public synchronized void pushNiceWebclient(ProxyServer proxyServer, WebClient webClient) {

        TimestapWebclient timestapWebclient = TimestapWebclient.builder().lastSuccessTimestamp(System.currentTimeMillis()).webClient(webClient).build();

        webClientConcurrentHashMap.put(proxyServer, timestapWebclient);

    }

    @Deprecated
    public Map.Entry<ProxyServer, TimestapWebclient> newWebclientFromProxyServer() {

        ProxyServer proxyServer = pull();

            return new Map.Entry<ProxyServer, TimestapWebclient>() {
                @Override
                public ProxyServer getKey() {
                    return proxyServer;
                }

                @Override
                public TimestapWebclient getValue() {

                    log.info("Using new proxy server " + proxyServer.getAddress() + " and created new web client.");

                   // Set<Cookie> lastCookies = webClientCookieService.getCookiesByServerAndWebsite(proxyServer, )
                    TimestapWebclient t = TimestapWebclient.builder().lastSuccessTimestamp(System.currentTimeMillis()).
                        webClient(HttpUtils.newWebClientWithRandomProxyServer(proxyServer)).build();

                    return t;
                }

                @Override
                public TimestapWebclient setValue(TimestapWebclient value) {
                    return null;
                }
            };

    }

    private synchronized LinkedList<ProxyServer> getProxyServers() {
        return proxyServers;
    }


    @Deprecated
    private synchronized WebClient getWebclientByProxyServer(ProxyServer proxyServer) {


        return null;
    }

    public synchronized WebClient retrieveWebclient(Website website) {

        ProxyServer proxyServer = pull();

        Set<Cookie> cookies = webClientCookieService.getCookiesByServerAndWebsite(proxyServer, website);

        WebClient webClient = HttpUtils.newWebClientWithRandomProxyServer(proxyServer);

        if (!cookies.isEmpty()) {

            cookies.forEach(e -> {
                webClient.getCookieManager().addCookie(e);
            });
        }

        return webClient;

    }

    private TimestapWebclient getVisiableTimestapWebclient(ProxyServer proxyServer) {

        TimestapWebclient timestapWebclient = webClientConcurrentHashMap.get(proxyServer);

        if (timestapWebclient == null) {
            return null;
        }
        if (timestapWebclient.getLastSuccessTimestamp() - System.currentTimeMillis() > vistInterval) {
            return timestapWebclient;
        }

        return null;
    }

}
