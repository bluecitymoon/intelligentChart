package com.intelligent.chart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
