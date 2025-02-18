package com.example.newsfeed.comment.entity;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    // optional = false: 반드시 해당 엔티티와 연관관계를 가지고 있어야됨
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // null 가능 => null 이면 최상위 댓글 , null 이 아니면 대댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentCommentId;

    @Column(nullable = false)
    private String content;

    private int likeCount;

    private int childCount; // 자식 댓글 수

    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedBy
    private LocalDateTime updatedAt;

    // null 이면 삭제 안된 상태 => soft delete 처리
    private LocalDateTime deletedAt;

}
