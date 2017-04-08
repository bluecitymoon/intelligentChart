package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.intelligent.chart.domain.enumeration.RobotStatus;

/**
 * A Robot.
 */
@Entity
@Table(name = "robot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Robot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 5000)
    @Column(name = "robot_description", length = 5000)
    private String robotDescription;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "last_start")
    private ZonedDateTime lastStart;

    @Column(name = "last_stop")
    private ZonedDateTime lastStop;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RobotStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Robot name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRobotDescription() {
        return robotDescription;
    }

    public Robot robotDescription(String robotDescription) {
        this.robotDescription = robotDescription;
        return this;
    }

    public void setRobotDescription(String robotDescription) {
        this.robotDescription = robotDescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Robot identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ZonedDateTime getLastStart() {
        return lastStart;
    }

    public Robot lastStart(ZonedDateTime lastStart) {
        this.lastStart = lastStart;
        return this;
    }

    public void setLastStart(ZonedDateTime lastStart) {
        this.lastStart = lastStart;
    }

    public ZonedDateTime getLastStop() {
        return lastStop;
    }

    public Robot lastStop(ZonedDateTime lastStop) {
        this.lastStop = lastStop;
        return this;
    }

    public void setLastStop(ZonedDateTime lastStop) {
        this.lastStop = lastStop;
    }

    public RobotStatus getStatus() {
        return status;
    }

    public Robot status(RobotStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RobotStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Robot robot = (Robot) o;
        if (robot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, robot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Robot{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", robotDescription='" + robotDescription + "'" +
            ", identifier='" + identifier + "'" +
            ", lastStart='" + lastStart + "'" +
            ", lastStop='" + lastStop + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
