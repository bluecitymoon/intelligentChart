package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonEducationBackground.
 */
@Entity
@Table(name = "person_education_background")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonEducationBackground implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private EducationBackgroundType educationBackgroundType;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PersonEducationBackground startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PersonEducationBackground endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public PersonEducationBackground description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EducationBackgroundType getEducationBackgroundType() {
        return educationBackgroundType;
    }

    public PersonEducationBackground educationBackgroundType(EducationBackgroundType educationBackgroundType) {
        this.educationBackgroundType = educationBackgroundType;
        return this;
    }

    public void setEducationBackgroundType(EducationBackgroundType educationBackgroundType) {
        this.educationBackgroundType = educationBackgroundType;
    }

    public Person getPerson() {
        return person;
    }

    public PersonEducationBackground person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonEducationBackground personEducationBackground = (PersonEducationBackground) o;
        if (personEducationBackground.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personEducationBackground.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonEducationBackground{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
