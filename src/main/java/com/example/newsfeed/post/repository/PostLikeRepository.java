package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    void deleteByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
