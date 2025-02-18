package com.example.newsfeed.member.controller;

import com.example.newsfeed.member.dto.*;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.member.util.filter.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    //유저 전체 조회
    //로그인이 안되어있을때에는 조회 안됨.
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAll() {
        List<MemberResponseDto> memberResponseDtoList = memberService.findAll();

        return new ResponseEntity<>(memberResponseDtoList, HttpStatus.OK);
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable() Long id) {
        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    //유저 이메일 수정 세션:Login_USER: 사용자 ID(Long)
//    @PostMapping("/email")
//    public ResponseEntity<Void> updateEmail(
//            //JWT 토큰을 헤더에서 가져옴
//            @RequestHeader("Authorization") String token,
//            @Valid @RequestBody UpdateEmailRequestDto dto) {
//
//        //"Bearer" 제거 후 검증
//        String jwt = token.replace("Bearer","");
//        String email = JwtUtil.validateToken(jwt); //JWT에서 이메일 추출
//
//        //사용자 ID 가져오기(이메일을 통해 조회)
//        Member member = memberService.getMemberByEmail(email);
//        Long memberId = member.getId();
//
//
//        memberService.validateMemberExists(memberId);
//        memberService.updateEmail(memberId, dto.getNewEmail());
//
//        return ResponseEntity.ok().build();
//    }

    //유저 이름 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 이름 수정 /name
    @PostMapping("/name")
    public ResponseEntity<Void> updateName(
            @SessionAttribute(name = "token") Long memberId,
            @Valid @RequestBody UpdateNameRequestDto dto) {

        memberService.validateMemberExists(memberId);
        memberService.updateName(memberId, dto.getNewName());

        return ResponseEntity.ok().build();
    }

    //유저 프로필 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 프로필 이미지 수정 /image
    @PostMapping("/image")
    public ResponseEntity<Void> updatedImage(
            @SessionAttribute(name = "token") Long memberId,
            @Valid @RequestBody UpdateImageRequestDto dto) {

        memberService.validateMemberExists(memberId);
        memberService.updateImage(memberId, dto.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    //유저 이메일 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 비밀번호 수정 /password
    @PatchMapping("/password")
    public ResponseEntity<Void> updatedPassword(
            @SessionAttribute(name = "token") Long memberId,
            @Valid @RequestBody UpdatePasswordRequestDto requestDto) {

        memberService.validateMemberExists(memberId);
        memberService.updatePassword(memberId, requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    //유저 삭제(탈퇴)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(
        @SessionAttribute(name = "token")Long memberId
        ,@RequestBody DeleteRequestDto dto) {
        memberService.delete(memberId,dto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}