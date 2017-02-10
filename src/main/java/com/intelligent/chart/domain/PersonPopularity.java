package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonPopularity.
 */
@Entity
@Table(name = "person_popularity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonPopularity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    private Person person;

    @ManyToOne
    private PopularityType popularityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPercentage() {
        return percentage;
    }

    public PersonPopularity percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Person getPerson() {
        return person;
    }

    public PersonPopularity person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PopularityType getPopularityType() {
        return popularityType;
    }

    public PersonPopularity popularityType(PopularityType popularityType) {
        this.popularityType = popularityType;
        return this;
    }

    public void setPopularityType(PopularityType popularityType) {
        this.popularityType = popularityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonPopularity personPopularity = (PersonPopularity) o;
        if (personPopularity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personPopularity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonPopularity{" +
            "id=" + id +
            ", percentage='" + percentage + "'" +
            '}';
    }
}
