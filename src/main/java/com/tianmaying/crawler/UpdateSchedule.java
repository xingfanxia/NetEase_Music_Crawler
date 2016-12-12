package com.tianmaying.crawler;

import com.tianmaying.crawler.model.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianmaying.crawler.impl.MultiCrawlerWithJpa;
import com.tianmaying.crawler.repository.WebPageRepository;

import java.util.List;

@Component
public class UpdateSchedule {

    @Autowired
    private MultiCrawlerWithJpa multiCrawler;

    @Autowired
    private WebPageRepository webPageRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void update() {
        List<WebPage> webPages = webPageRepository.findByType(WebPage.PageType.song);
        webPages.forEach(p -> p.setStatus(WebPage.Status.uncrawl));
        webPageRepository.save(webPages);
        multiCrawler.doRun();
    }

}
