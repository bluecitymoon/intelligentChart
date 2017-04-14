package com.intelligent.chart.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RobotMovieSubjectFailPage.
 */
@Entity
@Table(name = "robot_movie_subject_fail_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class RobotMovieSubjectFailPage implements Serializable {

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

    @Size(max = 10000)
    @Column(name = "reason", length = 10000)
    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public RobotMovieSubjectFailPage tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public RobotMovieSubjectFailPage pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public RobotMovieSubjectFailPage createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getReason() {
        return reason;
    }

    public RobotMovieSubjectFailPage reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RobotMovieSubjectFailPage robotMovieSubjectFailPage = (RobotMovieSubjectFailPage) o;
        if (robotMovieSubjectFailPage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, robotMovieSubjectFailPage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RobotMovieSubjectFailPage{" +
            "id=" + id +
            ", tag='" + tag + "'" +
            ", pageNumber='" + pageNumber + "'" +
            ", createDate='" + createDate + "'" +
            ", reason='" + reason + "'" +
            '}';
    }
}
