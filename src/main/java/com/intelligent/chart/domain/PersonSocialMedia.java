package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonSocialMedia.
 */
@Entity
@Table(name = "person_social_media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonSocialMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    private Person person;

    @ManyToOne
    private SocialMediaType socialMediaType;

    @ManyToOne
    private SocialMediaAttributeName socialMediaAttributeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public PersonSocialMedia attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Person getPerson() {
        return person;
    }

    public PersonSocialMedia person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public SocialMediaType getSocialMediaType() {
        return socialMediaType;
    }

    public PersonSocialMedia socialMediaType(SocialMediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
        return this;
    }

    public void setSocialMediaType(SocialMediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }

    public SocialMediaAttributeName getSocialMediaAttributeName() {
        return socialMediaAttributeName;
    }

    public PersonSocialMedia socialMediaAttributeName(SocialMediaAttributeName socialMediaAttributeName) {
        this.socialMediaAttributeName = socialMediaAttributeName;
        return this;
    }

    public void setSocialMediaAttributeName(SocialMediaAttributeName socialMediaAttributeName) {
        this.socialMediaAttributeName = socialMediaAttributeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonSocialMedia personSocialMedia = (PersonSocialMedia) o;
        if (personSocialMedia.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personSocialMedia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonSocialMedia{" +
            "id=" + id +
            ", attributeValue='" + attributeValue + "'" +
            '}';
    }
}
