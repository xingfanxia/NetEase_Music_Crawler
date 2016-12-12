package com.tianmaying.crawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianmaying.crawler.model.Song;

public interface SongRepository extends JpaRepository<Song, String> {
    
}
