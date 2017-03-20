package com.intelligent.chart.repository;

import com.intelligent.chart.domain.NetworkShoppingType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NetworkShoppingType entity.
 */
@SuppressWarnings("unused")
public interface NetworkShoppingTypeRepository extends JpaRepository<NetworkShoppingType,Long> {

}
