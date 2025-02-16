package com.example.newsfeed.member.service;

import com.example.newsfeed.member.dto.LoginResponseDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.SignupRequestDto;
import com.example.newsfeed.member.dto.SignupResponseDto;
import com.example.newsfeed.member.util.config.PasswordEncoder;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    //회원가입 이름 이메일 비밀번호
    @Transactional
    public SignupResponseDto signUp(SignupRequestDto dto) {

        //새로운 유저 생성 및 저장
        Member member= new Member(dto.getName(),dto.getEmail(),dto.getPassword());
        Member savedMember = memberRepository.save(member);

        return SignupResponseDto.toDto(savedMember);
    }

    //로그인
    @Transactional
    public LoginResponseDto memberLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"존재하지 않는 이메일 정보입니다."));

        return new LoginResponseDto(member.getId(),member.getName(), member.getEmail());
    }

    //유저 전체 조회
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream().map(MemberResponseDto::toDto).toList();

    }

    //단건 조회
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"조회된 정보가 없습니다."));
        return new MemberResponseDto(member.getId(),member.getName(),member.getEmail());
    }

    //유저 이메일 변경
    @Transactional
    public void updateEmail(Long memberId, String newEmail) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다"));
        if (!member.getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 이메일만 수정할 수 있습니다.");
        }
        member.updateEmail(newEmail);
        memberRepository.save(member);
    }

    //유저 이름 변경
    @Transactional
    public void updateName(Long memberId, String newName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!member.getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 이름만 수정할 수 있습니다.");
        }

        member.updateName(newName);
        memberRepository.save(member);
    }

    //유저 이미지 변경
    @Transactional
    public void updateImage(Long memberId, String newProfileUrl) {
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다"));
        if (newProfileUrl == null || newProfileUrl.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"프로필 이미지 URL을 입력해야 합니다.");
        }
        member.updateImage(newProfileUrl);
    }

    //유저 비밀번호 변경
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"수정할 수 있는 데이터가 없습니다."));
        //기존 비밀번호 검증
        validatePassword(oldPassword, findMember.getPassword());
        //새 비밀번호 암호화 후 저장
        findMember.updatePassword(encoder.encode(newPassword));
        memberRepository.save(findMember);
    }

    @Transactional
    public void delete(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"삭제할 데이터가 없습니다."));
        memberRepository.delete(member);
    }

    /*비밀번호 검증 메서드*/
    public void validatePassword(String newPassword, String password) {
        boolean passwordMatch = encoder.matches(newPassword,password);
        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }

    /*사용자 검증 메서드*/
    public void validateMemberExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다.");
        }
    }

}
