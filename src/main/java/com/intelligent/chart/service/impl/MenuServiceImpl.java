package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MenuService;
import com.intelligent.chart.domain.Menu;
import com.intelligent.chart.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService{

    private final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    
    @Inject
    private MenuRepository menuRepository;

    /**
     * Save a menu.
     *
     * @param menu the entity to save
     * @return the persisted entity
     */
    public Menu save(Menu menu) {
        log.debug("Request to save Menu : {}", menu);
        Menu result = menuRepository.save(menu);
        return result;
    }

    /**
     *  Get all the menus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Menu> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        Page<Menu> result = menuRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one menu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Menu findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        Menu menu = menuRepository.findOne(id);
        return menu;
    }

    /**
     *  Delete the  menu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.delete(id);
    }
}
