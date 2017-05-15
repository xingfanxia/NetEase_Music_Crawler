package com.tianmaying.crawler.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tianmaying.crawler.Crawler;
import com.tianmaying.crawler.model.Song;
import com.tianmaying.crawler.model.WebPage;
import com.tianmaying.crawler.model.WebPage.PageType;
import com.tianmaying.crawler.model.WebPage.Status;
import com.tianmaying.crawler.repository.SongRepository;
import com.tianmaying.crawler.repository.WebPageRepository;

@Component
public class MultiCrawlerWithJpa implements Crawler {
    
    public static final Integer MAX_THREADS = 20;
    
    @Autowired
    private WebPageRepository webPageRepository;
    
    @Autowired
    private SongRepository songRepository;
    
    @Override
    public void initCrawlerList() {
//        for(int i = 0; i < 43; i++) {
//            webPageRepository.saveAndFlush(new WebPage("http://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset="  + (i * 35), PageType.playlists));
//        }
        webPageRepository.saveAndFlush(new WebPage("http://music.163.com/playlist?id=454016843", PageType.playlist));
    }

    @Override
    public synchronized WebPage getUnCrawlPage() {
        WebPage webPage = webPageRepository.findTopByStatus(Status.uncrawl);
        if(webPage == null) {
            return null;
        }
        webPage.setStatus(Status.crawled);
        return webPageRepository.saveAndFlush(webPage);
    }
    
    @Override
    public List<WebPage> addToCrawlList(List<WebPage> webPages) {
        webPages = webPageRepository.save(webPages);
        webPageRepository.flush();
        return webPages;
    }
    
    @Override
    public Song saveSong(Song song) {
        return songRepository.saveAndFlush(song);
    }

    @Override
    public void doRun(){
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        for(int i = 0; i < MAX_THREADS; i++) {
            executorService.execute(new MultiCrawlerThread(this));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<Song> getSongs() {
        return songRepository.findAll();
    }
    
}
