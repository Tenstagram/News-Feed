package com.example.newsfeed.relationship.dto;

import com.example.newsfeed.relationship.entity.Relationship;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendRequestResponseDto {

    private final Long id;
    private final Long receiverId;
    private final Long senderId;
    private final LocalDateTime createdAt;

    public FriendRequestResponseDto(Long id, Long receiverId, Long senderId, LocalDateTime createdAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createdAt = createdAt;
    }

    public static FriendRequestResponseDto of(Relationship relationship) {
        return new FriendRequestResponseDto(
                relationship.getId(),
                relationship.getReceiverId(),
                relationship.getSenderId(),
                relationship.getCreatedAt()
        );
    }
}
