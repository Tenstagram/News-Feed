package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNameRequestDto {

    @NotBlank(message = "이름 입력값은 필수입니다.")
    @Size(min =1, max = 10, message = "이름은 10자 이내여야 합니다.")
    private String newName;

}
