package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFansPucharsingPower.
 */
@Entity
@Table(name = "person_fans_pucharsing_power")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFansPucharsingPower implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private FansPurchasingSection fansPurchasingSection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public PersonFansPucharsingPower count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonFansPucharsingPower person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FansPurchasingSection getFansPurchasingSection() {
        return fansPurchasingSection;
    }

    public PersonFansPucharsingPower fansPurchasingSection(FansPurchasingSection fansPurchasingSection) {
        this.fansPurchasingSection = fansPurchasingSection;
        return this;
    }

    public void setFansPurchasingSection(FansPurchasingSection fansPurchasingSection) {
        this.fansPurchasingSection = fansPurchasingSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonFansPucharsingPower personFansPucharsingPower = (PersonFansPucharsingPower) o;
        if (personFansPucharsingPower.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personFansPucharsingPower.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonFansPucharsingPower{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
