package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MovieSuccessLog.
 */
@Entity
@Table(name = "movie_success_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieSuccessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "movie_id")
    private Long movieId;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "douban_id")
    private String doubanId;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public MovieSuccessLog movieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public MovieSuccessLog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoubanId() {
        return doubanId;
    }

    public MovieSuccessLog doubanId(String doubanId) {
        this.doubanId = doubanId;
        return this;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public MovieSuccessLog createDate(ZonedDateTime createDate) {
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
        MovieSuccessLog movieSuccessLog = (MovieSuccessLog) o;
        if (movieSuccessLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, movieSuccessLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MovieSuccessLog{" +
            "id=" + id +
            ", movieId='" + movieId + "'" +
            ", name='" + name + "'" +
            ", doubanId='" + doubanId + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
