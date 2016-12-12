package com.tianmaying.crawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianmaying.crawler.model.WebPage;
import com.tianmaying.crawler.model.WebPage.Status;

import java.util.List;

public interface WebPageRepository extends JpaRepository<WebPage, String> {

    WebPage findTopByStatus(Status status);
    
    long countByStatus(Status status);

    List<WebPage> findByType(WebPage.PageType song);
}
