package com.example.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNameRequestDto {

    @NotBlank
    @Size(min =1, max = 10, message = "이름은 10자 이내여야 합니다.")
    private String newName;

}
