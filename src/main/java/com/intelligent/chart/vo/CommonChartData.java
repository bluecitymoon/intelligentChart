package com.intelligent.chart.vo;

import java.io.Serializable;

/**
 * Created by Jerry on 2017/3/16.
 */
public class CommonChartData implements Serializable {

    private Integer y;
    private String x;

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
}
