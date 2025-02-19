package com.example.newsfeed.member.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private Long id;
    private String email;
    private String name;
    private String token;

    public LoginResponseDto(Long id, String email, String name, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.token = token;
    }

}
