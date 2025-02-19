package com.example.newsfeed.post.dto.response;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.entity.State;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long postId;

    private final String title;

    private final String userName;

    private final String mediaUrl;

    private final String description;

    private final State state;

    private final Long likeCount;

    private final Long commentCount;

    public PostResponseDto(Long postId, String title, String userName, String mediaUrl, String description, State state, Long likeCount, Long commentCount) {
        this.postId = postId;
        this.title = title;
        this.userName = userName;
        this.mediaUrl = mediaUrl;
        this.description = description;
        this.state = state;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public static PostResponseDto of(Post post) {
        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getMember().getName(),
                post.getMediaUrl(),
                post.getDescription(),
                post.getState(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }
}
