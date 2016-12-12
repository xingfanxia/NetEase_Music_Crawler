package com.tianmaying.crawler.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianmaying.crawler.Crawler;
import com.tianmaying.crawler.impl.MultiCrawlerWithList;
import com.tianmaying.crawler.model.Song;

@RestController
@RequestMapping("/multi")
public class MultiCrawlerController {
    
    private final Crawler crawler = new MultiCrawlerWithList();
    
    @GetMapping("/start")
    public String start() throws InterruptedException {
        crawler.run();
        return "爬取完毕";
    }
    
    @GetMapping("/songs")
    public List<Song> songs() {
        return crawler.getSongs();
    }
    
}
