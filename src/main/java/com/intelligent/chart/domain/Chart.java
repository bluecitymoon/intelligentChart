package com.intelligent.chart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Chart.
 */
@Entity
@Table(name = "chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "type")
    private String type;

    @Column(name = "data_source_sql")
    private String dataSourceSql;

    @Column(name = "title_sql")
    private String titleSql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Chart identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public Chart type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataSourceSql() {
        return dataSourceSql;
    }

    public Chart dataSourceSql(String dataSourceSql) {
        this.dataSourceSql = dataSourceSql;
        return this;
    }

    public void setDataSourceSql(String dataSourceSql) {
        this.dataSourceSql = dataSourceSql;
    }

    public String getTitleSql() {
        return titleSql;
    }

    public Chart titleSql(String titleSql) {
        this.titleSql = titleSql;
        return this;
    }

    public void setTitleSql(String titleSql) {
        this.titleSql = titleSql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chart chart = (Chart) o;
        if (chart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chart{" +
            "id=" + id +
            ", identifier='" + identifier + "'" +
            ", type='" + type + "'" +
            ", dataSourceSql='" + dataSourceSql + "'" +
            ", titleSql='" + titleSql + "'" +
            '}';
    }
}
