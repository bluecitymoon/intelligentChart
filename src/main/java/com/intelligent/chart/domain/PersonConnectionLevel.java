package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonConnectionLevel.
 */
@Entity
@Table(name = "person_connection_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonConnectionLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    private Person person;

    @ManyToOne
    private ConnectionLevel connectionLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPercentage() {
        return percentage;
    }

    public PersonConnectionLevel percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Person getPerson() {
        return person;
    }

    public PersonConnectionLevel person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ConnectionLevel getConnectionLevel() {
        return connectionLevel;
    }

    public PersonConnectionLevel connectionLevel(ConnectionLevel connectionLevel) {
        this.connectionLevel = connectionLevel;
        return this;
    }

    public void setConnectionLevel(ConnectionLevel connectionLevel) {
        this.connectionLevel = connectionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonConnectionLevel personConnectionLevel = (PersonConnectionLevel) o;
        if (personConnectionLevel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personConnectionLevel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonConnectionLevel{" +
            "id=" + id +
            ", percentage='" + percentage + "'" +
            '}';
    }
}
