package com.example.newsfeed.post.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostPageResponseDto {

    private final Long postId;

    private final String title;

    private final String description;

    private final String mediaUrl;

    private final int likeCount;

    private final int commentCount;

    public PostPageResponseDto( Long postId, String title, String description, String mediaUrl, int likeCount, int commentCount) {
        this.postId = postId;//후에 댓글 파라미터 추가
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
