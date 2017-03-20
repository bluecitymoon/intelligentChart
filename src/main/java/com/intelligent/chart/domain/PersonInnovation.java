package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonInnovation.
 */
@Entity
@Table(name = "person_innovation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonInnovation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    private Person person;

    @ManyToOne
    private InnovationType innovationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPercentage() {
        return percentage;
    }

    public PersonInnovation percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Person getPerson() {
        return person;
    }

    public PersonInnovation person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public InnovationType getInnovationType() {
        return innovationType;
    }

    public PersonInnovation innovationType(InnovationType innovationType) {
        this.innovationType = innovationType;
        return this;
    }

    public void setInnovationType(InnovationType innovationType) {
        this.innovationType = innovationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonInnovation personInnovation = (PersonInnovation) o;
        if (personInnovation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personInnovation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonInnovation{" +
            "id=" + id +
            ", percentage='" + percentage + "'" +
            '}';
    }
}
