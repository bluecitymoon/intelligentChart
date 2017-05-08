package com.intelligent.chart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.domain.*;
import com.intelligent.chart.domain.enumeration.RobotLogLevel;
import com.intelligent.chart.repository.*;
import com.intelligent.chart.service.*;
import com.intelligent.chart.service.dto.DoubanMovieSubject;
import com.intelligent.chart.service.dto.DoubanMovieSubjects;
import com.intelligent.chart.service.dto.DoubanTags;
import com.intelligent.chart.service.util.DoubanUtil;
import com.intelligent.chart.service.util.HttpUtils;
import com.intelligent.chart.vo.MoviePageIndex;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Robot.
 */
@Service
@Scope("prototype")
public class RobotServiceImpl implements RobotService{

    private final Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Inject
    private RobotRepository robotRepository;

    @Inject
    private AreaTypeRepository areaTypeRepository;

    @Inject
    private DoubanMovieTagRepository doubanMovieTagRepository;

    @Inject
    private DoubanMovieTagService doubanMovieTagService;

    @Inject
    private DoubleMovieSubjectRepository doubleMovieSubjectRepository;

    @Inject
    private RobotMovieSubjectFailPageRepository robotMovieSubjectFailPageRepository;

    @Inject
    private RobotMovieSubjectSuccessPageRepository robotMovieSubjectSuccessPageRepository;

    private Robot robot;

    @Inject
    private RobotLogRepository robotLogRepository;

    @Inject
    private ProxyServerService proxyServerService;

    private ProxyServer lastGoodProxyServer;

    @Inject
    private MovieService movieService;

    @Inject
    private ProxyServerPool proxyServerPool;

    @Inject
    private MovieSuccessLogRepository movieSuccessLogRepository;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private PersonService personService;

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

        robot = findOne(id);

        robot.setLastStart(ZonedDateTime.now());

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

            case "all_tags_from_page":

                grabAllTags();

                break;

            case "test_links_in_single_detailed_category":

                break;
            case "test_single_one_page_links":

                grabMovieLinksInSingleCategory(doubanMovieTagRepository.findByName("爱情"));

                break;

            case "all_links":

                List<MoviePageIndex> targetPagesPool = Lists.newArrayList();
                List<DoubanMovieTag> tags = doubanMovieTagRepository.findAll();

                for (DoubanMovieTag tag : tags) {

                    for (int i = 1; i <= tag.getMaxPageCount(); i++) {
                        List<RobotMovieSubjectSuccessPage> subjectSuccessPage = robotMovieSubjectSuccessPageRepository.findByPageNumberAndTag(i, tag.getName());
                        if (subjectSuccessPage == null || subjectSuccessPage.size() == 0) {

                            MoviePageIndex index = MoviePageIndex.builder().pageNumber(i).tag(tag.getName()).build();
                            targetPagesPool.add(index);
                        }
                    }
                }

                Collections.shuffle(targetPagesPool);

                log.info("There are " + targetPagesPool.size() + " pages to go ...");

