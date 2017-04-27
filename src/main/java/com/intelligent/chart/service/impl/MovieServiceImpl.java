package com.intelligent.chart.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.google.common.base.Splitter;
import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.domain.*;
import com.intelligent.chart.repository.*;
import com.intelligent.chart.service.*;
import com.intelligent.chart.vo.TimestapWebclient;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class MovieServiceImpl implements MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Inject
    private MovieRepository movieRepository;

    @Inject
    private MovieParticipantRepository movieParticipantRepository;

    @Inject
    private MovieSuccessLogService movieSuccessLogService;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private JobService jobService;

    @Inject
    private ProxyServerPool proxyServerPool;

    @Inject
    private WebsiteRepository websiteRepository;

    @Inject
    private RobotLogService robotLogService;

    @Inject
    private WebClientCookieService webClientCookieService;

    @Inject
    private ProxyServerRepository proxyServerRepository;

    @Inject
        private ProxyServerService proxyServerService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    public synchronized Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        Movie result = movieRepository.save(movie);
        return result;
    }

    public Page<Movie> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        Page<Movie> result = movieRepository.findAll(pageable);
        return result;
    }

    public Movie findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        Movie movie = movieRepository.findOne(id);
        return movie;
    }

    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.delete(id);
    }

    private Website getDoubanWebsite() {

        return websiteRepository.findByName("豆瓣电影");
    }
    @Override
    public WebClient grabSingleMovieWithUrl(DoubleMovieSubject doubanMovieSubject, Map.Entry<ProxyServer, TimestapWebclient> client, WebClient lastSuccessWebclient) {

        List<String> urlElements = Splitter.on("/").omitEmptyStrings().splitToList(doubanMovieSubject.getUrl());
        String doubanSubjectId = urlElements.get(urlElements.size() - 1);

        try {

            Movie existedMovie = movieRepository.findByDoubanId(Long.valueOf(doubanSubjectId));
            if (existedMovie != null) {

                log.info(doubanSubjectId + " already have it.");

                return null;
            }

        } catch (Exception e) {

            log.info(doubanSubjectId + " already have it and found exception " + e.getMessage());
            return null;
        }

        log.info("Start to grab " + doubanMovieSubject.getTitle());
        Document document = null;
        Movie savedMovie = null;

        WebClient webClient = null;
        try {

            WebResponse response = webClient.getPage(doubanMovieSubject.getUrl()).getWebResponse();

            if (response.getStatusCode() != 200) {

                log.info("Grab " + doubanMovieSubject.getTitle() + " failed by code " + response.getStatusCode() + " retrying");
                return null;
            }

            log.info("Get response successfully  and start to parse and save relationships");
            String html = response.getContentAsString();

            document = Jsoup.parse(html);

            Movie movie = parseMovie(document, doubanMovieSubject);

            savedMovie = movieRepository.save(movie);

            parseAndSaveDirector(savedMovie, document);

            parseAndSaveScreenWriter(savedMovie, document);

            parseAndSaveActors(savedMovie, document);

           // proxyServerPool.pushNiceWebclient(client.getKey(), client.getValue().getWebClient());

            log.info("Save movie " + savedMovie.getName() + "successfully");

            MovieSuccessLog successLog = MovieSuccessLog.builder().createDate(ZonedDateTime.now()).doubanId(doubanMovieSubject.getDoubanId()).
                movieId(savedMovie.getId()).name(doubanMovieSubject.getTitle()).build();

            movieSuccessLogService.save(successLog);

            return webClient;

        } catch (IOException e) {
            log.info("Grab " + doubanMovieSubject.getTitle() + " failed by exception " + e.getMessage() + " retrying");

        } catch (Exception e) {

            log.info("Grab " + doubanMovieSubject.getTitle() + " failed by exception " + e.getMessage() + " retrying");

        }

        return null;
    }

    @Override
    public void grabSingleMovieWithUrl(DoubleMovieSubject doubanMovieSubject, WebClient webClient) {

        log.info("Start to grab " + doubanMovieSubject.getTitle());

        Document document = null;
        Movie savedMovie = null;

        ProxyServer proxyServer = proxyServerRepository.findByAddress(webClient.getOptions().getProxyConfig().getProxyHost());
        try {

            WebResponse response = webClient.getPage(doubanMovieSubject.getUrl()).getWebResponse();

            if (response.getStatusCode() != 200) {

                log.info("Grab " + doubanMovieSubject.getTitle() + " failed by code " + response.getStatusCode() + " retrying");

                if (response.getStatusCode() == org.springframework.http.HttpStatus.BAD_GATEWAY.value()) {

                    proxyServer.setIsBlocked(true);
                    proxyServerService.save(proxyServer);
                }

                //use another webclient and try again
                webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());
                grabSingleMovieWithUrl(doubanMovieSubject, webClient);

                return;
            }

            log.info("Get response successfully  and start to parse and save relationships.");

            String html = response.getContentAsString();

            document = Jsoup.parse(html);

            Movie movie = parseMovie(document, doubanMovieSubject);

            savedMovie = movieRepository.save(movie);

            try {
                parseAndSaveDirector(savedMovie, document);

            } catch (Exception e) {

                RobotLog robotLog = RobotLog.builder().logContent("Save director failed " + e.getMessage() + " movie id is " + savedMovie.getId()).build();

                robotLogService.save(robotLog);
            }

            try {
                parseAndSaveScreenWriter(savedMovie, document);

            } catch (Exception e) {

                RobotLog robotLog = RobotLog.builder().logContent("Save screenwriter failed " + e.getMessage() + " movie id is " + savedMovie.getId()).build();

                robotLogService.save(robotLog);
            }


            try {
                parseAndSaveActors(savedMovie, document);

            } catch (Exception e) {

                RobotLog robotLog = RobotLog.builder().logContent("Save actors failed " + e.getMessage() + " movie id is " + savedMovie.getId()).build();

                robotLogService.save(robotLog);
            }


            log.info("Save movie " + savedMovie.getName() + "successfully");

            MovieSuccessLog successLog = MovieSuccessLog.builder().createDate(ZonedDateTime.now()).doubanId(doubanMovieSubject.getDoubanId()).
                movieId(savedMovie.getId()).name(doubanMovieSubject.getTitle()).build();

            movieSuccessLogService.save(successLog);

            try {
                webClientCookieService.saveCookies(proxyServer, webClient.getCookieManager().getCookies(), getDoubanWebsite());

                proxyServerService.increaseSuccessCount(proxyServer);

            } catch (Exception e) {
                log.info("save cookie failed " + e.getMessage());
            }

        } catch (IOException e) {
            log.info("Grab " + doubanMovieSubject.getTitle() + " failed by exception " + e.getMessage() + " retrying");


            try {

                proxyServerService.increaseFailCount(proxyServer);

            } catch (Exception a) {
                log.info("save failed count failed " + a.getMessage());
            }

            if (e.getMessage() != null && e.getMessage().contains("Connection refused")) {

                proxyServer.setIsBlocked(true);
                proxyServerService.save(proxyServer);
            }

            //use another webclient and try again
            webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());

            grabSingleMovieWithUrl(doubanMovieSubject, webClient);



        } catch (Exception e) {

            log.info("Grab " + doubanMovieSubject.getTitle() + " failed by exception " + e.getMessage() + " retrying");

            try {

                proxyServerService.increaseFailCount(proxyServer);

            } catch (Exception a) {
                log.info("save failed count failed " + a.getMessage());
            }

            if (e.getMessage() != null && e.getMessage().contains("Connection refused")) {

                proxyServer.setIsBlocked(true);
                proxyServerService.save(proxyServer);
            }

            //use another webclient and try again
            webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());

            grabSingleMovieWithUrl(doubanMovieSubject, webClient);
        }

    }


    private void tryLuckyMovieLink(Document document) {

        Element recommedationArea = document.getElementById("recommendations");

        if (recommedationArea != null) {


        }

    }
    private void parseAndSaveAwards(Movie savedMovie, Document document) {

        Elements awards = document.getElementsByClass("award");

        if (awards == null || awards.isEmpty()) {return;}

        for (Element award : awards) {

        }
    }

    private void parseAndSaveScreenWriter(Movie savedMovie, Document document) {

        Elements elements = document.getElementsByClass("attrs");
        for (Element element : elements) {

            Element previousElement = element.previousElementSibling();
            if (previousElement != null && previousElement.text() == "编剧") {

                Elements links = element.getElementsByTag("a");

                if (links != null) {
                    for (Element link : links) {

                        savePersonWithRole(link, "编剧", savedMovie);
                    }
                }
            }
        }
    }

    private void savePersonWithRole(Element element, String role, Movie savedMovie) {

        String href = element.attr("href");
        String doubanPersonId = getLastNumberFromUrl(href);

        Person existedPerson = personRepository.findByDoubanId(Long.valueOf(doubanPersonId));

        if (existedPerson == null) {

            Person director = Person.builder().name(element.text()).doubanId(Long.valueOf(doubanPersonId)).grabed(false).build();

            existedPerson = personRepository.save(director);

            log.info("Saved director with douban id " + director.getDoubanId() + " and its id " + existedPerson.getId());

        }

        Job directorJob = jobService.findOrCreateJob(role);
        MovieParticipant movieParticipant = MovieParticipant.builder().person(existedPerson).job(directorJob).movie(savedMovie).build();

        movieParticipantRepository.save(movieParticipant);

        log.info("Saved " + role +" as movie participants " + movieParticipant.getId());
    }

    private void parseAndSaveActors(Movie savedMovie, Document document) {

        Elements directorElements = document.getElementsByAttributeValue("rel", "v:starring");
        for (Element directorElement : directorElements) {

            savePersonWithRole(directorElement, "演员", savedMovie);
        }
    }

    private void parseAndSaveDirector(Movie savedMovie, Document document) {

        Elements directorElements = document.getElementsByAttributeValue("rel", "v:directedBy");

        for (Element directorElement : directorElements) {

            savePersonWithRole(directorElement, "导演", savedMovie);
        }

    }

    public Movie parseMovie(Document document, DoubleMovieSubject doubanMovieSubject) {

        Movie movie = Movie.builder()
            .coverUrl(doubanMovieSubject.getCover())
            .doubanUrl(doubanMovieSubject.getUrl())
            .doubanId(Long.valueOf(doubanMovieSubject.getDoubanId()))
            .createDate(ZonedDateTime.now())
            .name(doubanMovieSubject.getTitle())
            .build();

        String showUpdate = getFirstByPropertyValue(document, "v:initialReleaseDate");
        try {

            if (showUpdate.contains("(")) {
                showUpdate = showUpdate.substring(0, showUpdate.indexOf("("));
            }
            movie.setRunDate(LocalDate.parse(showUpdate, formatter));

        } catch (Exception e) {
            movie.setRunDate(null);
        }

        String runtime = getFirstByPropertyValue(document, "v:runtime");
        movie.setTerm(runtime);

        Element infoElement = document.getElementById("info");

        String infoText = infoElement.text();
        try {

            int startIndex = infoText.indexOf("制片国家/地区:") + "制片国家/地区:".length();
            int endIndex = infoText.indexOf("语言:");
            String locationText = infoText.substring(startIndex, endIndex);

            movie.setArea(locationText.trim());

        }catch (Exception e) {

            movie.setArea("");
        }

        try {

            int lngStartIndx = infoText.indexOf("语言:") + "语言:".length();
            int lngEndIndex = infoText.indexOf("上映日期: ");
            String languagText = infoText.substring(lngStartIndx, lngEndIndex);

            movie.setLanguage(languagText.trim());

        } catch (Exception e) {

            movie.setLanguage("");
        }


        String description = getFirstByPropertyValue(document, "v:summary");
        movie.setDescription(description);

        String rate = getFirstByPropertyValue(document, "v:average");

        if (NumberUtils.isParsable(rate) && NumberUtils.isNumber(rate)) {

            movie.setRate(Float.valueOf(rate));
        }

        String ratePeopleCount = getFirstByPropertyValue(document, "v:votes");
        if (NumberUtils.isParsable(ratePeopleCount) && NumberUtils.isNumber(ratePeopleCount)) {

            movie.setRatePeopleCount(Long.valueOf(ratePeopleCount));
        }

        return movie;

    }

    @Override
    public synchronized List<Movie> findTopOneHundredMovies() {
        return null;
    }

    @Override
    public void grabMovies(List<DoubleMovieSubject> links) {

        if (links == null || links.isEmpty()) return;

        Website douban = getDoubanWebsite();

        for (DoubleMovieSubject link : links) {

            grabSingleMovieWithUrl(link, proxyServerPool.retrieveWebclient(douban));

            try {

                TimeUnit.SECONDS.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private String getFirstByPropertyValue(Document document, String value) {
        Element element = document.getElementsByAttributeValue("property", value).first();

        if (element == null) {
            return "";
        }
        return element.text();
    }

    private String getLastNumberFromUrl(String url) {

        List<String> urlElements = Splitter.on("/").omitEmptyStrings().splitToList(url);

        return urlElements.get(urlElements.size() - 1);
    }
}
