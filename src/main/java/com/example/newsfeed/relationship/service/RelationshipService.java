package com.example.newsfeed.relationship.service;

import com.example.newsfeed.relationship.dto.FriendAcceptResponseDto;
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

    @Transactional
    public FriendAcceptResponseDto acceptFriendRequest(Long receiverId, Long relationshipId) {
        // 친구 요청이 존재하는지 확인
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(IllegalStateException::new);

        //  요청을 받은 사용자인지 검증
        if (!relationship.getReceiverId().equals(receiverId)) {
            throw new IllegalArgumentException("해당 친구 요청을 수락할 권한이 없습니다.");
        }

        // 해당 요청이 REQUESTED 상태인지 확인
        if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
            throw new IllegalStateException("이미 처리된 친구 요청입니다.");
        }

        if (relationship.getStatus() == RelationshipStatus.BLOCKED) {
            throw new IllegalStateException("차단된 사용자입니다.");
        }

        // 요청 수락, 상태 변경
        relationship.updateStatus(RelationshipStatus.ACCEPTED);

        return FriendAcceptResponseDto.of(relationship);
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
