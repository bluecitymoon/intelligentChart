package com.intelligent.chart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MenuGroup.
 */
@Entity
@Table(name = "menu_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenuGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "icon")
    private String icon;

    @Column(name = "seq_order")
    private Integer seqOrder;

    @OneToMany(mappedBy = "menuGroup")
   // @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Menu> menus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MenuGroup title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public MenuGroup icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSeqOrder() {
        return seqOrder;
    }

    public MenuGroup seqOrder(Integer seqOrder) {
        this.seqOrder = seqOrder;
        return this;
    }

    public void setSeqOrder(Integer seqOrder) {
        this.seqOrder = seqOrder;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public MenuGroup menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public MenuGroup addMenu(Menu menu) {
        menus.add(menu);
        menu.setMenuGroup(this);
        return this;
    }

    public MenuGroup removeMenu(Menu menu) {
        menus.remove(menu);
        menu.setMenuGroup(null);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuGroup menuGroup = (MenuGroup) o;
        if (menuGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, menuGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuGroup{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", icon='" + icon + "'" +
            ", seqOrder='" + seqOrder + "'" +
            '}';
    }
}
