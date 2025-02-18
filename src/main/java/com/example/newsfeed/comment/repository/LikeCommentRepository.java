package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    // JPA 에서 자체적으로 메서드 이름을 분석해서 자동으로 쿼리처리 해줌
    boolean existsByComment_CommentIdAndMember_MemberId(Long commentId, Long memberId);

}
