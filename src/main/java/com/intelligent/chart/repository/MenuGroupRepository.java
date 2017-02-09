package com.intelligent.chart.repository;

import com.intelligent.chart.domain.MenuGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MenuGroup entity.
 */
@SuppressWarnings("unused")
public interface MenuGroupRepository extends JpaRepository<MenuGroup,Long> {

}
