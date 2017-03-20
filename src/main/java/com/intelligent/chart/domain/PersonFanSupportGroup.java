package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFanSupportGroup.
 */
@Entity
@Table(name = "person_fan_support_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFanSupportGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "leader")
    private String leader;

    @Column(name = "fans_count")
    private Long fansCount;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PersonFanSupportGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public PersonFanSupportGroup leader(String leader) {
        this.leader = leader;
        return this;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Long getFansCount() {
        return fansCount;
    }

    public PersonFanSupportGroup fansCount(Long fansCount) {
        this.fansCount = fansCount;
        return this;
    }

    public void setFansCount(Long fansCount) {
        this.fansCount = fansCount;
    }

    public Person getPerson() {
        return person;
    }

    public PersonFanSupportGroup person(Person person) {
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
        PersonFanSupportGroup personFanSupportGroup = (PersonFanSupportGroup) o;
        if (personFanSupportGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personFanSupportGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonFanSupportGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", leader='" + leader + "'" +
            ", fansCount='" + fansCount + "'" +
            '}';
    }
}
