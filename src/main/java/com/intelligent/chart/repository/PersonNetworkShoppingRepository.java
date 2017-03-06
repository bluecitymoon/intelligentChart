package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonNetworkShopping;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonNetworkShopping entity.
 */
@SuppressWarnings("unused")
public interface PersonNetworkShoppingRepository extends JpaRepository<PersonNetworkShopping,Long> {

}
