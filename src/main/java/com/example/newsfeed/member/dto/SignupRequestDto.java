package com.example.newsfeed.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "이름 입력은 필수 입력값입니다.")
    private final String name;

    @NotBlank(message = "이메일 입력은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호 입력은 필수 입력값입니다.")

    @Size(min = 8, max =12,message = "비밀번호는 8자 이상 12자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\W_]).+$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 1개 이상 포함해야 합니다.")
    private final String password;

    public SignupRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
