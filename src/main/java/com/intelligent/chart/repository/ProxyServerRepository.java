package com.intelligent.chart.repository;

import com.intelligent.chart.domain.ProxyServer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProxyServer entity.
 */
@SuppressWarnings("unused")
public interface ProxyServerRepository extends JpaRepository<ProxyServer,Long> {

    ProxyServer findByAddress(String address);

}
