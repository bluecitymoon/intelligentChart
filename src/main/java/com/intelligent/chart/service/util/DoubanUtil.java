package com.intelligent.chart.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.service.dto.DoubanMovieSubject;
import com.intelligent.chart.service.dto.DoubanMovieSubjects;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Jerry on 2017/4/12.
 */
public class DoubanUtil {

    public static DoubanMovieSubjects grabSinglePageSubjectsWithTag(int start, int size, String tag) {

        DoubanMovieSubjects doubanMovieSubjects = null;

        try {

            String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=" + URLEncoder.encode(tag, "UTF-8") + "&sort=recommend&page_limit=" + size + "&page_start=" + start;

            String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            doubanMovieSubjects = objectMapper.readValue(response, DoubanMovieSubjects.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doubanMovieSubjects;
    }

    public static List<String> grabAllTags() {

        String url = "https://movie.douban.com/tag/";
        List<String> doubanMovieTags = Lists.newArrayList();

        try {
            String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            Document document = Jsoup.parse(response);
            Elements tags = document.getElementsByAttributeValueStarting("href", "/tag/");

            for (Element element: tags) {
                doubanMovieTags.add(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doubanMovieTags;
    }

}
