package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.MenuGroupService;
import com.intelligent.chart.domain.MenuGroup;
import com.intelligent.chart.repository.MenuGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MenuGroup.
 */
@Service
@Transactional
public class MenuGroupServiceImpl implements MenuGroupService{

    private final Logger log = LoggerFactory.getLogger(MenuGroupServiceImpl.class);

    @Inject
    private MenuGroupRepository menuGroupRepository;

    /**
     * Save a menuGroup.
     *
     * @param menuGroup the entity to save
     * @return the persisted entity
     */
    public MenuGroup save(MenuGroup menuGroup) {
        log.debug("Request to save MenuGroup : {}", menuGroup);
        MenuGroup result = menuGroupRepository.save(menuGroup);
        return result;
    }

    /**
     *  Get all the menuGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MenuGroup> findAll(Pageable pageable) {
        log.debug("Request to get all MenuGroups");
        Page<MenuGroup> result = menuGroupRepository.findAll(pageable);

//        //TODO
//        List<MenuGroup> items = result.getContent();
//        for (MenuGroup menuGroup: items) {
//            menuGroup.setMenus(menuGroup.getMenus());
//        }

        return result;
    }

    /**
     *  Get one menuGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MenuGroup findOne(Long id) {
        log.debug("Request to get MenuGroup : {}", id);
        MenuGroup menuGroup = menuGroupRepository.findOne(id);
        return menuGroup;
    }

    /**
     *  Delete the  menuGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MenuGroup : {}", id);
        menuGroupRepository.delete(id);
    }
}
