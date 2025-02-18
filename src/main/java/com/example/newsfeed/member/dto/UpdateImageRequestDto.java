package com.example.newsfeed.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateImageRequestDto {

    @NotBlank(message = "프로필 이미지 URL을 입력해야 합니다.")
    private String profileUrl;

}
