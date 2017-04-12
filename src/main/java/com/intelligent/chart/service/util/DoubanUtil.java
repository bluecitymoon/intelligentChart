package com.intelligent.chart.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligent.chart.service.dto.DoubanMovieSubjects;

import java.io.IOException;

/**
 * Created by Jerry on 2017/4/12.
 */
public class DoubanUtil {

    public static DoubanMovieSubjects grabSinglePageSubjectsWithTag(int start, int size, String tag) {

        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=" + tag + "&sort=recommend&page_limit=" + size + "&page_start=" + start;
        DoubanMovieSubjects doubanMovieSubjects = null;

        try {
            String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            doubanMovieSubjects = objectMapper.readValue(response, DoubanMovieSubjects.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doubanMovieSubjects;
    }
}
