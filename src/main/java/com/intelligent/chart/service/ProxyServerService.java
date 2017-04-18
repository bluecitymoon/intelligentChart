package com.intelligent.chart.service;

import com.intelligent.chart.domain.ProxyServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ProxyServer.
 */
public interface ProxyServerService {

    /**
     * Save a proxyServer.
     *
     * @param proxyServer the entity to save
     * @return the persisted entity
     */
    ProxyServer save(ProxyServer proxyServer);

    /**
     *  Get all the proxyServers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProxyServer> findAll(Pageable pageable);

    /**
     *  Get the "id" proxyServer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProxyServer findOne(Long id);

    /**
     *  Delete the "id" proxyServer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void grabProxyServers();

    ProxyServer findRandomMostValuableProxyServer();

    void increaseSuccessCount(ProxyServer proxyServer);

    void increaseFailCount(ProxyServer proxyServer);
}
