package com.intelligent.chart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jerry on 2017/4/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoviePageIndex {

    private int pageNumber;
    private String tag;

//    public static void main(String[] args) {
//        String url = "http://www.baidu.com?username=jj@werner.com";
//        try {
//            System.out.println(URLEncoder.encode(url, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
}
