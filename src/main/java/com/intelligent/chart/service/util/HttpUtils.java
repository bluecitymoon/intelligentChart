package com.intelligent.chart.service.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class HttpUtils {

    public static WebClient newWebClient() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(30000);
        webClient.getOptions().setJavaScriptEnabled(false);

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient;
    }
}


