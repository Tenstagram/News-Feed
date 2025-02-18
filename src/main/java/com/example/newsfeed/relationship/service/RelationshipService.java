package com.example.newsfeed.relationship.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.relationship.dto.FollowResponseDto;
import com.example.newsfeed.relationship.dto.FriendAcceptResponseDto;
import com.example.newsfeed.relationship.dto.FriendRequestResponseDto;
import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
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
        if (relationshipRepository.existsBySenderIdAndReceiverId(senderId, receiverId) ||
                relationshipRepository.existsBySenderIdAndReceiverId(receiverId, senderId)) {
            throw new IllegalStateException("이미 친구 요청을 보냈거나 상대방이 요청을 보냈습니다.");
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
                .orElseThrow(IllegalStateException::new);

        //  요청을 받은 사용자인지 검증
        if (!relationship.getReceiver().getId().equals(receiverId)) {
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

    @Transactional
    public void rejectFriendRequest(Long receiverId, Long relationshipId) {
        // 친구 요청이 존재하는지 확인
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(IllegalStateException::new);

        //  요청을 받은 사용자인지 검증
        if (!relationship.getReceiver().getId().equals(receiverId)) {
            throw new IllegalArgumentException("해당 친구 요청을 거절할 권한이 없습니다.");
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
                        .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다.")));

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

    public Relationship getBlockedMembers() {
        return null;
    }

    public Relationship block(Long receiverId) {
        return null;
    }
}
