package com.example.newsfeed.relationship.controller;

import com.example.newsfeed.relationship.dto.FollowResponseDto;
import com.example.newsfeed.relationship.dto.FriendAcceptResponseDto;
import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RelationshipController {

    private final RelationshipService relationshipService;

    // 친구 추가 요청
    @PostMapping("/follows/{receiverId}/request")
    public ResponseEntity<FriendRequestResponseDto> sendFriendRequest(
            @SessionAttribute(name = "token") Long senderId,
            @PathVariable Long receiverId
    ) {
        FriendRequestResponseDto response = relationshipService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok(response);
    }

    // 친구 추가 요청 수락
    @PostMapping("/follows/{relationshipId}/accept")
    public ResponseEntity<FriendAcceptResponseDto> acceptFriendRequest(
            @SessionAttribute(name = "token") Long receiverId,
            @PathVariable Long relationshipId
    ) {
        FriendAcceptResponseDto response = relationshipService.acceptFriendRequest(receiverId, relationshipId);
        return ResponseEntity.ok(response);
    }

    // 친구 추가 요청 거절
    @DeleteMapping("/follows/{relationshipId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            @SessionAttribute(name = "token") Long receiverId,
            @PathVariable Long relationshipId
    ) {
        relationshipService.rejectFriendRequest(receiverId, relationshipId);
        return ResponseEntity.ok().build();
    }

    // 팔로우 목록 전체 조회
    @GetMapping("/follows/followers")
    public ResponseEntity<List<FollowResponseDto>> getFollowers(
            @SessionAttribute(name = "token") Long memberId
    ) {

        return null;
    }

    // 팔로잉 목록 전체 조회
    @GetMapping("/follows/following")
    public ResponseEntity<List<FollowResponseDto>> getFollowing() {

        return null;
    }

    // 맞팔한 목록 전체 조회
    @GetMapping("/follows/mutual")
    public ResponseEntity<List<FollowResponseDto>> getMutualFollowers() {

        return null;
    }

    // 팔로우 취소
    @DeleteMapping("/follows/{relationshipId}")
    public ResponseEntity<Void> unfollow(
            @PathVariable Long relationshipId
    ) {

        return null;
    }

    // 특정 유저 차단
    @PostMapping("/blocks/{receiverId}")
    public ResponseEntity<Relationship> block(
            @PathVariable Long receiverId
    ) {

        return null;
    }

    // 차단 목록 전체 조회
    @GetMapping("/blocks")
    public ResponseEntity<Relationship> getBlockedMembers() {

        return null;
    }

    // 특정 유저 차단 해제
    @DeleteMapping("/blocks/{receiverId}")
    public ResponseEntity<Void> unblock(
            @PathVariable Long receiverId
    ) {

        return null;
    }

}
