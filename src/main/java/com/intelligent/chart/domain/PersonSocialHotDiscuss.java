package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonSocialHotDiscuss.
 */
@Entity
@Table(name = "person_social_hot_discuss")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonSocialHotDiscuss implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "article_count")
    private Integer articleCount;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public PersonSocialHotDiscuss articleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
        return this;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getMediaName() {
        return mediaName;
    }

    public PersonSocialHotDiscuss mediaName(String mediaName) {
        this.mediaName = mediaName;
        return this;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PersonSocialHotDiscuss createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public PersonSocialHotDiscuss articleCount(Integer articleCount) {
        this.articleCount = articleCount;
        return this;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Person getPerson() {
        return person;
    }

    public PersonSocialHotDiscuss person(Person person) {
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
        PersonSocialHotDiscuss personSocialHotDiscuss = (PersonSocialHotDiscuss) o;
        if (personSocialHotDiscuss.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personSocialHotDiscuss.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonSocialHotDiscuss{" +
            "id=" + id +
            ", articleTitle='" + articleTitle + "'" +
            ", mediaName='" + mediaName + "'" +
            ", createDate='" + createDate + "'" +
            ", articleCount='" + articleCount + "'" +
            '}';
    }
}
