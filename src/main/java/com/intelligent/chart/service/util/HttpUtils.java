package com.intelligent.chart.service.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static WebClient newWebClient() {

        BrowserVersion[] browserVersions = {BrowserVersion.CHROME, BrowserVersion.FIREFOX_17, BrowserVersion.INTERNET_EXPLORER_8,
            BrowserVersion.INTERNET_EXPLORER_9, BrowserVersion.INTERNET_EXPLORER_10, BrowserVersion.INTERNET_EXPLORER_7, BrowserVersion.CHROME_16};
        BrowserVersion browserVersion = browserVersions[new Random().nextInt(browserVersions.length)];

        log.debug("Create new browser with " + browserVersion.toString());
        WebClient webClient = new WebClient(browserVersion, "202.153.228.130", 8080);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(30000);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }

    public static WebClient newNormalWebClient() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(30000);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }

}


