package com.intelligent.chart.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "logo")
    private String logo;

    @Column(name = "visibile")
    private Boolean visibile;

    @ManyToOne
   // @JsonIgnore
    private MenuGroup menuGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Menu title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Menu identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLogo() {
        return logo;
    }

    public Menu logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean isVisibile() {
        return visibile;
    }

    public Menu visibile(Boolean visibile) {
        this.visibile = visibile;
        return this;
    }

    public void setVisibile(Boolean visibile) {
        this.visibile = visibile;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public Menu menuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
        return this;
    }

    public void setMenuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        if (menu.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", identifier='" + identifier + "'" +
            ", logo='" + logo + "'" +
            ", visibile='" + visibile + "'" +
            '}';
    }
}
