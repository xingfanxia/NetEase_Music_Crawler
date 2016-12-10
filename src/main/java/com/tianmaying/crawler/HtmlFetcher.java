package com.tianmaying.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class HtmlFetcher {

    public String fetch(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).timeout(3000).execute();
            return response.statusCode() / 100 == 2 ? response.body() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static <T> void main(String[] args) throws Exception {
        HtmlFetcher htmlFetcher = new HtmlFetcher();
        System.out.println(htmlFetcher.fetch("http://music.163.com/playlist?id=454016843"));
    }

}
