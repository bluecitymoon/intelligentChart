package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PersonEndorsement.
 */
@Entity
@Table(name = "person_endorsement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonEndorsement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seq_number")
    private Integer seqNumber;

    @Column(name = "paid_amount")
    private Float paidAmount;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "address")
    private String address;

    @Column(name = "behavior_description")
    private String behaviorDescription;

    @Column(name = "create_by")
    private Long createBy;

    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public PersonEndorsement seqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
        return this;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    public Float getPaidAmount() {
        return paidAmount;
    }

    public PersonEndorsement paidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public PersonEndorsement createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getAddress() {
        return address;
    }

    public PersonEndorsement address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public PersonEndorsement behaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
        return this;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public PersonEndorsement createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Person getPerson() {
        return person;
    }

    public PersonEndorsement person(Person person) {
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
        PersonEndorsement personEndorsement = (PersonEndorsement) o;
        if (personEndorsement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personEndorsement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonEndorsement{" +
            "id=" + id +
            ", seqNumber='" + seqNumber + "'" +
            ", paidAmount='" + paidAmount + "'" +
            ", createDate='" + createDate + "'" +
            ", address='" + address + "'" +
            ", behaviorDescription='" + behaviorDescription + "'" +
            ", createBy='" + createBy + "'" +
            '}';
    }
}
