package com.example.newsfeed.relationship.service;

import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
import com.example.newsfeed.relationship.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    @Transactional
    public FriendRequestResponseDto sendFriendRequest(Long senderId, Long receiverId) {
        if (relationshipRepository.existsBySenderIdAndReceiverId(senderId, receiverId)) {
            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
        }

        Relationship relationship = new Relationship(senderId, receiverId);
        relationship.updateStatus(RelationshipStatus.REQUESTED);
        Relationship savedRelationship = relationshipRepository.save(relationship);
        return FriendRequestResponseDto.of(savedRelationship);
    }

    public Relationship acceptFriendRequest(Long relationshipId) {
        return null;
    }

    public Relationship getFollowers() {
        return null;
    }

    public Relationship getFollowing() {
        return null;
    }

    public Relationship getMutualFollowers() {
        return null;
    }

    public Relationship getBlockedMembers() {
        return null;
    }

    public Relationship block(Long receiverId) {
        return null;
    }
}
