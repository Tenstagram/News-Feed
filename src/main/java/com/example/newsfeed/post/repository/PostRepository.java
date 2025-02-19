package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    Page<Post> findAllByOrderByLikeCountDesc(Pageable pageable);

    Page<Post> findByCreatedAtBetween( LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);

    @Query("SELECT p FROM Post p WHERE (:blockedMemberIds IS NULL OR p.member.id NOT IN :blockedMemberIds)")
    List<Post> findAllExcludingBlockedUsers(@Param("blockedMemberIds") List<Long> blockedMemberIds);
}
