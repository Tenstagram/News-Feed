package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    private Long postId;
    private Long parentCommentId; // 비어있으면 최상위 댓글
    private String content;
}
