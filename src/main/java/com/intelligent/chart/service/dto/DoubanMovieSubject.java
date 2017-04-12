package com.intelligent.chart.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jerry on 2017/4/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoubanMovieSubject {

    private String rate;
    private String title;
    private String url;
    private String cover;
    private String id;
}
