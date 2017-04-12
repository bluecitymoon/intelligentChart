package com.intelligent.chart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligent.chart.domain.AreaType;
import com.intelligent.chart.repository.AreaTypeRepository;
import com.intelligent.chart.service.RobotService;
import com.intelligent.chart.domain.Robot;
import com.intelligent.chart.repository.RobotRepository;
import com.intelligent.chart.service.dto.DoubanTags;
import com.intelligent.chart.service.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Service Implementation for managing Robot.
 */
@Service
@Transactional
public class RobotServiceImpl implements RobotService{

    private final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Inject
    private RobotRepository robotRepository;

    @Inject
    private AreaTypeRepository areaTypeRepository;

    /**
     * Save a robot.
     *
     * @param robot the entity to save
     * @return the persisted entity
     */
    public Robot save(Robot robot) {
        log.debug("Request to save Robot : {}", robot);
        Robot result = robotRepository.save(robot);
        return result;
    }

    /**
     *  Get all the robots.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Robot> findAll(Pageable pageable) {
        log.debug("Request to get all Robots");
        Page<Robot> result = robotRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one robot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Robot findOne(Long id) {
        log.debug("Request to get Robot : {}", id);
        Robot robot = robotRepository.findOne(id);
        return robot;
    }

    /**
     *  Delete the  robot by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Robot : {}", id);
        robotRepository.delete(id);
    }

    @Override
    public Robot start(Long id) {

        Robot robot = findOne(id);
        switch (robot.getIdentifier()) {
            case "movie_tag":

                String url = "https://movie.douban.com/j/search_tags?type=movie";
                try {
                    String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    DoubanTags tags = objectMapper.readValue(response, DoubanTags.class);

                    for (String tag: tags.getTags()) {

                        AreaType existedType = areaTypeRepository.findByName(tag);

                        if (existedType == null) {

                            AreaType newAreaType = AreaType.builder().name(tag).build();
                            areaTypeRepository.save(newAreaType);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return robot;
    }
}
