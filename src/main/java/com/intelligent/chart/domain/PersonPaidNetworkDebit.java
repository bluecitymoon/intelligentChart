package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonPaidNetworkDebit.
 */
@Entity
@Table(name = "person_paid_network_debit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonPaidNetworkDebit implements Serializable {

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

    public PersonPaidNetworkDebit createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Float getAmount() {
        return amount;
    }

    public PersonPaidNetworkDebit amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Person getPerson() {
        return person;
    }

    public PersonPaidNetworkDebit person(Person person) {
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
        PersonPaidNetworkDebit personPaidNetworkDebit = (PersonPaidNetworkDebit) o;
        if (personPaidNetworkDebit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personPaidNetworkDebit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonPaidNetworkDebit{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
