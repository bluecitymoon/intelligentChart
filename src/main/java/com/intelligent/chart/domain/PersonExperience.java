package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonExperience.
 */
@Entity
@Table(name = "person_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "exp_year")
    private String expYear;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpYear() {
        return expYear;
    }

    public PersonExperience expYear(String expYear) {
        this.expYear = expYear;
        return this;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getDescription() {
        return description;
    }

    public PersonExperience description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public PersonExperience person(Person person) {
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
        PersonExperience personExperience = (PersonExperience) o;
        if (personExperience.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personExperience.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonExperience{" +
            "id=" + id +
            ", expYear='" + expYear + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
