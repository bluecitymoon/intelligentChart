package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.ChartMenuService;
import com.intelligent.chart.domain.ChartMenu;
import com.intelligent.chart.repository.ChartMenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ChartMenu.
 */
@Service
@Transactional
public class ChartMenuServiceImpl implements ChartMenuService{

    private final Logger log = LoggerFactory.getLogger(ChartMenuServiceImpl.class);
    
    @Inject
    private ChartMenuRepository chartMenuRepository;

    /**
     * Save a chartMenu.
     *
     * @param chartMenu the entity to save
     * @return the persisted entity
     */
    public ChartMenu save(ChartMenu chartMenu) {
        log.debug("Request to save ChartMenu : {}", chartMenu);
        ChartMenu result = chartMenuRepository.save(chartMenu);
        return result;
    }

    /**
     *  Get all the chartMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ChartMenu> findAll(Pageable pageable) {
        log.debug("Request to get all ChartMenus");
        Page<ChartMenu> result = chartMenuRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one chartMenu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ChartMenu findOne(Long id) {
        log.debug("Request to get ChartMenu : {}", id);
        ChartMenu chartMenu = chartMenuRepository.findOne(id);
        return chartMenu;
    }

    /**
     *  Delete the  chartMenu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartMenu : {}", id);
        chartMenuRepository.delete(id);
    }
}
