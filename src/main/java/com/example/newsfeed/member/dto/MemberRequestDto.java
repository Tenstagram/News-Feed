package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDto {

    private final Long id;
    @NotBlank(message = "이메일 입력은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    public MemberRequestDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
