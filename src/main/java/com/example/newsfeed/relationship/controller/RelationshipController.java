package com.example.newsfeed.relationship.controller;

import com.example.newsfeed.member.service.MemberService;
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
    private final MemberService memberService;

    // 친구 추가 요청
    @PostMapping("/follows/{receiverId}/request")
    public ResponseEntity<FriendRequestResponseDto> sendFriendRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long receiverId
    ) {
        Long senderId = getMemberIdByToken(token);
        FriendRequestResponseDto response = relationshipService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok(response);
    }

    // 친구 추가 요청 수락
    @PostMapping("/follows/{relationshipId}/accept")
    public ResponseEntity<FriendAcceptResponseDto> acceptFriendRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long relationshipId
    ) {
        Long receiverId = getMemberIdByToken(token);
        FriendAcceptResponseDto response = relationshipService.acceptFriendRequest(receiverId, relationshipId);
        return ResponseEntity.ok(response);
    }

    // 친구 추가 요청 거절
    @DeleteMapping("/follows/{relationshipId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long relationshipId
    ) {
        Long receiverId = getMemberIdByToken(token);
        relationshipService.rejectFriendRequest(receiverId, relationshipId);
        return ResponseEntity.ok().build();
    }

    // 팔로우 목록 전체 조회
    @GetMapping("/follows/followers")
    public ResponseEntity<List<FollowResponseDto>> getFollowers(
            @RequestHeader("Authorization") String token
    ) {
        Long memberId = getMemberIdByToken(token);
        List<FollowResponseDto> followers = relationshipService.getFollowers(memberId);
        return ResponseEntity.ok(followers);
    }

    // 팔로잉 목록 전체 조회
    @GetMapping("/follows/following")
    public ResponseEntity<List<FollowResponseDto>> getFollowing(
            @RequestHeader("Authorization") String token
    ) {
        Long memberId = getMemberIdByToken(token);
        List<FollowResponseDto> following = relationshipService.getFollowing(memberId);
        return ResponseEntity.ok(following);
    }

    // 맞팔한 목록 전체 조회
    @GetMapping("/follows/mutual")
    public ResponseEntity<List<FollowResponseDto>> getMutualFollowers(
            @RequestHeader("Authorization") String token
    ) {
        Long memberId = getMemberIdByToken(token);
        List<FollowResponseDto> mutual = relationshipService.getMutualFollowers(memberId);
        return ResponseEntity.ok(mutual);
    }

    // 팔로우 취소
    @DeleteMapping("/follows/{targetId}")
    public ResponseEntity<Void> unfollow(
            @RequestHeader("Authorization") String token,
            @PathVariable Long targetId
    ) {
        Long memberId = getMemberIdByToken(token);
        relationshipService.unfollow(memberId, targetId);
        return ResponseEntity.ok().build();
    }

    // 특정 유저 차단
    @PostMapping("/blocks/{receiverId}")
    public ResponseEntity<BlockResponseDto> block(
            @RequestHeader("Authorization") String token,
            @PathVariable Long receiverId
    ) {
        Long memberId = getMemberIdByToken(token);
        BlockResponseDto response = relationshipService.block(memberId, receiverId);
        return ResponseEntity.ok(response);
    }

    // 차단 목록 전체 조회
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockResponseDto>> getBlockedMembers(
            @RequestHeader("Authorization") String token
    ) {
        Long memberId = getMemberIdByToken(token);
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
            @RequestHeader("Authorization") String token,
            @PathVariable Long receiverId
    ) {
        Long memberId = getMemberIdByToken(token);
        relationshipService.unblock(memberId, receiverId);
        return ResponseEntity.ok().build();
    }

    private Long getMemberIdByToken(String token) {
        String jwt = token.replace("Bearer","");
        return memberService.getMemberIdFromToken(jwt);
    }

}
