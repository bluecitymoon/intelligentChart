package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFansEgeLevel.
 */
@Entity
@Table(name = "person_fans_ege_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFansEgeLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private EgeLevel egeLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public PersonFansEgeLevel count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonFansEgeLevel person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public EgeLevel getEgeLevel() {
        return egeLevel;
    }

    public PersonFansEgeLevel egeLevel(EgeLevel egeLevel) {
        this.egeLevel = egeLevel;
        return this;
    }

    public void setEgeLevel(EgeLevel egeLevel) {
        this.egeLevel = egeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonFansEgeLevel personFansEgeLevel = (PersonFansEgeLevel) o;
        if (personFansEgeLevel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personFansEgeLevel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonFansEgeLevel{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
