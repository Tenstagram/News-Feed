package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.MediaUrl;
import com.example.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaUrlRepository extends JpaRepository<MediaUrl, Long> {
    List<MediaUrl> findAllByPost(Post post);
}
