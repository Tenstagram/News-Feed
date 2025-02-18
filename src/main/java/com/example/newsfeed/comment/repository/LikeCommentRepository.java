package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    // 좋아요 중복 체크 메서드
    // JPA 에서 자체적으로 메서드 이름을 분석해서 자동으로 쿼리처리 해줌
    // 저는 편의상 멤버 id를 memberId 로 했는 데, 현재 member 객체에 필드명이 id 라서 existsByComment_CommentIdAndMember_id 로 변경
    boolean existsByComment_CommentIdAndMember_id(Long commentId, Long memberId);

    // 좋아요 기록을 조회 메서드
    Optional<LikeComment> findByComment_CommentIdAndMember_id(Long commentId, Long memberId);

}
