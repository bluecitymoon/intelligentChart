package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ChartMenu.
 */
@Entity
@Table(name = "chart_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChartMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Chart chart;

    @ManyToOne
    private Menu menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chart getChart() {
        return chart;
    }

    public ChartMenu chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Menu getMenu() {
        return menu;
    }

    public ChartMenu menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChartMenu chartMenu = (ChartMenu) o;
        if (chartMenu.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chartMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChartMenu{" +
            "id=" + id +
            '}';
    }
}
