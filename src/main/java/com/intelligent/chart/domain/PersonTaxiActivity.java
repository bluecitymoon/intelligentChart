package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PersonTaxiActivity.
 */
@Entity
@Table(name = "person_taxi_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonTaxiActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_place")
    private String startPlace;

    @Column(name = "destination")
    private String destination;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "paid_amount")
    private Float paidAmount;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public PersonTaxiActivity startPlace(String startPlace) {
        this.startPlace = startPlace;
        return this;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getDestination() {
        return destination;
    }

    public PersonTaxiActivity destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public PersonTaxiActivity createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Float getPaidAmount() {
        return paidAmount;
    }

    public PersonTaxiActivity paidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Person getPerson() {
        return person;
    }

    public PersonTaxiActivity person(Person person) {
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
        PersonTaxiActivity personTaxiActivity = (PersonTaxiActivity) o;
        if (personTaxiActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personTaxiActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonTaxiActivity{" +
            "id=" + id +
            ", startPlace='" + startPlace + "'" +
            ", destination='" + destination + "'" +
            ", createDate='" + createDate + "'" +
            ", paidAmount='" + paidAmount + "'" +
            '}';
    }
}
