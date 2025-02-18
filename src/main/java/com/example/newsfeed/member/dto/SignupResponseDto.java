package com.example.newsfeed.member.dto;


import com.example.newsfeed.member.entity.Member;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private final Long id;
    private final String email;
    private final String password;

    public SignupResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static SignupResponseDto toDto(Member member) {
        return new SignupResponseDto(member.getId(), member.getEmail(), member.getPassword());
    }
}
