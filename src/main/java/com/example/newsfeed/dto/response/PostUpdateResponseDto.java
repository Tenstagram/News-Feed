package com.example.newsfeed.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long postId;

    private final String title;

    private final String mediaUrl;

    private final String description;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public PostUpdateResponseDto(Long postId, String title, String mediaUrl, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
