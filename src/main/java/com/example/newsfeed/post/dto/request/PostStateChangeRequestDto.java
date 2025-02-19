package com.example.newsfeed.post.dto.request;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

@Getter
public class PostStateChangeRequestDto {

    private Long postId;

    private State state;

}
