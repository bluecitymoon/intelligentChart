package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonPrize.
 */
@Entity
@Table(name = "person_prize")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonPrize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "prize_date")
    private LocalDate prizeDate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private PrizeType prizeType;

    @ManyToOne
    private PrizeGroup prizeGroup;

    @ManyToOne
    private PrizeLevel prizeLevel;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPrizeDate() {
        return prizeDate;
    }

    public PersonPrize prizeDate(LocalDate prizeDate) {
        this.prizeDate = prizeDate;
        return this;
    }

    public void setPrizeDate(LocalDate prizeDate) {
        this.prizeDate = prizeDate;
    }

    public String getName() {
        return name;
    }

    public PersonPrize name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrizeType getPrizeType() {
        return prizeType;
    }

    public PersonPrize prizeType(PrizeType prizeType) {
        this.prizeType = prizeType;
        return this;
    }

    public void setPrizeType(PrizeType prizeType) {
        this.prizeType = prizeType;
    }

    public PrizeGroup getPrizeGroup() {
        return prizeGroup;
    }

    public PersonPrize prizeGroup(PrizeGroup prizeGroup) {
        this.prizeGroup = prizeGroup;
        return this;
    }

    public void setPrizeGroup(PrizeGroup prizeGroup) {
        this.prizeGroup = prizeGroup;
    }

    public PrizeLevel getPrizeLevel() {
        return prizeLevel;
    }

    public PersonPrize prizeLevel(PrizeLevel prizeLevel) {
        this.prizeLevel = prizeLevel;
        return this;
    }

    public void setPrizeLevel(PrizeLevel prizeLevel) {
        this.prizeLevel = prizeLevel;
    }

    public Person getPerson() {
        return person;
    }

    public PersonPrize person(Person person) {
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
        PersonPrize personPrize = (PersonPrize) o;
        if (personPrize.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personPrize.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonPrize{" +
            "id=" + id +
            ", prizeDate='" + prizeDate + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
