package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonNetworkShopping.
 */
@Entity
@Table(name = "person_network_shopping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonNetworkShopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Person person;

    @ManyToOne
    private NetworkShoppingType networkShoppingType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PersonNetworkShopping createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Float getAmount() {
        return amount;
    }

    public PersonNetworkShopping amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public PersonNetworkShopping description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public PersonNetworkShopping person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public NetworkShoppingType getNetworkShoppingType() {
        return networkShoppingType;
    }

    public PersonNetworkShopping networkShoppingType(NetworkShoppingType networkShoppingType) {
        this.networkShoppingType = networkShoppingType;
        return this;
    }

    public void setNetworkShoppingType(NetworkShoppingType networkShoppingType) {
        this.networkShoppingType = networkShoppingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonNetworkShopping personNetworkShopping = (PersonNetworkShopping) o;
        if (personNetworkShopping.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personNetworkShopping.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonNetworkShopping{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", amount='" + amount + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
