package com.intelligent.chart.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jerry on 2017/2/2.
 */
public class MenuGroupVo implements Serializable {

    private Long id;

    private String title;

    private String icon;

    private Set<MenuVo> menus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<MenuVo> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuVo> menus) {
        this.menus = menus;
    }
}
