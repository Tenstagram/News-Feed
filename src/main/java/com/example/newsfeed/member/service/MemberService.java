package com.example.newsfeed.member.service;

import com.example.newsfeed.member.command.SignupMemberCommand;
import com.example.newsfeed.member.dto.LoginResponseDto;
import com.example.newsfeed.member.dto.MemberResponseDto;
import com.example.newsfeed.member.dto.SignupResponseDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.entity.MemberStatus;
import com.example.newsfeed.member.exception.IncorrectPasswordException;
import com.example.newsfeed.member.exception.MemberDeactivatedException;
import com.example.newsfeed.member.exception.MemberNotFoundException;
import com.example.newsfeed.member.exception.ProfileImageRequiredException;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.member.util.filter.JwtUtil;
import com.example.newsfeed.util.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    //회원가입 이름 이메일 비밀번호
    @Transactional
    public SignupResponseDto signUp(SignupMemberCommand command) {
        String password = encoder.encode(command.getPassword());
        Member member= new Member(command.getName(),command.getEmail(),password,command.getStatus());
        Member savedMember = memberRepository.save(member);
        return SignupResponseDto.toDto(savedMember);
    }

    //로그인
    @Transactional
    public LoginResponseDto memberLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"존재하지 않는 ID 정보입니다."));

        validatePassword(password, member.getPassword());

        //JWT 토큰 생성 추가 TODO 변경점.
        String token = jwtUtil.generateToken(member.getId());
        //JWT 토큰을 응답에 포함
        return new LoginResponseDto(member.getId(),member.getName(), member.getEmail(),token);
    }

    //유저 전체 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .filter(member-> member.getStatus().equals(MemberStatus.ACTIVATE))
                .map(MemberResponseDto::toDto)
                .toList();
    }

    //유저 단건 조회
    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long memberId) {
        Member member = findActiveMemberById(memberId);

        if (member.getStatus().equals(MemberStatus.DELETED)) {
            throw new MemberDeactivatedException();
        }
        return new MemberResponseDto(member.getId(),member.getName(),member.getEmail());
    }

    //유저 이메일 변경
    @Transactional
    public void updateEmail(Long memberId, String newEmail) {
        Member member = findActiveMemberById(memberId);
        member.updateEmail(newEmail);
        memberRepository.save(member);
    }

    //유저 이름 변경
    @Transactional
    public void updateName(Long memberId, String newName) {
        Member member = findActiveMemberById(memberId);
        member.updateName(newName);
        memberRepository.save(member);
    }

    //유저 이미지 변경
    @Transactional
    public void updateImage(Long memberId, String newProfileUrl) {
        if (newProfileUrl == null || newProfileUrl.isBlank()) {
            throw new ProfileImageRequiredException();
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        member.updateImage(newProfileUrl);
    }

    //유저 비밀번호 변경
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"수정할 수 있는 데이터가 없습니다."));
        //기존 비밀번호 검증
        validatePassword(oldPassword, findMember.getPassword());
        //새 비밀번호 암호화 후 저장
        findMember.updatePassword(encoder.encode(newPassword));
        memberRepository.save(findMember);
    }

    //유저 삭제(탈퇴)
    @Transactional
    public void delete(Long memberId,String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"삭제할 데이터가 없습니다."));
        //비밀번호 검증
        validatePassword(password, member.getPassword());
        member.delete();
        memberRepository.save(member);
    }

    /*비밀번호 검증 메서드*/
    public void validatePassword(String newPassword, String password) {
        if (!encoder.matches(newPassword, password)) {
            throw new IncorrectPasswordException();
        }
    }

    //활성화된 회원 조회 메서드
    private Member findActiveMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .filter(member -> member.getStatus() == MemberStatus.ACTIVATE)
                .orElseThrow(MemberNotFoundException::new);
    }


    /*jwt 토큰에서 Id 추출 메서드*/
    @Transactional
    public Long getMemberIdFromToken(String jwt) {
        //JWT 검증 및 이메일 추출
        Long memberId = jwtUtil.extractMemberId(jwt);
        if (memberId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"유효하지 않은 토큰입니다.");
        }
        //이메일로 사용자 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return member.getId();
    }

}