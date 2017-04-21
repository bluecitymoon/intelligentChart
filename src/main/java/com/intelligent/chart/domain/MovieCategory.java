package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MovieCategory.
 */
@Entity
@Table(name = "movie_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private AreaType areaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieCategory movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public MovieCategory areaType(AreaType areaType) {
        this.areaType = areaType;
        return this;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieCategory movieCategory = (MovieCategory) o;
        if (movieCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, movieCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MovieCategory{" +
            "id=" + id +
            '}';
    }
}
