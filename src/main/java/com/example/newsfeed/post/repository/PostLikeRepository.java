package com.example.newsfeed.post.repository;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    void deleteByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);

    boolean existsByPost(Post post);

    boolean existsByMemberIdAndPost(Long memberId, Post post);

    int countByMember(Member member);

    int countByPostAndMember(Post post, Member member);

    void deleteByMemberAndPost(Member member, Post post);
}
