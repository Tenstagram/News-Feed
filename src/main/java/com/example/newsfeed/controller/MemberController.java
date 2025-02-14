package com.example.newsfeed.controller;

import com.example.newsfeed.dto.SignupRequestDto;
import com.example.newsfeed.dto.SignupResponseDto;
import com.example.newsfeed.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;


    //C
    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto =
                memberService.signUp(requestDto.)
    }


}
