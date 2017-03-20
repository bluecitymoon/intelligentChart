package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonRelation.
 */
@Entity
@Table(name = "person_relation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person thePerson;

    @ManyToOne
    private Person hasRelationWith;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getThePerson() {
        return thePerson;
    }

    public PersonRelation thePerson(Person person) {
        this.thePerson = person;
        return this;
    }

    public void setThePerson(Person person) {
        this.thePerson = person;
    }

    public Person getHasRelationWith() {
        return hasRelationWith;
    }

    public PersonRelation hasRelationWith(Person person) {
        this.hasRelationWith = person;
        return this;
    }

    public void setHasRelationWith(Person person) {
        this.hasRelationWith = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonRelation personRelation = (PersonRelation) o;
        if (personRelation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personRelation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonRelation{" +
            "id=" + id +
            '}';
    }
}
