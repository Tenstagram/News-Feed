package com.example.newsfeed.member.controller;

import com.example.newsfeed.member.dto.*;
import com.example.newsfeed.member.service.MemberService;
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
    @PostMapping("/{email}")
    public ResponseEntity<Void> updateEmail(
            @SessionAttribute(name = "token") Long memberId,
            @Valid @RequestBody UpdateEmailRequestDto dto) {

        memberService.validateMemberExists(memberId);
        memberService.updateEmail(memberId, dto.getNewEmail());

        return ResponseEntity.ok().build();
    }

    //유저 이름 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 이름 수정 /{name}
    @PatchMapping("/{name}")
    public ResponseEntity<Void> updateName(
            @SessionAttribute(name = "token") Long memberId,
            @Valid @RequestBody UpdateNameRequestDto dto) {

        memberService.validateMemberExists(memberId);
        memberService.updateName(memberId, dto.getNewName());

        return ResponseEntity.ok().build();
    }

    //유저 프로필 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 프로필 이미지 수정 /{image}
    @PatchMapping("/image")
    public ResponseEntity<Void> updatedImage(
            @SessionAttribute(name = "token")Long memberId,
            @Valid @RequestBody UpdateImageRequestDto dto) {
        memberService.updateImage(memberId, dto.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    //유저 이메일 수정 세션:Login_USER: 사용자 ID(Long)
    //유저 비밀번호 수정 /{password}
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatedPassword(@PathVariable Long id, @RequestBody UpdatePasswordRequestDto requestDto) {
        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //유저 삭제(탈퇴) /{member_id}
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable("email") String email) {
        memberService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
