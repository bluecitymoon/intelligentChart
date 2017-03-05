package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonCreditCardActivity.
 */
@Entity
@Table(name = "person_credit_card_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonCreditCardActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    private Person person;

    @ManyToOne
    private CreditCardActivityType creditCardActivityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public PersonCreditCardActivity amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PersonCreditCardActivity createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Person getPerson() {
        return person;
    }

    public PersonCreditCardActivity person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public CreditCardActivityType getCreditCardActivityType() {
        return creditCardActivityType;
    }

    public PersonCreditCardActivity creditCardActivityType(CreditCardActivityType creditCardActivityType) {
        this.creditCardActivityType = creditCardActivityType;
        return this;
    }

    public void setCreditCardActivityType(CreditCardActivityType creditCardActivityType) {
        this.creditCardActivityType = creditCardActivityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonCreditCardActivity personCreditCardActivity = (PersonCreditCardActivity) o;
        if (personCreditCardActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personCreditCardActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonCreditCardActivity{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
