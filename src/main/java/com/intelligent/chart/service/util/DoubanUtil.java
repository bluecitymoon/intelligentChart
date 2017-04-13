package com.intelligent.chart.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligent.chart.service.dto.DoubanMovieSubjects;

import java.io.IOException;
import java.net.URLEncoder;

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
}
