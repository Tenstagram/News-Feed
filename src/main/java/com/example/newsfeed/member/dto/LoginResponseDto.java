package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private Long id;
    private String email;
    private String name;

    public LoginResponseDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}
