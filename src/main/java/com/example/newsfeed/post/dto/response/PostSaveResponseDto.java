package com.example.newsfeed.post.dto.response;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveResponseDto {

    private final Long postId;

    private final String username;
// api에는 없지만, 유저 이름 추가할까요?

    private final String title;

    private final String mediaUrl;

    private final String description;

    private final State state;

    private final int likeCount;

    private final int commentCount;

    private final LocalDateTime createdAt;

//    private LocalDateTime updatedAt; 생성할 때는 수정시간 생략 어떨까요?

    public PostSaveResponseDto(Long postId, String username, String title, String mediaUrl, String description, State state, int likeCount, int commentCount, LocalDateTime createdAt) {
        this.postId = postId;
        this.username = username;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.state = state;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

}
