package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RobotMovieSubjectSuccessPage.
 */
@Entity
@Table(name = "robot_movie_subject_success_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RobotMovieSubjectSuccessPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public RobotMovieSubjectSuccessPage tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public RobotMovieSubjectSuccessPage pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public RobotMovieSubjectSuccessPage createDate(ZonedDateTime createDate) {
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
        RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage = (RobotMovieSubjectSuccessPage) o;
        if (robotMovieSubjectSuccessPage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, robotMovieSubjectSuccessPage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RobotMovieSubjectSuccessPage{" +
            "id=" + id +
            ", tag='" + tag + "'" +
            ", pageNumber='" + pageNumber + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
