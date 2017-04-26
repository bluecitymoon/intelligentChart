package com.intelligent.chart.config.pool;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.common.collect.Lists;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.service.impl.MovieServiceImpl;
import com.intelligent.chart.service.util.HttpUtils;
import com.intelligent.chart.vo.TimestapWebclient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
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

    public Map.Entry<ProxyServer, TimestapWebclient> newWebclientFromProxyServer() {

//        for (Map.Entry<ProxyServer, TimestapWebclient> action : webClientConcurrentHashMap.entrySet()) {
//
//            if (action.getValue().getLastSuccessTimestamp() - System.currentTimeMillis() > vistInterval) {
//
//                log.info("Found visiable web client from pool : " + action.getKey().getAddress());
//                return action;
//            }
//        }

        ProxyServer proxyServer = pull();

     //   TimestapWebclient timestapWebclient = getVisiableTimestapWebclient(proxyServer);

//        if (timestapWebclient != null) {
//
//            return new Map.Entry<ProxyServer, TimestapWebclient>() {
//                @Override
//                public ProxyServer getKey() {
//                    return proxyServer;
//                }
//
//                @Override
//                public TimestapWebclient getValue() {
//
//                    log.info("Using existed proxy server " + proxyServer.getAddress() + " and existed web client.");
//
//                    return timestapWebclient;
//                }
//
//                @Override
//                public TimestapWebclient setValue(TimestapWebclient value) {
//                    return null;
//                }
//            };
//        } else {

            return new Map.Entry<ProxyServer, TimestapWebclient>() {
                @Override
                public ProxyServer getKey() {
                    return proxyServer;
                }

                @Override
                public TimestapWebclient getValue() {

                    log.info("Using new proxy server " + proxyServer.getAddress() + " and created new web client.");

                    TimestapWebclient t = TimestapWebclient.builder().lastSuccessTimestamp(System.currentTimeMillis()).
                        webClient(HttpUtils.newWebClientWithRandomProxyServer(proxyServer)).build();
                    return t;
                }

                @Override
                public TimestapWebclient setValue(TimestapWebclient value) {
                    return null;
                }
            };
      //  }
    }

    private synchronized LinkedList<ProxyServer> getProxyServers() {
        return proxyServers;
    }

    private synchronized WebClient getWebclientByProxyServer(ProxyServer proxyServer) {

        return null;
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
//    public static void main(String[] args) {
//        ProxyServerPool proxyServerPool = new ProxyServerPool();
//        proxyServerPool.push(ProxyServer.builder().address("a").build());
//
//        proxyServerPool.push(ProxyServer.builder().address("a1").build());
//
//        proxyServerPool.push(ProxyServer.builder().address("a2").build());
//        proxyServerPool.push(ProxyServer.builder().address("a3").build());
//        proxyServerPool.push(ProxyServer.builder().address("a4").build());
//        proxyServerPool.push(ProxyServer.builder().address("a5").build());
//        proxyServerPool.push(ProxyServer.builder().address("a6").build());
//        proxyServerPool.push(ProxyServer.builder().address("a7").build());
//        proxyServerPool.push(ProxyServer.builder().address("a8").build());
//
//        proxyServerPool.getProxyServers().forEach(e->System.out.println(e));
//
//        proxyServerPool.pull();
//
//        proxyServerPool.getProxyServers().forEach(e->System.out.println(e));
//
//        System.out.println(proxyServerPool.pull());
//
//        proxyServerPool.getProxyServers().forEach(e->System.out.println(e));
//    }
}
