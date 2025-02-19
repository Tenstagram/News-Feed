package com.example.newsfeed.relationship.controller;

import com.example.newsfeed.relationship.dto.BlockResponseDto;
import com.example.newsfeed.relationship.dto.FollowResponseDto;
import com.example.newsfeed.relationship.dto.FriendAcceptResponseDto;
import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
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
        List<FollowResponseDto> followers = relationshipService.getFollowers(memberId);
        return ResponseEntity.ok(followers);
    }

    // 팔로잉 목록 전체 조회
    @GetMapping("/follows/following")
    public ResponseEntity<List<FollowResponseDto>> getFollowing(
            @SessionAttribute(name = "token") Long memberId
    ) {
        List<FollowResponseDto> following = relationshipService.getFollowing(memberId);
        return ResponseEntity.ok(following);
    }

    // 맞팔한 목록 전체 조회
    @GetMapping("/follows/mutual")
    public ResponseEntity<List<FollowResponseDto>> getMutualFollowers(
            @SessionAttribute(name = "token") Long memberId
    ) {
        List<FollowResponseDto> mutual = relationshipService.getMutualFollowers(memberId);
        return ResponseEntity.ok(mutual);
    }

    // 팔로우 취소
    @DeleteMapping("/follows/{targetId}")
    public ResponseEntity<Void> unfollow(
            @SessionAttribute(name = "token") Long memberId,
            @PathVariable Long targetId
    ) {
        relationshipService.unfollow(memberId, targetId);
        return ResponseEntity.ok().build();
    }

    // 특정 유저 차단
    @PostMapping("/blocks/{receiverId}")
    public ResponseEntity<BlockResponseDto> block(
            @SessionAttribute(name = "token") Long memberId,
            @PathVariable Long receiverId
    ) {
        BlockResponseDto response = relationshipService.block(memberId, receiverId);
        return ResponseEntity.ok(response);
    }

    // 차단 목록 전체 조회
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockResponseDto>> getBlockedMembers(
            @SessionAttribute(name = "token") Long memberId
    ) {
        List<BlockResponseDto> response = relationshipService.getBlockedMembers(memberId);

        // 차단된 사용자가 없을때
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    // 특정 유저 차단 해제
    @DeleteMapping("/blocks/{receiverId}")
    public ResponseEntity<Void> unblock(
            @SessionAttribute(name = "token") Long memberId,
            @PathVariable Long receiverId
    ) {
        relationshipService.unblock(memberId, receiverId);
        return ResponseEntity.ok().build();
    }

}
