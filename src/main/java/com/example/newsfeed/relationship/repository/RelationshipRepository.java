package com.example.newsfeed.relationship.repository;

import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Relationship> findBySenderIdAndStatusNot(Long senderId, RelationshipStatus status);

    List<Relationship> findByReceiverIdAndStatusNot(Long memberId, RelationshipStatus status);

    List<Relationship> findByReceiverIdAndStatus(Long receiverId, RelationshipStatus status);

    List<Relationship> findBySenderIdAndStatus(Long senderId, RelationshipStatus status);

    Optional<Relationship> findBySenderIdAndReceiverId(Long memberId, Long targetId);

    @Query("""
    SELECT CASE
        WHEN r.sender.id = :memberId THEN r.receiver.id
        ELSE r.sender.id
    END
    FROM Relationship r
    WHERE (r.sender.id = :memberId OR r.receiver.id = :memberId)
      AND r.status IN :statuses
    """)
    List<Long> findFriendIdsExcludingBlocked(@Param("memberId") Long memberId,@Param("statuses") List<RelationshipStatus> statuses);
}
