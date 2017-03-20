package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonNetworkTexiActivity.
 */
@Entity
@Table(name = "person_network_texi_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonNetworkTexiActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private NetworkTexiCompany networkTexiCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public PersonNetworkTexiActivity count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonNetworkTexiActivity person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public NetworkTexiCompany getNetworkTexiCompany() {
        return networkTexiCompany;
    }

    public PersonNetworkTexiActivity networkTexiCompany(NetworkTexiCompany networkTexiCompany) {
        this.networkTexiCompany = networkTexiCompany;
        return this;
    }

    public void setNetworkTexiCompany(NetworkTexiCompany networkTexiCompany) {
        this.networkTexiCompany = networkTexiCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonNetworkTexiActivity personNetworkTexiActivity = (PersonNetworkTexiActivity) o;
        if (personNetworkTexiActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personNetworkTexiActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonNetworkTexiActivity{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
