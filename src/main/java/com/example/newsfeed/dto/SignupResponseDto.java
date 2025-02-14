package com.example.newsfeed.dto;

public class SignupResponseDto {

    private final Long id;
    private final String email;
    private final String password;

    public SignupResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
