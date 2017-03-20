package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFansHobby.
 */
@Entity
@Table(name = "person_fans_hobby")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFansHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Long count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Hobby hobby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public PersonFansHobby count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonFansHobby person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public PersonFansHobby hobby(Hobby hobby) {
        this.hobby = hobby;
        return this;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonFansHobby personFansHobby = (PersonFansHobby) o;
        if (personFansHobby.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personFansHobby.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonFansHobby{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
