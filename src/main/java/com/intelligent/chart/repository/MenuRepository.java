package com.intelligent.chart.repository;

import com.intelligent.chart.domain.Menu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Menu entity.
 */
@SuppressWarnings("unused")
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByMenuGroup_Id(Long menuGroupId);

}
