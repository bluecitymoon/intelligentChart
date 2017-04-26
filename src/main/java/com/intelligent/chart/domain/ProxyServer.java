package com.intelligent.chart.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.intelligent.chart.domain.enumeration.ProxyServerCategory;

/**
 * A ProxyServer.
 */
@Entity
@Table(name = "proxy_server")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "address", "port"})
@ToString
public class ProxyServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "port")
    private Integer port;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProxyServerCategory category;

    @Column(name = "anonymous_level")
    private String anonymousLevel;

    @Column(name = "http_type")
    private String httpType;

    @Column(name = "location")
    private String location;

    @Column(name = "response_second")
    private Float responseSecond;

    @Column(name = "last_validation_date")
    private ZonedDateTime lastValidationDate;

    @Column(name = "total_success_count")
    private Integer totalSuccessCount;

    @Column(name = "total_fail_count")
    private Integer totalFailCount;

    @Column(name = "last_success_date")
    private ZonedDateTime lastSuccessDate;

    @Column(name = "last_fail_date")
    private ZonedDateTime lastFailDate;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "is_reachable")
    private Boolean isReachable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public ProxyServer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public ProxyServer port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ProxyServerCategory getCategory() {
        return category;
    }

    public ProxyServer category(ProxyServerCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(ProxyServerCategory category) {
        this.category = category;
    }

    public String getAnonymousLevel() {
        return anonymousLevel;
    }

    public ProxyServer anonymousLevel(String anonymousLevel) {
        this.anonymousLevel = anonymousLevel;
        return this;
    }

    public void setAnonymousLevel(String anonymousLevel) {
        this.anonymousLevel = anonymousLevel;
    }

    public String getHttpType() {
        return httpType;
    }

    public ProxyServer httpType(String httpType) {
        this.httpType = httpType;
        return this;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public String getLocation() {
        return location;
    }

    public ProxyServer location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getResponseSecond() {
        return responseSecond;
    }

    public ProxyServer responseSecond(Float responseSecond) {
        this.responseSecond = responseSecond;
        return this;
    }

    public void setResponseSecond(Float responseSecond) {
        this.responseSecond = responseSecond;
    }

    public ZonedDateTime getLastValidationDate() {
        return lastValidationDate;
    }

    public ProxyServer lastValidationDate(ZonedDateTime lastValidationDate) {
        this.lastValidationDate = lastValidationDate;
        return this;
    }

    public void setLastValidationDate(ZonedDateTime lastValidationDate) {
        this.lastValidationDate = lastValidationDate;
    }

    public Integer getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public ProxyServer totalSuccessCount(Integer totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
        return this;
    }

    public void setTotalSuccessCount(Integer totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public Integer getTotalFailCount() {
        return totalFailCount;
    }

    public ProxyServer totalFailCount(Integer totalFailCount) {
        this.totalFailCount = totalFailCount;
        return this;
    }

    public void setTotalFailCount(Integer totalFailCount) {
        this.totalFailCount = totalFailCount;
    }

    public ZonedDateTime getLastSuccessDate() {
        return lastSuccessDate;
    }

    public ProxyServer lastSuccessDate(ZonedDateTime lastSuccessDate) {
        this.lastSuccessDate = lastSuccessDate;
        return this;
    }

    public void setLastSuccessDate(ZonedDateTime lastSuccessDate) {
        this.lastSuccessDate = lastSuccessDate;
    }

    public ZonedDateTime getLastFailDate() {
        return lastFailDate;
    }

    public ProxyServer lastFailDate(ZonedDateTime lastFailDate) {
        this.lastFailDate = lastFailDate;
        return this;
    }

    public void setLastFailDate(ZonedDateTime lastFailDate) {
        this.lastFailDate = lastFailDate;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public ProxyServer createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean isIsReachable() {
        return isReachable;
    }

    public ProxyServer isReachable(Boolean isReachable) {
        this.isReachable = isReachable;
        return this;
    }

    public void setIsReachable(Boolean isReachable) {
        this.isReachable = isReachable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProxyServer proxyServer = (ProxyServer) o;
        if (proxyServer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proxyServer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProxyServer{" +
            "id=" + id +
            ", address='" + address + "'" +
            ", port='" + port + "'" +
            ", category='" + category + "'" +
            ", anonymousLevel='" + anonymousLevel + "'" +
            ", httpType='" + httpType + "'" +
            ", location='" + location + "'" +
            ", responseSecond='" + responseSecond + "'" +
            ", lastValidationDate='" + lastValidationDate + "'" +
            ", totalSuccessCount='" + totalSuccessCount + "'" +
            ", totalFailCount='" + totalFailCount + "'" +
            ", lastSuccessDate='" + lastSuccessDate + "'" +
            ", lastFailDate='" + lastFailDate + "'" +
            ", createDate='" + createDate + "'" +
            ", isReachable='" + isReachable + "'" +
            '}';
    }
}
