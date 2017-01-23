package com.intelligent.chart.service;

import com.intelligent.chart.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Menu.
 */
public interface MenuService {

    /**
     * Save a menu.
     *
     * @param menu the entity to save
     * @return the persisted entity
     */
    Menu save(Menu menu);

    /**
     *  Get all the menus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Menu> findAll(Pageable pageable);

    /**
     *  Get the "id" menu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Menu findOne(Long id);

    /**
     *  Delete the "id" menu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
