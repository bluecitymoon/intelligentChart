package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "politic_role")
    private String politicRole;

    @Column(name = "support_taiwan_indepence")
    private Boolean supportTaiwanIndepence;

    @Column(name = "detail")
    private String detail;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_job",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="jobs_id", referencedColumnName="ID"))
    private Set<Job> jobs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public Person sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Person birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public Person birthplace(String birthplace) {
        this.birthplace = birthplace;
        return this;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getPoliticRole() {
        return politicRole;
    }

    public Person politicRole(String politicRole) {
        this.politicRole = politicRole;
        return this;
    }

    public void setPoliticRole(String politicRole) {
        this.politicRole = politicRole;
    }

    public Boolean isSupportTaiwanIndepence() {
        return supportTaiwanIndepence;
    }

    public Person supportTaiwanIndepence(Boolean supportTaiwanIndepence) {
        this.supportTaiwanIndepence = supportTaiwanIndepence;
        return this;
    }

    public void setSupportTaiwanIndepence(Boolean supportTaiwanIndepence) {
        this.supportTaiwanIndepence = supportTaiwanIndepence;
    }

    public String getDetail() {
        return detail;
    }

    public Person detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAvatar() {
        return avatar;
    }

    public Person avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Person jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Person addJob(Job job) {
        jobs.add(job);
        job.getPeople().add(this);
        return this;
    }

    public Person removeJob(Job job) {
        jobs.remove(job);
        job.getPeople().remove(this);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", sex='" + sex + "'" +
            ", age='" + age + "'" +
            ", birthday='" + birthday + "'" +
            ", birthplace='" + birthplace + "'" +
            ", politicRole='" + politicRole + "'" +
            ", supportTaiwanIndepence='" + supportTaiwanIndepence + "'" +
            ", detail='" + detail + "'" +
            ", avatar='" + avatar + "'" +
            '}';
    }
}
