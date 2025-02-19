package com.example.newsfeed.relationship.dto;

import com.example.newsfeed.relationship.entity.Relationship;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlockResponseDto {

    private final Long id;
    private final Long senderId;
    private final Long receiverId;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BlockResponseDto(Long id, Long senderId, Long receiverId, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BlockResponseDto of(Relationship relationship) {
        return new BlockResponseDto(
                relationship.getId(),
                relationship.getSender().getId(),
                relationship.getReceiver().getId(),
                relationship.getStatus().name(),
                relationship.getCreatedAt(),
                relationship.getModifiedAt()
        );
    }
}
