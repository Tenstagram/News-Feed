package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    // JPA 에서 자체적으로 메서드 이름을 분석해서 자동으로 쿼리처리 해줌
    // 만약 더 복잡한 조건이 필요하면 직접 쿼리문 입력 가능

    // [JPA 쿼리 네이밍 방법]
    // JPA 에서는 _는 개체 필드타입과 필드명을 구분하는 것
    // => Comment_commentId 이건 Comment.commentId 이런 의미로 해석할 수 있음
    // 그 외에는 카멜케이스 규칙을 따름

    // [문제점]
    // existsByComment_commentIdAndMember_memberId => existsByCommentIdAndMemberId 변경 시의 문제점
    // 만약, LikeComment 엔티티에 자체적으로 commentId memberId이 있으면 상관없음
    // 하지만 private Comment comment; + private Member member; 처럼 ManyToOne 으로 묶여 있으면
    // 각 id는 LikeComment 가 아니라 각 Comment, Member 안에 있는 거기 때문에 불가능

    // 좋아요 중복 체크 메서드
    // 해당 댓글 id와 멤버 id가 DB에 묶여서 존재하는 지, true / false 반환
    boolean existsByComment_commentIdAndMember_memberId(Long commentId, Long memberId);

    // 좋아요 기록을 조회 메서드
    // 댓글 id와 멤버 id가 DB에 묶여서 존재하는 것을 찾음
    // 왜 이건 boolean 반환을 안할까? => 위는 단 순히 O/X 만 판단하면 되지만,
    // 이건 있으면 그걸 가져와서 likeComment 객체에 넣어야 하기 때문에 likeComment 객체로 반환함
    // 그런데 생으로 반환 시키면 null 일때 오류 발생하니 Optional 타입으로 묶어서 포장해서 가져옴
    Optional<LikeComment> findByComment_commentIdAndMember_memberId(Long commentId, Long memberId);

}
