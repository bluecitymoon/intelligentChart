package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonLawBusiness.
 */
@Entity
@Table(name = "person_law_business")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonLawBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descriptions")
    private String descriptions;

    @ManyToOne
    private Person person;

    @ManyToOne
    private LawBusinessType lawBusinessType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public PersonLawBusiness descriptions(String descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Person getPerson() {
        return person;
    }

    public PersonLawBusiness person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LawBusinessType getLawBusinessType() {
        return lawBusinessType;
    }

    public PersonLawBusiness lawBusinessType(LawBusinessType lawBusinessType) {
        this.lawBusinessType = lawBusinessType;
        return this;
    }

    public void setLawBusinessType(LawBusinessType lawBusinessType) {
        this.lawBusinessType = lawBusinessType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonLawBusiness personLawBusiness = (PersonLawBusiness) o;
        if (personLawBusiness.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personLawBusiness.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonLawBusiness{" +
            "id=" + id +
            ", descriptions='" + descriptions + "'" +
            '}';
    }
}
