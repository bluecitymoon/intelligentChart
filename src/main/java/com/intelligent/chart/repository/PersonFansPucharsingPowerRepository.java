package com.intelligent.chart.repository;

import com.intelligent.chart.domain.PersonFansPucharsingPower;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonFansPucharsingPower entity.
 */
@SuppressWarnings("unused")
public interface PersonFansPucharsingPowerRepository extends JpaRepository<PersonFansPucharsingPower,Long> {
    List<PersonFansPucharsingPower> findByPerson_Id(Long id);


}
