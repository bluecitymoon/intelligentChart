package com.intelligent.chart.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.common.collect.Lists;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.enumeration.ProxyServerCategory;
import com.intelligent.chart.repository.ProxyServerRepository;
import com.intelligent.chart.service.ProxyServerService;
import com.intelligent.chart.service.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service Implementation for managing ProxyServer.
 */
@Service
public class ProxyServerServiceImpl implements ProxyServerService{

    private final Logger log = LoggerFactory.getLogger(ProxyServerServiceImpl.class);

    @Inject
    private ProxyServerRepository proxyServerRepository;

    @Inject
    private EntityManager entityManager;

    /**
     * Save a proxyServer.
     *
     * @param proxyServer the entity to save
     * @return the persisted entity
     */
    public ProxyServer save(ProxyServer proxyServer) {
        log.debug("Request to save ProxyServer : {}", proxyServer);
        ProxyServer result = proxyServerRepository.save(proxyServer);
        return result;
    }

    /**
     *  Get all the proxyServers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProxyServer> findAll(Pageable pageable) {
        log.debug("Request to get all ProxyServers");
        Page<ProxyServer> result = proxyServerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one proxyServer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProxyServer findOne(Long id) {
        log.debug("Request to get ProxyServer : {}", id);
        ProxyServer proxyServer = proxyServerRepository.findOne(id);
        return proxyServer;
    }

    /**
     *  Delete the  proxyServer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProxyServer : {}", id);
        proxyServerRepository.delete(id);
    }

    public void grabProxyServers() {

        WebClient webClient = HttpUtils.newNormalWebClient();
        String[] urls = {"http://www.kuaidaili.com/free/outha/", "http://www.kuaidaili.com/free/outtr/", "http://www.kuaidaili.com/free/inha/", "http://www.kuaidaili.com/free/intr/"};

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        for (String url: urls) {

            for (int i = 1; i < 50; i++) {

                grabSinglePage(i, url, webClient, formatter);

            }
        }

    }

    @Override
    public ProxyServer findRandomMostValuableProxyServer() {

        Integer minId =  entityManager.createNativeQuery("select min(id) from proxy_server").getFirstResult();
        Integer maxId = entityManager.createNativeQuery("select max(id) from proxy_server").getFirstResult();

        Integer randomId = new Random().nextInt(26858);

        ProxyServer proxyServer = findOne(Long.valueOf(randomId) + 1);

        if (proxyServer == null) {
            return findRandomMostValuableProxyServer();
        }

        if (!proxyServer.isIsReachable()) {
            return findRandomMostValuableProxyServer();
        }

        return proxyServer;
    }

    @Override
    public ProxyServer findOneReachableProxyServer() {

        ProxyServer server = proxyServerRepository.findTop1ByIsReachableTrueOrderByLastSuccessDateAscTotalFailCountAscTotalSuccessCountDesc();

        if (server == null) {

            List<ProxyServer> reachabeServers = proxyServerRepository.findByIsReachableTrue();

            int randomServer = new Random().nextInt(reachabeServers.size());
            return reachabeServers.get(randomServer);

        } else {
            return server;
        }

    }

    @Override
    public void increaseSuccessCount(ProxyServer proxyServer) {

        if (proxyServer.getTotalSuccessCount() == null || proxyServer.getTotalSuccessCount() == 0) {
            proxyServer.setTotalSuccessCount(1);
        } else {
            proxyServer.setTotalSuccessCount(proxyServer.getTotalSuccessCount() + 1);
        }

        proxyServer.setLastSuccessDate(ZonedDateTime.now());

        save(proxyServer);
    }

    @Override
    public void increaseFailCount(ProxyServer proxyServer) {

        if (proxyServer.getTotalFailCount() == null || proxyServer.getTotalFailCount() == 0) {
            proxyServer.setTotalFailCount(1);
        } else {
            proxyServer.setTotalFailCount(proxyServer.getTotalFailCount() + 1);
        }

        proxyServer.setLastFailDate(ZonedDateTime.now());

        save(proxyServer);

    }

    @Override
    public void checkReachable() {

        List<ProxyServer> allServers = proxyServerRepository.findAll();
        List<List<ProxyServer>> partitionServers = Lists.partition(allServers, 200);

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        for (final List<ProxyServer> servers: partitionServers) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    servers.forEach(proxyServer -> {
                        boolean isReachable = HttpUtils.isReachable(proxyServer.getAddress());

                        log.info(proxyServer.getAddress() + " reachable is " + isReachable);

                        proxyServer.setIsReachable(isReachable);

                        save(proxyServer);
                    });
                }
            });
        }

    }

    private void grabSinglePage(int page, String url, WebClient webClient, DateTimeFormatter formatter) {

        String fullUrl = url + page;
        try {
            String response = webClient.getPage(fullUrl).getWebResponse().getContentAsString();

            if (response == "-10") {
                 grabSinglePage(page, url, webClient, formatter);

                 return;
            }
            Document document = Jsoup.parse(response);
            Elements elements = document.getElementsByAttribute("data-title");

            List<List<Element>> subElements = Lists.partition(elements, 7);

            subElements.forEach(els -> {

                String address = els.get(0).text();
                ProxyServer existedServer = proxyServerRepository.findByAddress(address);
                if (existedServer == null) {

                    ProxyServer proxyServer = ProxyServer.builder().address(els.get(0).text())
                        .port(Integer.valueOf(els.get(1).text()))
                        .anonymousLevel(els.get(2).text())
                        .httpType(els.get(3).text())
                        .location(els.get(4).text())

                        .lastValidationDate(ZonedDateTime.parse(els.get(6).text(), formatter))
                        .createDate(ZonedDateTime.now())
                        .build();

                    if (StringUtils.isNotEmpty(els.get(5).text())) {

                        try {
                            proxyServer.setResponseSecond(Float.valueOf(els.get(5).text().replace("秒", "")));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();

                            proxyServer.setResponseSecond(0f);
                        }

                    }

                    switch (url) {
                        case "http://www.kuaidaili.com/free/outha/":
                            proxyServer.setCategory(ProxyServerCategory.outCountryAnonymous);
                            break;
                        case "http://www.kuaidaili.com/free/outtr/":
                            proxyServer.setCategory(ProxyServerCategory.outCountryVisible);
                            break;
                        case "http://www.kuaidaili.com/free/inha/":
                            proxyServer.setCategory(ProxyServerCategory.inCountryAnonymous);
                            break;
                        case "http://www.kuaidaili.com/free/intr/":
                            proxyServer.setCategory(ProxyServerCategory.inCountryVisible);
                            break;
                        default:break;
                    }
                    save(proxyServer);
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        String text = "2017-04-18 14:36:12";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ZonedDateTime.parse(text, formatter.withZone(ZoneId.systemDefault())).toLocalDate());
//    }
}
