package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonAreaPercentage.
 */
@Entity
@Table(name = "person_area_percentage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonAreaPercentage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    private Person person;

    @ManyToOne
    private MediaType mediaType;

    @ManyToOne
    private AreaType areaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPercentage() {
        return percentage;
    }

    public PersonAreaPercentage percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Person getPerson() {
        return person;
    }

    public PersonAreaPercentage person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public PersonAreaPercentage mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public PersonAreaPercentage areaType(AreaType areaType) {
        this.areaType = areaType;
        return this;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonAreaPercentage personAreaPercentage = (PersonAreaPercentage) o;
        if (personAreaPercentage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personAreaPercentage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonAreaPercentage{" +
            "id=" + id +
            ", percentage='" + percentage + "'" +
            '}';
    }
}
