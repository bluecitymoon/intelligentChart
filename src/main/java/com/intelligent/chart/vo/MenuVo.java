package com.intelligent.chart.vo;

import java.io.Serializable;

/**
 * Created by Jerry on 2017/2/2.
 */
public class MenuVo implements Serializable {

    private String title;
    private String logo;
    private Long chartId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
