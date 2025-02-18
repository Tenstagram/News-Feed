package com.example.newsfeed.post.dto.request;

import com.example.newsfeed.post.entity.State;
import lombok.Getter;

@Getter
public class PostSaveRequestDto {

    private String title;

    private String description;

    private State state;

}
