package com.example.newsfeed.member.controller;

import com.example.newsfeed.member.command.SignupMemberCommand;
import com.example.newsfeed.member.dto.LoginRequestDto;
import com.example.newsfeed.member.dto.LoginResponseDto;
import com.example.newsfeed.member.dto.SignupRequestDto;
import com.example.newsfeed.member.dto.SignupResponseDto;
import com.example.newsfeed.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    //회원가입 이름,이메일,비밀번호
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto dto) {
        SignupMemberCommand signupCommand = new SignupMemberCommand(dto.getName(), dto.getEmail(), dto.getPassword());
        SignupResponseDto signupResponseDto = memberService.signUp(signupCommand);
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(HttpServletRequest request,
                                                  @RequestBody LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = memberService.memberLogin(dto.getEmail(),dto.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("token",loginResponseDto.getId());

        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
