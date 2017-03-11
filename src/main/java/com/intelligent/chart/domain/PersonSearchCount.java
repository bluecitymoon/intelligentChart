package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonSearchCount.
 */
@Entity
@Table(name = "person_search_count")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonSearchCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "search_date")
    private LocalDate searchDate;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public PersonSearchCount searchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
        return this;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public Integer getCount() {
        return count;
    }

    public PersonSearchCount count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonSearchCount person(Person person) {
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
        PersonSearchCount personSearchCount = (PersonSearchCount) o;
        if (personSearchCount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personSearchCount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonSearchCount{" +
            "id=" + id +
            ", searchDate='" + searchDate + "'" +
            ", count='" + count + "'" +
            '}';
    }
}
