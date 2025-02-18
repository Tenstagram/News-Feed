package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotBlank(message = "현재 비밀번호는 필수값입니다.")
    private String oldPassword;

    @NotBlank(message = "새 비밀번호는 필수값입니다.")
    @Size(min = 8, max =12,message = "비밀번호는 8자 이상 12자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\W_]).+$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 1개 이상 포함해야 합니다.")
    private String newPassword;
}
