package com.example.newsfeed.member.controller;

import com.example.newsfeed.member.command.SignupMemberCommand;
import com.example.newsfeed.member.dto.LoginRequestDto;
import com.example.newsfeed.member.dto.LoginResponseDto;
import com.example.newsfeed.member.dto.SignupRequestDto;
import com.example.newsfeed.member.dto.SignupResponseDto;
import com.example.newsfeed.member.entity.MemberStatus;
import com.example.newsfeed.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    //회원가입 이름,이메일,비밀번호
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto dto) {
        SignupMemberCommand signupCommand = new SignupMemberCommand(dto.getName(), dto.getEmail(), dto.getPassword(), MemberStatus.ACTIVATE);
        SignupResponseDto signupResponseDto = memberService.signUp(signupCommand);
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = memberService.memberLogin(dto.getEmail(),dto.getPassword());

        //JWT 토큰을 Authrization 헤더에 추가
        return ResponseEntity.ok()
                .header("Authorization","Bearer" + loginResponseDto.getToken())
                .body(loginResponseDto);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
