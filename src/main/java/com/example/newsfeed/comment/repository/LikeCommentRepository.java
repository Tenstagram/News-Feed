package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    // existsByComment_commentIdAndMember_memberId => existsByCommentIdAndMemberId 변경 시의 문제점
    // 만약, LikeComment 엔티티에 자체적으로 commentId memberId이 있으면 상관없음
    // 하지만 private Comment comment; + private Member member; 처럼 ManyToOne 으로 묶여 있으면
    // 각 id는 LikeComment 가 아니라 각 Comment, Member 안에 있는 거기 때문에 불가능

    // 좋아요 중복만 체크 하는 메서드
    boolean existsByComment_commentIdAndMember_id(Long commentId, Long memberId);

    // 좋아요 기록을 조회 후 가져오는 메서드
    Optional<LikeComment> findByComment_commentIdAndMember_id(Long commentId, Long memberId);

}
