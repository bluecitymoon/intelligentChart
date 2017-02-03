package com.intelligent.chart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.intelligent.chart.domain.ChartMenu;
import com.intelligent.chart.domain.Menu;
import com.intelligent.chart.domain.MenuGroup;
import com.intelligent.chart.repository.ChartMenuRepository;
import com.intelligent.chart.repository.MenuGroupRepository;
import com.intelligent.chart.repository.MenuRepository;
import com.intelligent.chart.service.MenuGroupService;
import com.intelligent.chart.vo.MenuGroupVo;
import com.intelligent.chart.vo.MenuVo;
import com.intelligent.chart.web.rest.util.HeaderUtil;
import com.intelligent.chart.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing MenuGroup.
 */
@RestController
@RequestMapping("/api")
public class MenuGroupResource {

    private final Logger log = LoggerFactory.getLogger(MenuGroupResource.class);

    @Inject
    private MenuGroupService menuGroupService;

    @Inject
    private MenuGroupRepository menuGroupRepository;

    @Inject
    private MenuRepository menuRepository;

    @Inject
    private ChartMenuRepository chartMenuRepository;

    /**
     * POST  /menu-groups : Create a new menuGroup.
     *
     * @param menuGroup the menuGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menuGroup, or with status 400 (Bad Request) if the menuGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menu-groups")
    @Timed
    public ResponseEntity<MenuGroup> createMenuGroup(@RequestBody MenuGroup menuGroup) throws URISyntaxException {
        log.debug("REST request to save MenuGroup : {}", menuGroup);
        if (menuGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("menuGroup", "idexists", "A new menuGroup cannot already have an ID")).body(null);
        }
        MenuGroup result = menuGroupService.save(menuGroup);
        return ResponseEntity.created(new URI("/api/menu-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("menuGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menu-groups : Updates an existing menuGroup.
     *
     * @param menuGroup the menuGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menuGroup,
     * or with status 400 (Bad Request) if the menuGroup is not valid,
     * or with status 500 (Internal Server Error) if the menuGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menu-groups")
    @Timed
    public ResponseEntity<MenuGroup> updateMenuGroup(@RequestBody MenuGroup menuGroup) throws URISyntaxException {
        log.debug("REST request to update MenuGroup : {}", menuGroup);
        if (menuGroup.getId() == null) {
            return createMenuGroup(menuGroup);
        }
        MenuGroup result = menuGroupService.save(menuGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("menuGroup", menuGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menu-groups : get all the menuGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of menuGroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/menu-groups")
    @Timed
    public ResponseEntity<List<MenuGroup>> getAllMenuGroups(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MenuGroups");
        Page<MenuGroup> page = menuGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menu-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/menu-groups/with/menus")
    @Timed
    public ResponseEntity<Set<MenuGroupVo>> getMenusWithGroup()  {

        List<MenuGroup> groups = menuGroupRepository.findAll();
        Set<MenuGroupVo> vos = new HashSet<MenuGroupVo>();

        for (MenuGroup menuGroup: groups) {

            MenuGroupVo menuGroupVo = new MenuGroupVo();
            menuGroupVo.setId(menuGroup.getId());
            menuGroupVo.setTitle(menuGroup.getTitle());
            menuGroupVo.setIcon(menuGroup.getIcon());

            List<Menu> menus = menuRepository.findByMenuGroup_Id(menuGroup.getId());

            Set<MenuVo> menuVos = new HashSet<>();
            for (Menu menu: menus) {

                MenuVo menuVo = new MenuVo();
                menuVo.setTitle(menu.getTitle());
                menuVo.setLogo(menu.getLogo());
                menuVos.add(menuVo);

                List<ChartMenu> chartMenus = chartMenuRepository.findByMenu_Id(menu.getId());
                //TODO support multiple charts in one menu. No requirement right now.
                if (!chartMenus.isEmpty()) {
                    menuVo.setChartId(chartMenus.get(0).getId());
                }
            }

            menuGroupVo.setMenus(menuVos);

            vos.add(menuGroupVo);

        }

        return new ResponseEntity<>(vos, HttpStatus.OK);
    }


    /**
     * GET  /menu-groups/:id : get the "id" menuGroup.
     *
     * @param id the id of the menuGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menuGroup, or with status 404 (Not Found)
     */
    @GetMapping("/menu-groups/{id}")
    @Timed
    public ResponseEntity<MenuGroup> getMenuGroup(@PathVariable Long id) {
        log.debug("REST request to get MenuGroup : {}", id);
        MenuGroup menuGroup = menuGroupService.findOne(id);
        return Optional.ofNullable(menuGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /menu-groups/:id : delete the "id" menuGroup.
     *
     * @param id the id of the menuGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menu-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenuGroup(@PathVariable Long id) {
        log.debug("REST request to delete MenuGroup : {}", id);
        menuGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("menuGroup", id.toString())).build();
    }

}
