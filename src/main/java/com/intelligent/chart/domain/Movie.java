package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Size(max = 2000)
    @Column(name = "douban_url", length = 2000)
    private String doubanUrl;

    @Column(name = "rate")
    private Float rate;

    @Size(max = 2000)
    @Column(name = "cover_url", length = 2000)
    private String coverUrl;

    @Column(name = "area")
    private String area;

    @Column(name = "language")
    private String language;

    @Column(name = "run_date")
    private LocalDate runDate;

    @Column(name = "term")
    private String term;

    @Size(max = 20000)
    @Column(name = "mov_description", length = 20000)
    private String movDescription;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoubanUrl() {
        return doubanUrl;
    }

    public Movie doubanUrl(String doubanUrl) {
        this.doubanUrl = doubanUrl;
        return this;
    }

    public void setDoubanUrl(String doubanUrl) {
        this.doubanUrl = doubanUrl;
    }

    public Float getRate() {
        return rate;
    }

    public Movie rate(Float rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Movie coverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getArea() {
        return area;
    }

    public Movie area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguage() {
        return language;
    }

    public Movie language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getRunDate() {
        return runDate;
    }

    public Movie runDate(LocalDate runDate) {
        this.runDate = runDate;
        return this;
    }

    public void setRunDate(LocalDate runDate) {
        this.runDate = runDate;
    }

    public String getTerm() {
        return term;
    }

    public Movie term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getMovDescription() {
        return movDescription;
    }

    public Movie movDescription(String movDescription) {
        this.movDescription = movDescription;
        return this;
    }

    public void setMovDescription(String movDescription) {
        this.movDescription = movDescription;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Movie createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        if (movie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", doubanUrl='" + doubanUrl + "'" +
            ", rate='" + rate + "'" +
            ", coverUrl='" + coverUrl + "'" +
            ", area='" + area + "'" +
            ", language='" + language + "'" +
            ", runDate='" + runDate + "'" +
            ", term='" + term + "'" +
            ", movDescription='" + movDescription + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
