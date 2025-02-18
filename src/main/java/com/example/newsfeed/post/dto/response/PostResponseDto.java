package com.example.newsfeed.post.dto.response;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long postId;

    private final String title;

    private final String userName;

    private final String mediaUrl;

    private final String description;

    private final State state;

    private final int likeCount;

    private final int commentCount;

    public PostResponseDto(Long postId, String title, String userName, String mediaUrl, String description, State state, int likeCount, int commentCount) {
        this.postId = postId;
        this.title = title;
        this.userName = userName;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.state = state;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
