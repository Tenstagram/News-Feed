package com.example.newsfeed.post.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostPageRequestDto {

    LocalDate startDate;

    LocalDate endDate;
}
