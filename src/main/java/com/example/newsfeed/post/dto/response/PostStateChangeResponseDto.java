package com.example.newsfeed.post.dto.response;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

@Getter
public class PostStateChangeResponseDto {

    private final State state;

    public PostStateChangeResponseDto(State state) {
        this.state = state;
    }

}
