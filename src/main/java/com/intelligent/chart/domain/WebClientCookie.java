package com.intelligent.chart.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A WebClientCookie.
 */
@Entity
@Table(name = "web_client_cookie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebClientCookie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "domain")
    private String domain;

    @Column(name = "path")
    private String path;

    @Column(name = "secure")
    private Boolean secure;

    @Column(name = "http_only")
    private Boolean httpOnly;

    @Column(name = "expires")
    private Date expires;

    @ManyToOne
    private Website website;

    @ManyToOne
    private ProxyServer proxyServer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WebClientCookie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public WebClientCookie value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public WebClientCookie domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public WebClientCookie path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isSecure() {
        return secure;
    }

    public WebClientCookie secure(Boolean secure) {
        this.secure = secure;
        return this;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean isHttpOnly() {
        return httpOnly;
    }

    public WebClientCookie httpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public Date getExpires() {
        return expires;
    }

    public WebClientCookie expires(Date expires) {
        this.expires = expires;
        return this;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Website getWebsite() {
        return website;
    }

    public WebClientCookie website(Website website) {
        this.website = website;
        return this;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public WebClientCookie proxyServer(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
        return this;
    }

    public void setProxyServer(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebClientCookie webClientCookie = (WebClientCookie) o;
        if (webClientCookie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webClientCookie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WebClientCookie{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", domain='" + domain + "'" +
            ", path='" + path + "'" +
            ", secure='" + secure + "'" +
            ", httpOnly='" + httpOnly + "'" +
            ", expires='" + expires + "'" +
            '}';
    }
}
