package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonWordCloud.
 */
@Entity
@Table(name = "person_word_cloud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonWordCloud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Person person;

    @ManyToOne
    private WordCloud wordCloud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public PersonWordCloud count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Person getPerson() {
        return person;
    }

    public PersonWordCloud person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public WordCloud getWordCloud() {
        return wordCloud;
    }

    public PersonWordCloud wordCloud(WordCloud wordCloud) {
        this.wordCloud = wordCloud;
        return this;
    }

    public void setWordCloud(WordCloud wordCloud) {
        this.wordCloud = wordCloud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonWordCloud personWordCloud = (PersonWordCloud) o;
        if (personWordCloud.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personWordCloud.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonWordCloud{" +
            "id=" + id +
            ", count='" + count + "'" +
            '}';
    }
}
