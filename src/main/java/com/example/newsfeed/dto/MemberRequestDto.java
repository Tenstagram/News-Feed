package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private final Long id;
    private final String email;

    public MemberRequestDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
