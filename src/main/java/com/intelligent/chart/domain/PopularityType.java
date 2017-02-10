package com.intelligent.chart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PopularityType.
 */
@Entity
@Table(name = "popularity_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopularityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "popularityType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonPopularity> personPopularities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public PopularityType identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public PopularityType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PersonPopularity> getPersonPopularities() {
        return personPopularities;
    }

    public PopularityType personPopularities(Set<PersonPopularity> personPopularities) {
        this.personPopularities = personPopularities;
        return this;
    }

    public PopularityType addPersonPopularity(PersonPopularity personPopularity) {
        personPopularities.add(personPopularity);
        personPopularity.setPopularityType(this);
        return this;
    }

    public PopularityType removePersonPopularity(PersonPopularity personPopularity) {
        personPopularities.remove(personPopularity);
        personPopularity.setPopularityType(null);
        return this;
    }

    public void setPersonPopularities(Set<PersonPopularity> personPopularities) {
        this.personPopularities = personPopularities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopularityType popularityType = (PopularityType) o;
        if (popularityType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popularityType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopularityType{" +
            "id=" + id +
            ", identifier='" + identifier + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
