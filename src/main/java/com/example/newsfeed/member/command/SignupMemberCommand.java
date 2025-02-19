package com.example.newsfeed.member.command;

import com.example.newsfeed.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupMemberCommand {
    //이름 ,이메일, 패스워드
    private String name;
    private String email;
    private String password;
    private MemberStatus status;
}
