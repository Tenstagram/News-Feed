package com.example.newsfeed.relationship.dto;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.relationship.entity.Relationship;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowResponseDto {

    private final Long id;
    private final String image;
    private final String name;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FollowResponseDto(Long id, String image, String name, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FollowResponseDto of(Relationship relationship, Member member) {
        return new FollowResponseDto(
                relationship.getId(),
                member.getProfileUrl(),
                member.getName(),
                relationship.getStatus().name(),
                relationship.getCreatedAt(),
                relationship.getModifiedAt()
        );
    }
}
