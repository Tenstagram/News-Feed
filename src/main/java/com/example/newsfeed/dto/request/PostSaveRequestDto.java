package com.example.newsfeed.dto.request;

import lombok.Getter;

@Getter
public class PostSaveRequestDto {

    private String title;

    private String mediaUrl;

    private String description;

}
