package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long member_id;
    private final String name;
    private final String email;

    public MemberResponseDto(Long member_id, String name, String email) {
        this.member_id = member_id;
        this.name = name;
        this.email = email;
    }

    public static MemberResponseDto toDto(Member member) {
        return new MemberResponseDto(member.getId(), member.getName(), member.getName());
    }
}
