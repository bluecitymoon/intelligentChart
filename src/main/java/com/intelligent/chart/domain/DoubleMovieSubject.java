package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DoubleMovieSubject.
 */
@Entity
@Table(name = "double_movie_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoubleMovieSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Size(max = 3000)
    @Column(name = "url", length = 3000)
    private String url;

    @Size(max = 3000)
    @Column(name = "cover", length = 3000)
    private String cover;

    @Column(name = "rate")
    private String rate;

    @Column(name = "douban_id")
    private String doubanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DoubleMovieSubject title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public DoubleMovieSubject url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public DoubleMovieSubject cover(String cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRate() {
        return rate;
    }

    public DoubleMovieSubject rate(String rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDoubanId() {
        return doubanId;
    }

    public DoubleMovieSubject doubanId(String doubanId) {
        this.doubanId = doubanId;
        return this;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DoubleMovieSubject doubleMovieSubject = (DoubleMovieSubject) o;
        if (doubleMovieSubject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, doubleMovieSubject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DoubleMovieSubject{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", url='" + url + "'" +
            ", cover='" + cover + "'" +
            ", rate='" + rate + "'" +
            ", doubanId='" + doubanId + "'" +
            '}';
    }
}
