package com.example.newsfeed.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long postId;

    private final String title;

    private final String mediaUrl;

    private final String description;

    private final int likeCount;

    private final int commentCount;

    public PostResponseDto(Long postId, String title, String mediaUrl, String description, int likeCount, int commentCount) {
        this.postId = postId;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
