package com.intelligent.chart.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.google.common.base.Splitter;
import com.intelligent.chart.config.pool.ProxyServerPool;
import com.intelligent.chart.domain.Job;
import com.intelligent.chart.domain.Person;
import com.intelligent.chart.domain.ProxyServer;
import com.intelligent.chart.domain.Website;
import com.intelligent.chart.repository.JobRepository;
import com.intelligent.chart.repository.PersonRepository;
import com.intelligent.chart.repository.ProxyServerRepository;
import com.intelligent.chart.repository.WebsiteRepository;
import com.intelligent.chart.service.PersonService;
import com.intelligent.chart.service.ProxyServerService;
import com.intelligent.chart.service.WebClientCookieService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Service Implementation for managing Person.
 */
@Service
public class PersonServiceImpl implements PersonService{


    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Inject
    private PersonRepository personRepository;

    @Inject
    private ProxyServerPool proxyServerPool;

    @Inject
    private WebsiteRepository websiteRepository;

    @Inject
    private ProxyServerRepository proxyServerRepository;

    @Inject
    private ProxyServerService proxyServerService;

    @Inject
    private JobRepository jobRepository;

    @Inject
    private WebClientCookieService webClientCookieService;

    @Inject
    private PersonService personService;

    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        Person result = personRepository.save(person);
        return result;
    }

    public Page<Person> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        Page<Person> result = personRepository.findAll(pageable);
        return result;
    }

    public Person findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        Person person = personRepository.findOneWithEagerRelationships(id);
        return person;
    }

    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.delete(id);
    }

    public Person parsePerson(String html) {

        log.info("Start to parsing person");

        Document document = Jsoup.parse(html);

        String infoContent = document.getElementsByClass("article").first().text();

        Element avatarImage = document.getElementsByClass("article").first().getElementsByClass("nbg").first();
        String avatar = null;
        if (avatarImage != null) {

            avatar = avatarImage.attr("href");
        }
        String gender = getTextBetweenKeywords("性别:","星座:", infoContent);
        String birthPlace = getTextBetweenKeywords("出生地:", "职业: ", infoContent);
        String birthdayString = getTextBetweenKeywords("出生日期:", "出生地:", infoContent);
        String socialName = getTextBetweenKeywords("更多中文名:", "家庭成员:", infoContent);
        if (StringUtils.isEmpty(socialName)) {
            socialName = getTextBetweenKeywords("更多中文名:", "imdb编号:", infoContent);
        }
        String description = "";

        Element introElement = document.getElementById("intro");
        if (introElement != null) {
            Element element = introElement.getElementsByClass("bd").first();

            description = element.text().trim();
        }

        Person person = Person.builder()
            .sex(gender)
            .birthplace(birthPlace)
            .detail(description)
            .socialGoodName(socialName)
            .avatar(avatar)
            .build();

        if (StringUtils.isNotBlank(birthdayString)) {

            try {
                person.setBirthday(LocalDate.parse(birthdayString, dateTimeFormatter));
            } catch (java.time.format.DateTimeParseException e) {
                log.info(birthdayString + "is not a valid date string.");
            }
        }

        log.info("Parsed person : " + person.toString());

        return person;
    }

    @Override
    public Person parseAndUpdatePerson(String html, Person person) {

        Person parsedPerson = parsePerson(html);

        String[] ignoreProperties = {"id", "name", "doubanId", "jobs", "grabed"};
        BeanUtils.copyProperties(parsedPerson, person, ignoreProperties);

       // parseAndSavePersonJobs(html, person);

        person.setGrabed(true);

        return save(person);
    }

    private void parseAndSavePersonJobs(String html, Person savedPerson) {

        Document document = Jsoup.parse(html);

        Element infoElement = document.getElementsByClass("article").first();

        infoElement.getElementsByTag("span").forEach(span -> {

            if (StringUtils.isNotBlank(span.text()) && span.text().equals("职业")) {
                Element liElement = span.parent();

                String jobsText = liElement.text().split(":")[1];
                List<String> jobs = Splitter.on("/").omitEmptyStrings().trimResults().splitToList(jobsText);

                jobs.forEach(job -> {

                    Job existedJob = jobRepository.findByName(job);
                    if (existedJob == null) {

                        existedJob = jobRepository.save(Job.builder().name(job).build());
                    }


                    addPersonJob(savedPerson, existedJob);
                });
            }
        });
    }

    @Override
    public void grabPerson(List<Person> personList) {

        if (personList == null || personList.isEmpty()) return;

        Website website = getDoubanWebsite();
        for (Person person : personList) {

            if (person.isGrabed()) {
                log.info("Person " + person.getId() + "is already finished parsing.");
                continue;
            }

            WebClient webClient = proxyServerPool.retrieveWebclient(website);
            grabSinglePerson(webClient, person);

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void grabSinglePerson(WebClient webClient, Person target) {

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProxyServer proxyServer = proxyServerRepository.findByAddress(webClient.getOptions().getProxyConfig().getProxyHost());

        String url = "https://movie.douban.com/celebrity/" + target.getDoubanId();

        log.info("Start to grab person " + url);

        try {

            WebResponse response = webClient.getPage(url).getWebResponse();

            if (response.getStatusCode() != 200) {

                log.info("Grab " + target.getName() + " failed by code " + response.getStatusCode() + " retrying");

                if (response.getStatusCode() == org.springframework.http.HttpStatus.BAD_GATEWAY.value() || response.getStatusCode() == 403) {

                    proxyServer.setIsBlocked(true);
                    proxyServerService.save(proxyServer);

                    log.info(proxyServer.getAddress() + " is blocked. There are " + proxyServerPool.getProxyServers().size() + " proxy servers living.");

                    proxyServerPool.getProxyServers().remove(proxyServer);
                }

                //use another webclient and try again
                webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());
                grabSinglePerson(webClient, target);

                return;
            }

            parseAndUpdatePerson(response.getContentAsString(), target);

         //   parseAndSavePersonJobs(response.getContentAsString(), target);

            try {
                log.info("Grab person " + target.getName() + " successfully. Updating cookies.");
                webClientCookieService.saveCookies(proxyServer, webClient.getCookieManager().getCookies(), getDoubanWebsite());

                proxyServerService.increaseSuccessCount(proxyServer);

            } catch (Exception e) {
                log.info("save cookie failed " + e.getMessage());
            }

        } catch (IOException e) {


            proxyServerService.increaseFailCount(proxyServer);
            log.info("Grab " + target.getName() + " failed by exception " + e.getMessage() + " retrying");

            if (e.getMessage() !=null && e.getMessage().contains("Connection refused")) {
                proxyServer.setIsBlocked(true);
                proxyServerService.save(proxyServer);

                webClientCookieService.removeCookies(proxyServer, getDoubanWebsite());

                log.info(proxyServer.getAddress() + " is blocked. There are " + proxyServerPool.getProxyServers().size() + " proxy servers living.");

                proxyServerPool.getProxyServers().remove(proxyServer);
            }
            //use another webclient and try again
            webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());
            grabSinglePerson(webClient, target);

        } catch (Exception e) {

            if (e.getMessage() !=null && e.getMessage().contains("Connection refused")) {
                proxyServer.setIsBlocked(true);
                proxyServerService.save(proxyServer);

                webClientCookieService.removeCookies(proxyServer, getDoubanWebsite());

                log.info(proxyServer.getAddress() + " is blocked. There are " + proxyServerPool.getProxyServers().size() + " proxy servers living.");

                proxyServerPool.getProxyServers().remove(proxyServer);

            }

            proxyServerService.increaseFailCount(proxyServer);

            log.info("Grab " + target.getName() + " failed by exception " + e.getMessage() + " retrying");

            //use another webclient and try again
            webClient = proxyServerPool.retrieveWebclient(getDoubanWebsite());
            grabSinglePerson(webClient, target);
        }

    }

    @Override
    public void addPersonJob(Person person, Job job) {

        Set<Job> personJobs = person.getJobs();

        if (!personJobs.contains(job)) {
            personJobs.add(job);
        }

        save(person);
    }

    @Override
    public List<Person> findAllTargetPerson() {
        return personRepository.findByGrabedFalse();
    }

    private Website getDoubanWebsite() {

        return websiteRepository.findByName("豆瓣电影");
    }

    private String getTextBetweenKeywords(String firstSplitter, String secondSplliter, String content) {

        if (StringUtils.isEmpty(content)) return null;

        if (!content.contains(firstSplitter) || !content.contains(secondSplliter)) return null;

        int start = content.indexOf(firstSplitter) + firstSplitter.length();
        int end = content.indexOf(secondSplliter);

        if (start > end) return null;

        return content.substring(start, end).trim();
    }

    /*
    public static void main(String[] args) {
        PersonServiceImpl personService = new PersonServiceImpl();

        try {
            String html = Files.toString(new File("/Users/Jerry/Documents/workspace/clean/intelligentChart/src/main/resources/pages/single-person.html"), Charset.defaultCharset());
            personService.parsePerson(html);

            Person target = Person.builder().id(111L).build();
            personService.parseAndUpdatePerson(html, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
