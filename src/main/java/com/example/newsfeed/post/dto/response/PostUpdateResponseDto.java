package com.example.newsfeed.post.dto.response;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long postId;

    private final String title;

    private final String mediaUrl;

    private final String description;

    private final State state;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public PostUpdateResponseDto(Long postId, String title, String mediaUrl, String description, State state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
