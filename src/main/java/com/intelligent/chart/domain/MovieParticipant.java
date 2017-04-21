package com.intelligent.chart.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MovieParticipant.
 */
@Entity
@Table(name = "movie_participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public MovieParticipant createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieParticipant movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public MovieParticipant person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Job getJob() {
        return job;
    }

    public MovieParticipant job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieParticipant movieParticipant = (MovieParticipant) o;
        if (movieParticipant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, movieParticipant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MovieParticipant{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
