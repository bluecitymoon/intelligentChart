package com.intelligent.chart.config.pool;

import com.google.common.collect.Lists;
import com.intelligent.chart.domain.ProxyServer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * Created by Jerry on 2017/4/20.
 */
@Scope("singleton")
@Component
public class ProxyServerPool {

    private static LinkedList<ProxyServer> proxyServers = Lists.newLinkedList();

    public synchronized void push(ProxyServer proxyServer) { proxyServers.push(proxyServer);}

    public synchronized ProxyServer pull() {
        ProxyServer proxyServer = proxyServers.pollLast();

        push(proxyServer);
        return proxyServer;
    }

    private synchronized LinkedList<ProxyServer> getProxyServers() {
        return proxyServers;
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
