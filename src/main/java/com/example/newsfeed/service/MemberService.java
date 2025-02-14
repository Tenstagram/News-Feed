package com.example.newsfeed.service;

import com.example.newsfeed.dto.SignupResponseDto;
import com.example.newsfeed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입 이름 이메일 비밀번호
    public SignupResponseDto signUp(String name, String email, String password) {

    }



}
