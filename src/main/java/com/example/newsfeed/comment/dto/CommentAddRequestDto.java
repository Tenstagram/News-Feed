package com.example.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentAddRequestDto {
    private Long parentCommentId; // 비어있으면 최상위 댓글
    private String content;
}
