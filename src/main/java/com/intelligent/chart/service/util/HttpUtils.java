package com.intelligent.chart.service.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.intelligent.chart.domain.ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static final int defaultTimeout = 50000;

    public static WebClient newWebClient() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(defaultTimeout);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }

    public static WebClient newNormalWebClient() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(defaultTimeout);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }

    public static WebClient newWebClientWithRandomProxyServer(ProxyServer proxyServer) {

        BrowserVersion[] browserVersions = {BrowserVersion.CHROME, BrowserVersion.FIREFOX_17, BrowserVersion.INTERNET_EXPLORER_8,
            BrowserVersion.INTERNET_EXPLORER_9, BrowserVersion.INTERNET_EXPLORER_10, BrowserVersion.INTERNET_EXPLORER_7, BrowserVersion.CHROME_16};
        BrowserVersion browserVersion = browserVersions[new Random().nextInt(browserVersions.length)];

        log.debug("Create new browser with " + browserVersion.toString());
        WebClient webClient = new WebClient(browserVersion, proxyServer.getAddress(), proxyServer.getPort());
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(defaultTimeout);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }

    public static boolean isReachable(String address) {

        try {
            InetAddress inet4Address = Inet4Address.getByName(address);

            if (inet4Address.isReachable(5000)) {
                return true;
            }
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}


