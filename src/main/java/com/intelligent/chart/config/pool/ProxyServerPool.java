package com.intelligent.chart.config.pool;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.common.collect.Lists;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.service.util.HttpUtils;
import com.intelligent.chart.vo.TimestapWebclient;
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

    public static final Long vistInterval = 1000 * 30L;
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

        ProxyServer proxyServer = pull();

        TimestapWebclient timestapWebclient = getVisiableTimestapWebclient(proxyServer);

        if (timestapWebclient != null) {

            return new Map.Entry<ProxyServer, TimestapWebclient>() {
                @Override
                public ProxyServer getKey() {
                    return proxyServer;
                }

                @Override
                public TimestapWebclient getValue() {
                    return timestapWebclient;
                }

                @Override
                public TimestapWebclient setValue(TimestapWebclient value) {
                    return null;
                }
            };
        } else {

            return new Map.Entry<ProxyServer, TimestapWebclient>() {
                @Override
                public ProxyServer getKey() {
                    return proxyServer;
                }

                @Override
                public TimestapWebclient getValue() {

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
    }

    private synchronized LinkedList<ProxyServer> getProxyServers() {
        return proxyServers;
    }

    private synchronized WebClient getWebclientByProxyServer(ProxyServer proxyServer) {

        return null;
    }

    private TimestapWebclient getVisiableTimestapWebclient(ProxyServer proxyServer) {

        TimestapWebclient timestapWebclient = webClientConcurrentHashMap.get(proxyServer);

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
