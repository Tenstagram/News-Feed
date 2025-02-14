package com.example.newsfeed.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private Long postId;

    private String title;

    private String description;


}
