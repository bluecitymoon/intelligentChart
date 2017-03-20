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
 * A WordCloud.
 */
@Entity
@Table(name = "word_cloud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WordCloud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "wordCloud")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonWordCloud> personWordClouds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WordCloud name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PersonWordCloud> getPersonWordClouds() {
        return personWordClouds;
    }

    public WordCloud personWordClouds(Set<PersonWordCloud> personWordClouds) {
        this.personWordClouds = personWordClouds;
        return this;
    }

    public WordCloud addPersonWordCloud(PersonWordCloud personWordCloud) {
        personWordClouds.add(personWordCloud);
        personWordCloud.setWordCloud(this);
        return this;
    }

    public WordCloud removePersonWordCloud(PersonWordCloud personWordCloud) {
        personWordClouds.remove(personWordCloud);
        personWordCloud.setWordCloud(null);
        return this;
    }

    public void setPersonWordClouds(Set<PersonWordCloud> personWordClouds) {
        this.personWordClouds = personWordClouds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WordCloud wordCloud = (WordCloud) o;
        if (wordCloud.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wordCloud.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WordCloud{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
