package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Director.
 */
@Entity
@Table(name = "director")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "total_box_office")
    private Float totalBoxOffice;

    @Column(name = "unit")
    private String unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Director name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public Director totalBoxOffice(Float totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
        return this;
    }

    public void setTotalBoxOffice(Float totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public String getUnit() {
        return unit;
    }

    public Director unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Director director = (Director) o;
        if (director.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, director.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Director{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", totalBoxOffice='" + totalBoxOffice + "'" +
            ", unit='" + unit + "'" +
            '}';
    }
}
