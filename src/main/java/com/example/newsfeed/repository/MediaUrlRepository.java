package com.example.newsfeed.repository;

import com.example.newsfeed.entity.MediaUrl;
import com.example.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaUrlRepository extends JpaRepository<MediaUrl, Long> {
    MediaUrl findByPost(Post post);
}
