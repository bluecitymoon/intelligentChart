package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonNetworkShopping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonNetworkShopping entity.
 */
@SuppressWarnings("unused")
public interface PersonNetworkShoppingRepository extends JpaRepository<PersonNetworkShopping,Long> {

    Page<PersonNetworkShopping> findByPerson_Id(Long id, Pageable pageable);

    Page<PersonNetworkShopping> findByPerson_IdAndNetworkShoppingType_Identifier(Long id, String type, Pageable pageable);
}
