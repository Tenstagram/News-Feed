package com.example.newsfeed.comment.entity;

import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeComment {
    // 중복 좋아요 방지 + 좋아요 기록을 저장하기 위해
    // 좋아요한 해당 댓글과 멤버 정보를 받아서 DB에 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createdAt; // 최초 좋아요 일자

}
