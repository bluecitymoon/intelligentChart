package com.intelligent.chart.vo;

import com.gargoylesoftware.htmlunit.WebClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jerry on 2017/4/23.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TimestapWebclient {

    private Long lastSuccessTimestamp;
    private WebClient webClient;
}
