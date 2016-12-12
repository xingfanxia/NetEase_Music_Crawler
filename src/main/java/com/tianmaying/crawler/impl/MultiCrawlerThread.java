package com.tianmaying.crawler.impl;

import com.tianmaying.crawler.Crawler;
import com.tianmaying.crawler.HtmlParser;
import com.tianmaying.crawler.model.Song;
import com.tianmaying.crawler.model.WebPage;

public class MultiCrawlerThread implements Runnable {
    
    private final Crawler multiCrawler;
    private final HtmlParser htmlParser = new HtmlParser();

    public MultiCrawlerThread(Crawler multiCrawler) {
        super();
        this.multiCrawler = multiCrawler;
    }
    
    @Override
    public void run() {
        WebPage webPage;
        int getUnCrawlPageTimes = 0;
        while (true) {
            webPage = multiCrawler.getUnCrawlPage();
            if(webPage == null) {
                if(getUnCrawlPageTimes > 10) {
                    break;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    getUnCrawlPageTimes++;
                    continue;
                }
            }
            getUnCrawlPageTimes = 0;

            if(WebPage.PageType.playlists.equals(webPage.getType())) {
                multiCrawler.addToCrawlList(htmlParser.parsePlaylists(webPage.getUrl()));
            } else if(WebPage.PageType.playlist.equals(webPage.getType())) {
                multiCrawler.addToCrawlList(htmlParser.parsePlaylist(webPage.getUrl()));
            } else {
                Song song = new Song(webPage.getUrl(), webPage.getTitle(), htmlParser.parseSong(webPage.getUrl()));
                multiCrawler.saveSong(song);
            }
        }
    }

}
