package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonTieBa.
 */
@Entity
@Table(name = "person_tie_ba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonTieBa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_count")
    private Integer userCount;

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "rank_description")
    private String rankDescription;

    @Column(name = "rank")
    private Integer rank;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PersonTieBa name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public PersonTieBa userCount(Integer userCount) {
        this.userCount = userCount;
        return this;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public PersonTieBa postCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public PersonTieBa shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getRankDescription() {
        return rankDescription;
    }

    public PersonTieBa rankDescription(String rankDescription) {
        this.rankDescription = rankDescription;
        return this;
    }

    public void setRankDescription(String rankDescription) {
        this.rankDescription = rankDescription;
    }

    public Integer getRank() {
        return rank;
    }

    public PersonTieBa rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Person getPerson() {
        return person;
    }

    public PersonTieBa person(Person person) {
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
        PersonTieBa personTieBa = (PersonTieBa) o;
        if (personTieBa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personTieBa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonTieBa{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", userCount='" + userCount + "'" +
            ", postCount='" + postCount + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", rankDescription='" + rankDescription + "'" +
            ", rank='" + rank + "'" +
            '}';
    }
}
