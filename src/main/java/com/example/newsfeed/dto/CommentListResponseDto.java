package com.example.newsfeed.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponseDto {
    private List<CommentResponseDto> bestCommentList;
    private List<CommentResponseDto> totalCommentList;

}
