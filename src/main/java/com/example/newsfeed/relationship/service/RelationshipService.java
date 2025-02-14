package com.example.newsfeed.relationship.service;

import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationshipService {

    private RelationshipRepository relationshipRepository;

    public FriendRequestResponseDto sendFriendRequest(Long receiverId) {
        return null;
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
