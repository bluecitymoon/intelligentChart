package com.intelligent.chart.service.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.intelligent.chart.service.dto.DoubanMovieSubject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Jerry on 2017/4/14.
 */
public class DetailedLinkUtil {

    public static List<DoubanMovieSubject> grabMovieLinksInSingleCategory(String category) {

        int pageNumber = 0;

        List<DoubanMovieSubject> allSubjects = Lists.newArrayList();

        while (true) {

            List<DoubanMovieSubject> onePageLinks = grabSinglePage(pageNumber, category);

            if (onePageLinks == null || onePageLinks.isEmpty()) {
                break;
            }

            allSubjects.addAll(onePageLinks);

            if (onePageLinks.size() < 20) {
                break;
            }

            pageNumber ++;

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return allSubjects;
    }

    public static List<DoubanMovieSubject> grabSinglePage(int pageNumber, String category) {

        List<DoubanMovieSubject> subjects = Lists.newArrayList();

        try {
            String url = "https://movie.douban.com/tag/" + URLEncoder.encode(category, "UTF-8") + "?start=" + 20 * pageNumber + "&type=T";
            String content = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            Document document = Jsoup.parse(content);
            Elements movieLinks = document.getElementsByClass("nbg");

            movieLinks.forEach(element -> {

                String href = element.attr("href");
                List<String> linkElements = Splitter.on("/").omitEmptyStrings().splitToList(href);

                DoubanMovieSubject doubanMovieSubject = DoubanMovieSubject.builder()
                    .url(href)
                    .title(element.attr("title"))
                    .id(linkElements.get(linkElements.size() - 1))
                    .build();

                Element imgElement = element.getElementsByTag("img").first();
                doubanMovieSubject.setCover(imgElement.attr("src"));

                subjects.add(doubanMovieSubject);
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subjects;
    }

//    public static void main(String args[]) {
//        Splitter.on("/").omitEmptyStrings().split("https://movie.douban.com/subject/26879060/").forEach(a -> System.out.println(a));
//    }
}
