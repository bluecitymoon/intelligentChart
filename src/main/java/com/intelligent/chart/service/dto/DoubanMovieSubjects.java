package com.intelligent.chart.service.dto;

import lombok.*;

import java.util.List;

/**
 * Created by Jerry on 2017/4/12.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
public class DoubanMovieSubjects {

    private List<DoubanMovieSubject> subjects;
}
