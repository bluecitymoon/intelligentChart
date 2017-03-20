package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonNetworkDebit.
 */
@Entity
@Table(name = "person_network_debit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonNetworkDebit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PersonNetworkDebit createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Float getAmount() {
        return amount;
    }

    public PersonNetworkDebit amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Person getPerson() {
        return person;
    }

    public PersonNetworkDebit person(Person person) {
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
        PersonNetworkDebit personNetworkDebit = (PersonNetworkDebit) o;
        if (personNetworkDebit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personNetworkDebit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonNetworkDebit{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
