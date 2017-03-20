package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonIncome.
 */
@Entity
@Table(name = "person_income")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonIncome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "in_country_salary_total")
    private Float inCountrySalaryTotal;

    @Column(name = "in_country_plus_box_total")
    private Float inCountryPlusBoxTotal;

    @Column(name = "out_country_salary_total")
    private Float outCountrySalaryTotal;

    @Column(name = "out_country_plus_box_total")
    private Float outCountryPlusBoxTotal;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public PersonIncome year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Float getInCountrySalaryTotal() {
        return inCountrySalaryTotal;
    }

    public PersonIncome inCountrySalaryTotal(Float inCountrySalaryTotal) {
        this.inCountrySalaryTotal = inCountrySalaryTotal;
        return this;
    }

    public void setInCountrySalaryTotal(Float inCountrySalaryTotal) {
        this.inCountrySalaryTotal = inCountrySalaryTotal;
    }

    public Float getInCountryPlusBoxTotal() {
        return inCountryPlusBoxTotal;
    }

    public PersonIncome inCountryPlusBoxTotal(Float inCountryPlusBoxTotal) {
        this.inCountryPlusBoxTotal = inCountryPlusBoxTotal;
        return this;
    }

    public void setInCountryPlusBoxTotal(Float inCountryPlusBoxTotal) {
        this.inCountryPlusBoxTotal = inCountryPlusBoxTotal;
    }

    public Float getOutCountrySalaryTotal() {
        return outCountrySalaryTotal;
    }

    public PersonIncome outCountrySalaryTotal(Float outCountrySalaryTotal) {
        this.outCountrySalaryTotal = outCountrySalaryTotal;
        return this;
    }

    public void setOutCountrySalaryTotal(Float outCountrySalaryTotal) {
        this.outCountrySalaryTotal = outCountrySalaryTotal;
    }

    public Float getOutCountryPlusBoxTotal() {
        return outCountryPlusBoxTotal;
    }

    public PersonIncome outCountryPlusBoxTotal(Float outCountryPlusBoxTotal) {
        this.outCountryPlusBoxTotal = outCountryPlusBoxTotal;
        return this;
    }

    public void setOutCountryPlusBoxTotal(Float outCountryPlusBoxTotal) {
        this.outCountryPlusBoxTotal = outCountryPlusBoxTotal;
    }

    public Person getPerson() {
        return person;
    }

    public PersonIncome person(Person person) {
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
        PersonIncome personIncome = (PersonIncome) o;
        if (personIncome.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personIncome.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonIncome{" +
            "id=" + id +
            ", year='" + year + "'" +
            ", inCountrySalaryTotal='" + inCountrySalaryTotal + "'" +
            ", inCountryPlusBoxTotal='" + inCountryPlusBoxTotal + "'" +
            ", outCountrySalaryTotal='" + outCountrySalaryTotal + "'" +
            ", outCountryPlusBoxTotal='" + outCountryPlusBoxTotal + "'" +
            '}';
    }
}
