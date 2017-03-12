package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFanPaymentTool.
 */
@Entity
@Table(name = "person_fan_payment_tool")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFanPaymentTool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Long count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private PaymentTool paymentTool;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public PersonFanPaymentTool count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonFanPaymentTool person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PaymentTool getPaymentTool() {
        return paymentTool;
    }

    public PersonFanPaymentTool paymentTool(PaymentTool paymentTool) {
        this.paymentTool = paymentTool;
        return this;
    }

    public void setPaymentTool(PaymentTool paymentTool) {
        this.paymentTool = paymentTool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonFanPaymentTool personFanPaymentTool = (PersonFanPaymentTool) o;
        if (personFanPaymentTool.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personFanPaymentTool.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonFanPaymentTool{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
