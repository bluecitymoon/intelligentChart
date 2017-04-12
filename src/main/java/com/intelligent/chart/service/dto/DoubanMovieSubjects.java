package com.intelligent.chart.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Jerry on 2017/4/12.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoubanMovieSubjects {

    private List<DoubanMovieSubject> subjects;
}
