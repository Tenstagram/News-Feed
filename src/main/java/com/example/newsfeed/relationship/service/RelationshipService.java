package com.example.newsfeed.relationship.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.relationship.dto.BlockResponseDto;
import com.example.newsfeed.relationship.dto.FollowResponseDto;
import com.example.newsfeed.relationship.dto.FriendAcceptResponseDto;
import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
import com.example.newsfeed.relationship.exception.custom.*;
import com.example.newsfeed.relationship.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FriendRequestResponseDto sendFriendRequest(Long senderId, Long receiverId) {
        // 자기 자신에게 요청 보낼 경우
        if (senderId.equals(receiverId)) {
            throw new SelfFriendRequestException();
        }

        // 이미 나 혹은 상대방이 요청을 보냈을 경우
        if (relationshipRepository.existsBySenderIdAndReceiverId(senderId, receiverId) ||
                relationshipRepository.existsBySenderIdAndReceiverId(receiverId, senderId)) {
            throw new FriendRequestAlreadyExistsException();
        }

        Member sender = memberRepository.findById(senderId)
                .orElseThrow(IllegalStateException::new);

        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(IllegalStateException::new);

        Relationship relationship = new Relationship(sender, receiver);
        relationship.updateStatus(RelationshipStatus.REQUESTED);
        Relationship savedRelationship = relationshipRepository.save(relationship);
        return FriendRequestResponseDto.of(savedRelationship);
    }

    @Transactional
    public FriendAcceptResponseDto acceptFriendRequest(Long receiverId, Long relationshipId) {
        // 친구 요청이 존재하는지 확인
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(FollowNotFoundException::new);

        //  요청을 받은 사용자인지 검증
        if (!relationship.getReceiver().getId().equals(receiverId)) {
            throw new FriendRequestAuthorizationException();
        }

        // 해당 요청이 REQUESTED 상태인지 확인
        if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
            throw new FriendRequestAlreadyProcessedException();
        }

        if (relationship.getStatus() == RelationshipStatus.BLOCKED) {
            throw new BlockedUserException();
        }

        // 요청 수락, 상태 변경
        relationship.updateStatus(RelationshipStatus.ACCEPTED);

        return FriendAcceptResponseDto.of(relationship);
    }

    @Transactional
    public void rejectFriendRequest(Long receiverId, Long relationshipId) {
        // 친구 요청이 존재하는지 확인
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(IllegalStateException::new);

        //  요청을 받은 사용자인지 검증
        if (!relationship.getReceiver().getId().equals(receiverId)) {
            throw new FriendRequestRejectionAuthorizationException();
        }

        // 거절 시 DB에서 완전히 삭제
        relationshipRepository.deleteById(relationshipId);
    }

    public List<FollowResponseDto> getFollowers(Long memberId) {
        // 내가 팔로우 한 사람 목록 조회
        return relationshipRepository.findBySenderIdAndStatusNot(memberId, RelationshipStatus.BLOCKED)
                .stream()
                .map(relationship -> {
                    Member toMember = memberRepository.findById(relationship.getReceiver().getId())
                            .orElseThrow(IllegalStateException::new);
                    return FollowResponseDto.of(relationship, toMember);
                })
                .toList();
    }

    public List<FollowResponseDto> getFollowing(Long memberId) {
        // 나를 팔로우 한 사람 목록 조회
        return relationshipRepository.findByReceiverIdAndStatusNot(memberId, RelationshipStatus.BLOCKED)
                .stream()
                .map(relationship -> {
                    Member fromMember = memberRepository.findById(relationship.getSender().getId())
                            .orElseThrow(IllegalStateException::new);
                    return FollowResponseDto.of(relationship, fromMember);
                })
                .toList();
    }

    public List<FollowResponseDto> getMutualFollowers(Long memberId) {
        // 나와 맞팔인 사람 목록 조회

        // 내가 팔로우 한 사람 중 나의 요청을 수락한 경우
        List<Relationship> acceptedByThem = relationshipRepository.findBySenderIdAndStatus(memberId, RelationshipStatus.ACCEPTED);
        
        // 나를 팔로우 한 사람 중 내가 수락한 경우
        List<Relationship> acceptedByMe = relationshipRepository.findByReceiverIdAndStatus(memberId, RelationshipStatus.ACCEPTED);

        // 새로운 맞팔 리스트 생성
        List<FollowResponseDto> mutualFollowers = new ArrayList<>();
        acceptedByThem.forEach(r -> mutualFollowers.add(FollowResponseDto.of(r, r.getReceiver())));
        acceptedByMe.forEach(r -> mutualFollowers.add(FollowResponseDto.of(r, r.getSender())));

        return mutualFollowers;
    }

    @Transactional
    public void unfollow(Long memberId, Long targetId) {
        // 내가 보내서 성립된 관계 & 상대방이 보내서 성립된 관계 조회
        Relationship relationship = relationshipRepository.findBySenderIdAndReceiverId(memberId, targetId)
                .orElse(relationshipRepository.findBySenderIdAndReceiverId(targetId, memberId)
                        .orElseThrow(FollowNotFoundException::new));

        // 맞팔 상태인 경우
        if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
            if (relationship.getSender().getId().equals(memberId)) {
                // 내가 보낸 요청이 ACCEPTED 상태인 경우 -> 언팔하면 관계 삭제
                relationshipRepository.delete(relationship);
            } else {
                // 상대가 보낸 요청이 ACCEPTED 상태인 경우 -> 언팔하면 REQUESTED로 변경
                relationship.updateStatus(RelationshipStatus.REQUESTED);
            }
            return;
        }

        // 이미 REQUESTED 상태라면 관계 삭제(나만 팔로우 하고 있는 경우)
        relationshipRepository.delete(relationship);
    }

    @Transactional
    public BlockResponseDto block(Long senderId, Long receiverId) {
        // 차단 하는 유저
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(IllegalStateException::new);

        // 차단 당하는 유저
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(IllegalStateException::new);

        // 차단 관계 생성
        Relationship relationship = new Relationship(sender, receiver);
        relationship.updateStatus(RelationshipStatus.BLOCKED);
        relationshipRepository.save(relationship);

        return BlockResponseDto.of(relationship);
    }

    public List<BlockResponseDto> getBlockedMembers(Long memberId) {
        // 내가 차단한 유저 목록 조회
        return relationshipRepository.findBySenderIdAndStatus(memberId, RelationshipStatus.BLOCKED)
                .stream()
                .map(BlockResponseDto::of)
                .toList();
    }

    @Transactional
    public void unblock(Long senderId, Long receiverId) {
        // 내가 차단한 관계 찾기
        List<Relationship> blockList = relationshipRepository.findBySenderIdAndStatus(senderId, RelationshipStatus.BLOCKED);

        // 차단 관계들 중 특정 유저에게 보낸 차단 관계 찾기
        Relationship relationship = blockList.stream()
                .filter(r -> r.getReceiver().getId().equals(receiverId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        relationshipRepository.delete(relationship);
    }
}