                for (int i = 1; i <= 3; i ++) {
                    log.info("Start in " + i);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                List<List<MoviePageIndex>> partitions = Lists.partition(targetPagesPool, 100);

                ExecutorService executorService = Executors.newFixedThreadPool(100);


                for (final List<MoviePageIndex> targets: partitions) {

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {

                            grabPages(targets);
                        }
                    });
                }

                break;

            case "all_proxy_server":

                proxyServerService.grabProxyServers();
                break;

            case "proxy_server_reachable_checker":

                proxyServerService.checkReachable();
                break;

            case "get_category_max_count":
                doubanMovieTagService.getAllMaxCount();
                break;

            case "get_movies":

                List<DoubleMovieSubject> targetLinks = doubleMovieSubjectRepository.findAll().stream().filter(new Predicate<DoubleMovieSubject>() {
                    @Override
                    public boolean test(DoubleMovieSubject doubleMovieSubject) {

                        try {

                            return movieSuccessLogRepository.findByDoubanId(doubleMovieSubject.getDoubanId()) == null;
                        } catch (Exception e) {
                            return false;
                        }

                    }
                }).collect(Collectors.toList());

                log.info("There are " + targetLinks.size() + " movies to go ...");

                for (int i = 1; i <= 3; i ++) {
                    log.info("Start in " + i);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int threadCount = 150;
                ExecutorService executorPool = Executors.newFixedThreadPool(threadCount);
                Lists.partition(targetLinks, threadCount).forEach(links -> {

                    executorPool.execute(new Runnable() {
                        @Override
                        public void run() {

                            movieService.grabMovies(links);
                        }
                    });
                });


                break;

            case "get_person_list":

                List<Person> people = personService.findAllTargetPerson();

                log.info("There are " + people.size() + " people to go ...");

                for (int i = 1; i <= 3; i ++) {
                    log.info("Start in " + i);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int p_threadCount = 120;
                ExecutorService peopleExecutorPool = Executors.newFixedThreadPool(p_threadCount);
                Lists.partition(people, p_threadCount).forEach(personList -> {

                    peopleExecutorPool.execute(new Runnable() {
                        @Override
                        public void run() {

                            personService.grabPerson(personList);
                        }
                    });
                });

                break;

            case "test_get_singleMovie":

                //WebClient webClient = HttpUtils.newWebClientWithRandomProxyServer(proxyServerPool.pull());

                try {
                    String testHtml = Files.toString(new File("/Users/Jerry/Documents/workspace/clean/intelligentChart/src/main/resources/pages/movie-single-page.html"), Charsets.UTF_8);
                    DoubleMovieSubject testSubject = doubleMovieSubjectRepository.findOne(101L);

                    Document document = Jsoup.parse(testHtml);
                    Movie testMovie = movieService.parseMovie(document, testSubject);

                    log.info(testMovie.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "test_get_single_movie_online" :

                WebClient webClient = HttpUtils.newNormalWebClient();

                Set<Cookie> cookies = webClient.getCookieManager().getCookies();

                log.info("before: ");
                cookies.forEach(e -> {
                    log.info(e.toString());

                    try {
                        Files.write(e.getName()+ ":" + e.getValue(), new File("before.txt"), Charset.defaultCharset());

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                try {
                    WebResponse response = webClient.getPage("https://movie.douban.com/subject/26260853/?from=showing").getWebResponse();
                    List<NameValuePair> headers = response.getResponseHeaders();
                    cookies = webClient.getCookieManager().getCookies();

                    log.info("after: ");
                    cookies.forEach(e -> {
                        log.info(e.toString());

                        try {
                            Files.append(e.getName()+ ":" + e.getValue(), new File("after.txt"), Charset.defaultCharset());

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });

                    log.info("response headers are: ");

                    for (NameValuePair header : headers) {

                        log.info(header.toString());

                        try {
                            Files.append(header.getName()+ ":" + header.getValue(), new File("response.txt"), Charset.defaultCharset());

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "test_get_movie_single_thread" :

                break;

            default:
                break;
        }

        robot.setLastStop(ZonedDateTime.now());

        robotRepository.save(robot);

        return robot;
    }

    private void grabAllTags() {

        DoubanUtil.grabAllTags().forEach(this::saveOrUpdateTags);

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
            doubanMovieSubjects.getSubjects().forEach(this::saveNewMovieLink);

            pageNumber ++;

            if ( doubanMovieSubjects.getSubjects().size() < pageLimit) {
                break;
            }

            try {

                Thread.sleep(generateRandomReasonableWaitMiliSeconds());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveNewMovieLink(DoubanMovieSubject doubanMovieSubject) {

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
    }
    private void grabDoubanMovieTag() {

        String url = "https://movie.douban.com/j/search_tags?type=movie";
        try {
            String response = HttpUtils.newWebClient().getPage(url).getWebResponse().getContentAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            DoubanTags tags = objectMapper.readValue(response, DoubanTags.class);

            for (String tag: tags.getTags()) {

                saveOrUpdateTags(tag);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrUpdateTags(String tag) {

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

    private  void grabPages(List<MoviePageIndex> targets) {

        for (MoviePageIndex target : targets) {
            grabSinglePage(target.getPageNumber(), target.getTag());
        }
    }

    private void grabMovieLinksInSingleCategory(DoubanMovieTag tag) {

        int pageNumber = 0;

        while (true) {

            List<RobotMovieSubjectSuccessPage> existedSuccessPage = robotMovieSubjectSuccessPageRepository.findByPageNumberAndTag(pageNumber, tag.getName());

            if (existedSuccessPage != null && existedSuccessPage.size() > 0) {
                pageNumber ++;
                continue;
            }
            List<DoubanMovieSubject> onePageLinks = grabSinglePage(pageNumber, tag.getName());

            /*
            if (onePageLinks == null || onePageLinks.isEmpty()) {
                break;
            }
            if (onePageLinks.size() < 20) {
                break;
            }
*/
            if (pageNumber > 392) {
                break;
            }
            pageNumber ++;

//            try {
//                Thread.sleep(generateRandomReasonableWaitMiliSeconds());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

    }

    private long generateRandomReasonableWaitMiliSeconds() {

        return 1 + new Random().nextInt(1000);
    }

    private List<DoubanMovieSubject> grabSinglePage(int pageNumber, String category) {

        waitSomewhile();

        log.info("Start to get page " + pageNumber + " with category " + category);

       // ProxyServer proxyServer = lastGoodProxyServer == null? proxyServerService.findOneReachableProxyServer(): lastGoodProxyServer;
        //ProxyServer proxyServer = proxyServerService.findOneReachableProxyServer();
        ProxyServer proxyServer = proxyServerPool.pull();

        List<DoubanMovieSubject> subjects = Lists.newArrayList();

        RobotLog log = RobotLog.builder().createDate(ZonedDateTime.now()).robot(robot).level(RobotLogLevel.success).build();
        try {

            String url = "https://movie.douban.com/tag/" + URLEncoder.encode(category, "UTF-8") + "?start=" + 20 * pageNumber + "&type=T";
            WebResponse response = HttpUtils.newWebClientWithRandomProxyServer(proxyServer).getPage(url).getWebResponse();

            if (response.getStatusCode() != 200) {

                this.log.info("Get page " + pageNumber + " with category " + category + " failed with code " + response.getStatusCode() + " -> retrying");

                return grabSinglePage(pageNumber, category);
            }

            String content = response.getContentAsString();

            Document document = Jsoup.parse(content);
            Elements movieLinks = document.getElementsByClass("nbg");

            movieLinks.forEach(element -> {

                String href = element.attr("href");
                List<String> linkElements = Splitter.on("/").omitEmptyStrings().splitToList(href);

                DoubanMovieSubject doubanMovieSubject = DoubanMovieSubject.builder()
                    .url(href)
                    .title(element.attr("title"))
                    .id(linkElements.get(linkElements.size() - 1))
                    .build();

                Element imgElement = element.getElementsByTag("img").first();
                doubanMovieSubject.setCover(imgElement.attr("src"));

                subjects.add(doubanMovieSubject);

                saveNewMovieLink(doubanMovieSubject);

            });

            log.setLogContent(url + " saved successfully with tag = " + category + " and page number is " + pageNumber);

            RobotMovieSubjectSuccessPage robotMovieSubjectSuccessPage = RobotMovieSubjectSuccessPage.builder()
                .pageNumber(pageNumber)
                .tag(category)
                .createDate(ZonedDateTime.now())
                .build();

            robotMovieSubjectSuccessPageRepository.save(robotMovieSubjectSuccessPage);

            proxyServerService.increaseSuccessCount(proxyServer);

            this.log.info("Getting page " + pageNumber + " with category " + category + " successfully");

        } catch (UnsupportedEncodingException e) {
            handleGrabMovieSubjectError(category, pageNumber, ExceptionUtils.getStackTrace(e));

            String reason = ExceptionUtils.getStackTrace(e);
            if (reason.length() > 20000) {
                reason = reason.substring(0, 19999);
            }
            log.setLevel(RobotLogLevel.error);
            log.setLogContent(reason);

//            try {
//                Thread.sleep(generateRandomReasonableWaitMiliSeconds());
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }

            proxyServerService.increaseFailCount(proxyServer);

            robotLogRepository.save(log);

            this.log.info("Getting page " + pageNumber + " with category " + category + "failed: " + e.getMessage());
            //to death!
           return grabSinglePage(pageNumber, category);

        } catch (MalformedURLException e) {

            handleGrabMovieSubjectError(category, pageNumber, ExceptionUtils.getStackTrace(e));
            String reason = ExceptionUtils.getStackTrace(e);
            if (reason.length() > 20000) {
                reason = reason.substring(0, 19999);
            }
            log.setLevel(RobotLogLevel.error);
            log.setLogContent(reason);

//            //to death!
//            try {
//                Thread.sleep(generateRandomReasonableWaitMiliSeconds());
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
            proxyServerService.increaseFailCount(proxyServer);
            robotLogRepository.save(log);
            //to death!

            this.log.info("Getting page " + pageNumber + " with category " + category + "failed: " + e.getMessage());
            return grabSinglePage(pageNumber, category);
        } catch (IOException e) {
            handleGrabMovieSubjectError(category, pageNumber, ExceptionUtils.getStackTrace(e));

            String reason = ExceptionUtils.getStackTrace(e);
            if (reason.length() > 20000) {
                reason = reason.substring(0, 19999);
            }
            log.setLevel(RobotLogLevel.error);
            log.setLogContent(reason);

//            try {
//                Thread.sleep(generateRandomReasonableWaitMiliSeconds());
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
            proxyServerService.increaseFailCount(proxyServer);
            robotLogRepository.save(log);

            this.log.info("Getting page " + pageNumber + " with category " + category + "failed: " + e.getMessage());
            //to death!
            return grabSinglePage(pageNumber, category);

        } catch (Exception unexpectedException) {
            handleGrabMovieSubjectError(category, pageNumber, ExceptionUtils.getStackTrace(unexpectedException));

            String reason = ExceptionUtils.getStackTrace(unexpectedException);
            if (reason.length() > 20000) {
                reason = reason.substring(0, 19999);
            }
            log.setLevel(RobotLogLevel.error);
            log.setLogContent(reason);

//            try {
//                Thread.sleep(generateRandomReasonableWaitMiliSeconds());
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
            proxyServerService.increaseFailCount(proxyServer);
            robotLogRepository.save(log);

            this.log.info("Getting page " + pageNumber + " with category " + category + "failed: " + unexpectedException.getMessage());
            //to death!
            return grabSinglePage(pageNumber, category);

        }
        //log last grab step print.O

        robotLogRepository.save(log);

        waitSomewhile();
        return subjects;
    }

    private void waitSomewhile() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleGrabMovieSubjectError(String tag, int pageNumber, String reason) {

        if (reason.length() > 20000) {
            reason = reason.substring(0, 19999);
        }
        RobotMovieSubjectFailPage robotMovieSubjectFailPage = RobotMovieSubjectFailPage.builder().pageNumber(pageNumber).tag(tag).reason(reason).createDate(ZonedDateTime.now()).build();
        robotMovieSubjectFailPageRepository.save(robotMovieSubjectFailPage);
    }

}
