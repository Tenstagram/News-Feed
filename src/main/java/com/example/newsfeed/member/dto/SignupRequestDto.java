package com.example.newsfeed.dto;


import lombok.Getter;

@Getter
public class SignupRequestDto {

    private final String name;
    private final String email;
    private final String password;

    public SignupRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
