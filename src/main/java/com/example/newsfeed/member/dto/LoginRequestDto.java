package com.example.newsfeed.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private final String password;


    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
