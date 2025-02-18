package com.example.newsfeed.post.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPageRequestDto {

    LocalDateTime startDate;

    LocalDateTime endDate;
}
