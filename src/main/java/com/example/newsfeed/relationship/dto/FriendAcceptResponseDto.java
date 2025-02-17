package com.example.newsfeed.relationship.dto;

import com.example.newsfeed.relationship.entity.Relationship;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendAcceptResponseDto {

    private final Long id;
    private final Long receiverId;
    private final Long senderId;
    private final LocalDateTime updatedAt;

    public FriendAcceptResponseDto(Long id, Long receiverId, Long senderId, LocalDateTime updatedAt) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.updatedAt = updatedAt;
    }

    public static FriendAcceptResponseDto of(Relationship relationship) {
        return new FriendAcceptResponseDto(
                relationship.getId(),
                relationship.getReceiverId(),
                relationship.getSenderId(),
                relationship.getUpdatedAt()
        );
    }
}
