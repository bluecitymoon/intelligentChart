package com.intelligent.chart.vo;

import java.io.Serializable;

/**
 * Created by Jerry on 2017/3/16.
 */
public class CommonChartData implements Serializable {

    private Integer x;
    private String y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
