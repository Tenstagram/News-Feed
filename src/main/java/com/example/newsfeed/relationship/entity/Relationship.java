package com.example.newsfeed.relationship.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "relationship")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id")
    private Long id;

    // 임시 필드
    @Column(name = "from_member_id")
    private Long senderId;

    @Column(name = "to_member_id")
    private Long receiverId;

    // 나중에 개발
//    @ManyToOne
//    @JoinColumn(name = "from_member_id")
//    private Member sender;
//
//    @ManyToOne
//    @JoinColumn(name = "to_member_id")
//    private Member receiver;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Relationship(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public void updateStatus(RelationshipStatus status) {
        this.status = status;
    }
}
