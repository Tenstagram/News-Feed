package com.example.newsfeed.relationship.repository;

import com.example.newsfeed.relationship.entity.Relationship;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Relationship> findBySenderIdAndStatusNot(Long senderId, RelationshipStatus status);

}
