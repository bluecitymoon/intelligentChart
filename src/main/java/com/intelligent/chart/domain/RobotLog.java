package com.intelligent.chart.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.intelligent.chart.domain.enumeration.RobotLogLevel;

/**
 * A RobotLog.
 */
@Entity
@Table(name = "robot_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RobotLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private RobotLogLevel level;

    @Size(max = 20000)
    @Column(name = "log_content", length = 20000)
    private String logContent;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @ManyToOne
    private Robot robot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RobotLogLevel getLevel() {
        return level;
    }

    public RobotLog level(RobotLogLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(RobotLogLevel level) {
        this.level = level;
    }

    public String getLogContent() {
        return logContent;
    }

    public RobotLog logContent(String logContent) {
        this.logContent = logContent;
        return this;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public RobotLog createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Robot getRobot() {
        return robot;
    }

    public RobotLog robot(Robot robot) {
        this.robot = robot;
        return this;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RobotLog robotLog = (RobotLog) o;
        if (robotLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, robotLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RobotLog{" +
            "id=" + id +
            ", level='" + level + "'" +
            ", logContent='" + logContent + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
