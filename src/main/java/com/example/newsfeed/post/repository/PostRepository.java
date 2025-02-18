package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    Page<Post> findAllByOrderByLikeCountDesc(Pageable pageable);

    Page<Post> findByCreatedAtBetween( LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);
}
