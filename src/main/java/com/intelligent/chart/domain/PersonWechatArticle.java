package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonWechatArticle.
 */
@Entity
@Table(name = "person_wechat_article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonWechatArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private Long count;

    @Column(name = "incread_by_last_month")
    private Integer increadByLastMonth;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public PersonWechatArticle count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getIncreadByLastMonth() {
        return increadByLastMonth;
    }

    public PersonWechatArticle increadByLastMonth(Integer increadByLastMonth) {
        this.increadByLastMonth = increadByLastMonth;
        return this;
    }

    public void setIncreadByLastMonth(Integer increadByLastMonth) {
        this.increadByLastMonth = increadByLastMonth;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PersonWechatArticle createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Person getPerson() {
        return person;
    }

    public PersonWechatArticle person(Person person) {
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
        PersonWechatArticle personWechatArticle = (PersonWechatArticle) o;
        if (personWechatArticle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personWechatArticle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonWechatArticle{" +
            "id=" + id +
            ", count='" + count + "'" +
            ", increadByLastMonth='" + increadByLastMonth + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
