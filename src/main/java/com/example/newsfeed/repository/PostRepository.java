package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
