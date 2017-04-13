package com.intelligent.chart.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by Jerry on 2017/4/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class DoubanMovieSubject {

    private String rate;
    private String title;
    private String url;
    private String cover;
    private String id;

    @JsonProperty("cover_x")
    private String coverX;
    @JsonProperty("is_beetle_subject")
    private Boolean isBeetleSubject;
    private Boolean playable;

    @JsonProperty("cover_y")
    private Integer coverY;
    @JsonProperty("is_new")
    private Boolean isNew;
}
