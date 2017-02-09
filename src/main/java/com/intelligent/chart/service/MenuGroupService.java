package com.intelligent.chart.service;

import com.intelligent.chart.domain.MenuGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MenuGroup.
 */
public interface MenuGroupService {

    /**
     * Save a menuGroup.
     *
     * @param menuGroup the entity to save
     * @return the persisted entity
     */
    MenuGroup save(MenuGroup menuGroup);

    /**
     *  Get all the menuGroups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MenuGroup> findAll(Pageable pageable);

    /**
     *  Get the "id" menuGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MenuGroup findOne(Long id);

    /**
     *  Delete the "id" menuGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
