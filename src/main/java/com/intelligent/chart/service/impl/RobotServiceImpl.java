package com.intelligent.chart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligent.chart.domain.AreaType;
import com.intelligent.chart.domain.DoubanMovieTag;
import com.intelligent.chart.domain.DoubleMovieSubject;
import com.intelligent.chart.repository.AreaTypeRepository;
import com.intelligent.chart.repository.DoubanMovieTagRepository;
import com.intelligent.chart.repository.DoubleMovieSubjectRepository;
import com.intelligent.chart.service.RobotService;
import com.intelligent.chart.domain.Robot;
import com.intelligent.chart.repository.RobotRepository;
import com.intelligent.chart.service.dto.DoubanMovieSubject;
import com.intelligent.chart.service.dto.DoubanMovieSubjects;
import com.intelligent.chart.service.dto.DoubanTags;
import com.intelligent.chart.service.util.DoubanUtil;
import com.intelligent.chart.service.util.HttpUtils;
import com.intelligent.chart.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

import static com.intelligent.chart.service.util.DoubanUtil.grabSinglePageSubjectsWithTag;

/**
 * Service Implementation for managing Robot.
 */
@Service
public class RobotServiceImpl implements RobotService{

    private final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Inject
    private RobotRepository robotRepository;

    @Inject
    private AreaTypeRepository areaTypeRepository;

    @Inject
    private DoubanMovieTagRepository doubanMovieTagRepository;

    @Inject
    private DoubleMovieSubjectRepository doubleMovieSubjectRepository;

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
                grabDoubanMovieTag();

                break;
            case "test_movie_link_one_page":

                DoubanMovieSubjects doubanMovieSubjects = DoubanUtil.grabSinglePageSubjectsWithTag(0, 20, "热门");

                for (DoubanMovieSubject doubanMovieSubject: doubanMovieSubjects.getSubjects()) {

                    System.out.println(doubanMovieSubject);
                }

                break;

            case "movie_links":

                grabDoubanMovieLink();

                break;

            default:
                break;
        }

        return robot;
    }

    private void grabDoubanMovieLink() {

        List<DoubanMovieTag> doubanMovieTags = doubanMovieTagRepository.findAll();
        for (DoubanMovieTag doubanMovieTag: doubanMovieTags) {

            grabSingleCategoryMovieLink(doubanMovieTag.getName());

        }
    }

    private void grabSingleCategoryMovieLink(String tag) {

        int pageLimit = 100;
        int pageNumber = 0;

        while (true) {

            DoubanMovieSubjects doubanMovieSubjects = DoubanUtil.grabSinglePageSubjectsWithTag(pageNumber * pageLimit, pageLimit, tag);

            if (doubanMovieSubjects.getSubjects() == null) {
                break;
            }
            doubanMovieSubjects.getSubjects().forEach( doubanMovieSubject -> {

                DoubleMovieSubject existedObject = doubleMovieSubjectRepository.findByDoubanId(doubanMovieSubject.getId());
                if (existedObject == null) {

                    DoubleMovieSubject doubleMovieSubject = DoubleMovieSubject.builder()
                        .cover(doubanMovieSubject.getCover())
                        .doubanId(doubanMovieSubject.getId())
                        .rate(doubanMovieSubject.getRate())
                        .url(doubanMovieSubject.getUrl())
                        .title(doubanMovieSubject.getTitle())
                        .build();

                    doubleMovieSubjectRepository.save(doubleMovieSubject);
                }

            });

            pageNumber ++;

            if ( doubanMovieSubjects.getSubjects().size() < pageLimit) {
                break;
            }

            Long randomWaitTime = new Random(3000).nextLong();

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void grabDoubanMovieTag() {

        String url = "https://movie.douban.com/j/search_tags?type=movie";
        try {
            String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            DoubanTags tags = objectMapper.readValue(response, DoubanTags.class);

            for (String tag: tags.getTags()) {

                AreaType existedType = areaTypeRepository.findByName(tag);
                DoubanMovieTag doubanMovieTag = doubanMovieTagRepository.findByName(tag);

                if (existedType == null) {

                    AreaType newAreaType = AreaType.builder().name(tag).build();
                    areaTypeRepository.save(newAreaType);
                }

                if (doubanMovieTag == null) {
                    DoubanMovieTag doubanMovieTagEntity = DoubanMovieTag.builder().name(tag).build();
                    doubanMovieTagRepository.save(doubanMovieTagEntity);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
