package com.example.newsfeed.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentListResponseDto {
    private List<CommentResponseDto> bestCommentList; // 좋아요 수에 따른 베스트 댓글 목록
    private List<CommentResponseDto> allCommentList; // 일반 댓글 목록

}
