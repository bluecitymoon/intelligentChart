package com.intelligent.chart.vo;

import java.util.List;

/**
 * Created by Jerry on 2017/1/17.
 */
public class ChartData {

    private List<String> titles;
    private List<Float> numbers;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<Float> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Float> numbers) {
        this.numbers = numbers;
    }
}
