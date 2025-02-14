package com.example.newsfeed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String name;
    private Long postId;
    private Long parentCommentId; // 부모 댓글 ID, 없으면 null
    private String content;
    private int likeCount;
    private int childCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
